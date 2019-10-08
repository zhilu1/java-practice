$(document).ready(function () {
    $('#exampleModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget) // Button that triggered the modal
        let staffId = button.data('whatever') // Extract info from data-* attributes
        let riqi1 = button.data('whatever1')
        let riqi =todate(riqi1)
        // console.log(recipient);
        // console.log("here");
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        $('#staffId').val(staffId)
        $('#riqi').val(riqi)
        // modal.find('.modal-body input').val(recipient)
    });
})

function todate(inputstr) {
    //Wed Mar 22 13:38:37 CST 2017
    inputstr = inputstr + ""; //末尾加一个空格
    var date = "";
    var month = new Array();

    month["Jan"] = 1; month["Feb"] = 2; month["Mar"] = 3; month["Apr"] = 4; month["May"] = 5; month["Jun"] = 6;
    month["Jul"] = 7; month["Aug"] = 8; month["Sep"] = 9; month["Oct"] = 10; month["Nov"] = 11; month["Dec"] = 12;

    str = inputstr.split(" ");

    date = str[5];
    date += "-" + month[str[1]] + "-" + str[2];

    return date;
}

function changeStatu() {
    let staffId = document.getElementById("staffId");
    let riqi = document.getElementById("riqi");
    let statu = document.getElementById("statu");
    let searchParams = new URLSearchParams;
    searchParams.set('staffId',staffId.value);
    searchParams.set('riqi',riqi.value);
    searchParams.set('statu',statu.value);
    fetch("http://localhost:8080/changeStatu",{
        method:'POST',
        body:searchParams
    }).then(function (res) {
        return res.json()
    }).then(json=>{
        $('#exampleModal').modal('hide');
        location.reload();
        console.log(json);
    }).catch(err=>{
       console.log(err);
    });

}

function changeMonthStatu() {
    let staffId = document.getElementById("staffId");
    let year = document.getElementById("year1");
    let month = document.getElementById("month1");
    let searchParams = new URLSearchParams;
    searchParams.set('staffId',staffId.value);
    searchParams.set('year',year.value);
    searchParams.set('month',month.value);
    fetch("http://localhost:8080/changeMonthStatu",{
        method:'POST',
        body:searchParams
    }).then(function (res) {
        return res.json()
    }).then(json=>{
        $('#exampleModal').modal('hide');
        location.reload();
        console.log(json);
    }).catch(err=>{
        console.log(err);
    });
}


