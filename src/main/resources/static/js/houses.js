$(document).ready(function(){
    $(".page-number").click(function () {
        var houseName = $("#houseName").val();

        var pageIndex = $(this).html();
        window.location = "/houselist/"+pageIndex+"?houseName="+houseName;
    });
});

