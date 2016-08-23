/**
 * Created by JooLiu on 2016/8/23.
 */
function showContent(nodeId){
    baseData = {};
    $.ajax({
        type: 'post',
        url:'/common/getBaseData',
        data: {purchasing_id:nodeId.id},
        dataType: 'json',
        success: function (data) {
            if(data.result == "success") {
                if (nodeId.type == 'create') {
                    showNewPage();
                } else {
                    $('#contentDiv').panel('setTitle','已提交采购过程');
                    $('#contentDiv').panel('refresh', '../jsp/applicant/submitted.jsp');
                }
                baseData = data.base;
            } else {
                baseData = null;
            }
        },
        error: function (x, e) {
            alert("error");
        }
    });
}