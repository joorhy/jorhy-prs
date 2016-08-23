/**
 * Created by JooLiu on 2016/8/23.
 */
function showContent(node){
    baseData = {};
    $.ajax({
        type: 'post',
        url:'/common/getBaseData',
        data: {purchasing_id:node.id},
        dataType: 'json',
        success: function (data) {
            if(data.result == "success") {
                if (node.type == 'to_divide' || node.type == 'divided' || node.type == 'finished' ||
                    node.type == 'interrupt' || node.type == 'failed') {
                    $('#contentDiv').panel('setTitle', node.text);
                    $('#contentDiv').panel('refresh', '../jsp/purchase/project.jsp');
                } else {

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

function showNewPacket() {
    $('#contentDiv').panel('setTitle', '新建分包');
    $('#contentDiv').panel('refresh', '../jsp/purchase/new_packet.jsp');
}

function savePackets() {

}