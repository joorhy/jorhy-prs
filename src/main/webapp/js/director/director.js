/**
 * Created by JooLiu on 2016/8/22.
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
                if (nodeId.type == 'to_approve') {
                    $('#contentDiv').panel('setTitle','待审批项目');
                    $('#contentDiv').panel('refresh', '../jsp/director/to_approve.jsp');
                } else if (nodeId.type == 'approved') {
                    $('#contentDiv').panel('setTitle','已审批项目');
                    $('#contentDiv').panel('refresh', '../jsp/director/approved.jsp');
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