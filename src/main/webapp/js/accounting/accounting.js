/**
 * Created by Joo on 2016/8/20.
 */

var baseData = {};
function showContent(nodeId){
    baseData = {};
    $.ajax({
        type: 'post',
        url:'/common/getBaseData',
        data: {id:nodeId.id},
        dataType: 'json',
        success: function (data) {
            if(data.result == "success") {
                if (nodeId.type == 'submitted') {
                    $('#contentDiv').panel('setTitle','新建采购过程');
                    $('#contentDiv').panel('refresh', '../jsp/accounting/to_approve.jsp');
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