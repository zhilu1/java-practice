// https://www.cnblogs.com/sexintercourse/p/6490921.html
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

function editEvent(event) {
    $('#event-modal input[name="startTime"]').val(event ? event.startTime : '');
    $('#event-modal input[name="endTime"]').val(event ? event.endTime : '');
    $('#event-modal input[name="startDate"]').datepicker('update', event ? event.startDate : '');
    $('#event-modal input[name="endDate"]').datepicker('update', event ? event.endDate : '');
    $('#event-modal').modal();
}

function deleteEvent(event) {
    var dataSource = $('#calendar').data('calendar').getDataSource();

    for(var i in dataSource) {
        if(dataSource[i].id === event.id) {
            dataSource.splice(i, 1);
            break;
        }
    }

    $('#calendar').data('calendar').setDataSource(dataSource);
}

function saveEvent() {

    var event = {
        status: $('#event-modal input[name="status"]').val(),
        startTime: $('#event-modal input[name="startTime"]').val(),
        endTime: $('#event-modal input[name="endTime"]').val(),
        startDate: $('#event-modal input[name="startDate"]').datepicker('getDate'),
        endDate: $('#event-modal input[name="endDate"]').datepicker('getDate'),
        ignoSetDays: $('#event-modal input[name="ignoSetDays"]').checked ,
        ignoWeekends: $('#event-modal input[name="ignoWeekends"]').checked
    };
    let evStartD = new Date(event.startDate);
    let evEndD = new Date(event.endDate);

    let dataSource = $('#calendar').data('calendar').getDataSource();

    for (;evStartD <= evEndD; evStartD.setDate(evStartD.getTime() + 24 * 60 * 60 * 1000)) {
        // loop through once per day
        if(event.ignoWeekends && (evStartD.getDay() === 0 || evStartD.getDay() === 6 )){
            continue; // continue if weekends is ignored
        }
        let newDate = {
            date: evStartD,
            status: event.status,
            startTime: event.startTime,
            endTime: event.endTime,
        }
        dataSource.push(newDate);

    }



    $('#calendar').data('calendar').setDataSource(dataSource);
    $('#event-modal').modal('hide');
}

function init(data) {
    var currentYear = new Date().getFullYear();
    // var dayOnList = []; // working days list
    // var dayOffList = []; //day off list
    console.log(data);
    $('#calendar').calendar({
        enableRangeSelection: true,

        customDayRenderer: function(element, date) {
            if(date.getDay() === 0 || date.getDay() === 6 ) {
                $(element).css('font-weight', 'bold');
                $(element).css('color', '#66ccff');
            }
            for(let i = 0; i < data.length ; i ++){
                if(data[i].date === date.Format("yyyy-MM-dd")){
                    if(data[i].status === 1){
                        $(element).css('background-color', '#88e08e');
                        $(element).css('color', 'white');
                    }
                    if(data[i].status === 0){
                        $(element).css('color', 'grey');
                    }
                }
            }
            // if(dateMap[date].status === 1) {
            //     $(element).css('background-color', 'red');
            //     $(element).css('color', 'white');
            // }

        },
        selectRange: function(e) {
            editEvent({ startDate: e.startDate, endDate: e.endDate });
        },
        mouseOnDay: function(e) {
            let officeDate = null;
            let ds = $('#calendar').data('calendar').getDataSource();
            for (let i = 0; i < ds.length; i++){
                officeDate = ds[i];
                let timeoffset = new Date().getTimezoneOffset();
                let elemDate = new Date(e.date.getTime() - timeoffset * 1000 * 60);

                if(officeDate.date === elemDate.Format("yyyy-MM-dd")){
                    let content = '';
                    let startShown = officeDate.startTime === null ? "" : officeDate.startTime;
                    let endShown = officeDate.endTime === null ? "" : officeDate.endTime;
                    let info = officeDate.status === 1 ? "工作日" : "非工作日";
                        content += '<div class="event-tooltip-content">'
                            + '<div>'+ "上班时间：  " + startShown+ '</div>'
                            + '<div>' + "下班时间：  " + endShown+ '</div>'
                                + '<div>' + info + '</div>'
                                + '</div>';

                        $(e.element).popover({
                            trigger: 'manual',
                            container: 'body',
                            html:true,
                            content: content
                        });

                        $(e.element).popover('show');
                    break;

                }
            }
        },
        mouseOutDay: function(e) {
            $(e.element).popover('hide');
        },

        dataSource: data
            // {
            //     status: 0,
            //     startTime: "09:00",
            //     endTime: "20:00"
            // }
            // {
            //     id: 1,
            //     name: 'Microsoft Convergence',
            //     location: 'New Orleans, LA',
            //     startDate: new Date(currentYear, 2, 16),
            //     endDate: new Date(currentYear, 2, 19)
            // }

    });

    $('#save-event').click(function() {
        saveEvent();
    });
}

