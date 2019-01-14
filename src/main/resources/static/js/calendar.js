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
    $('#event-modal input[name="event-index"]').val(event ? event.id : '');
    $('#event-modal input[name="event-status"]').val(event ? event.status : '');
    $('#event-modal input[name="event-start-time"]').val(event ? event.startTime : '');
    $('#event-modal input[name="event-end-time"]').val(event ? event.endTime : '');
    $('#event-modal input[name="event-start-date"]').datepicker('update', event ? event.startDate : '');
    $('#event-modal input[name="event-end-date"]').datepicker('update', event ? event.endDate : '');
    $('#event-modal input[name="event-igno-holiday"]').val(event ? event.ignoHoliday : '');
    $('#event-modal input[name="event-igno-weekends"]').val(event ? event.ignoWeekends : '');
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
        status: $('#event-modal input[name="event-status"]').val(),
        startTime: $('#event-modal input[name="event-start-time"]').val(),
        endTime: $('#event-modal input[name="event-end-time"]').val(),
        startDate: $('#event-modal input[name="event-start-date"]').datepicker('getDate'),
        endDate: $('#event-modal input[name="event-end-date"]').datepicker('getDate'),
        ignoHoliday: $('#event-modal input[name="event-igno-holiday"]').checked ,
        ignoWeekends: $('#event-modal input[name="event-igno-weekends"]').checked
    };
    let evStartD = Date(event.startDate);
    let evEndD = Date(event.endDate);

    let dataSource = $('#calendar').data('calendar').getDataSource();

    for (;evStartD.getTime() <= evEndD.getTime(); evStartD.setDate(evStartD.getTime() + 24 * 60 * 60 * 1000)) {
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



    // $('#calendar').data('calendar').setDataSource(dataSource);
    $('#event-modal').modal('hide');
}

function init(data) {
    var currentYear = new Date().getFullYear();
    // var dayOnList = []; // working days list
    // var dayOffList = []; //day off list
    console.log(data);
    $('#calendar').calendar({
        enableContextMenu: true,
        enableRangeSelection: true,
        contextMenuItems:[
            {
                text: 'Update',
                click: editEvent
            },
            {
                text: 'Delete',
                click: deleteEvent
            }
        ],
        customDayRenderer: function(element, date) {
            if(date.getDay() === 0 || date.getDay() === 6 ) {
                $(element).css('font-weight', 'bold');
                $(element).css('color', 'green');
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
            for (let i = 0; i < data.length; i++){
                officeDate = data[i];
                let timeoffset = new Date().getTimezoneOffset();
                let elemDate = new Date(e.date.getTime() - timeoffset * 1000 * 60);

                if(officeDate.date === elemDate.Format("yyyy-MM-dd")){
                    var content = '';
                    console.log(content);
                        content += '<div class="event-tooltip-content">'
                            + '<div class="event-name">'+ "上班时间：  " + officeDate.startTime+ '</div>'
                            + '<div class="event-name">' + "下班时间：  " + officeDate.endTime+ '</div>'
                                + '<div class="event-location">' + officeDate.status + '</div>'
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
        dayContextMenu: function(e) {
            $(e.element).popover('hide');
        },
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
};

