/**
 * Created by JooLiu on 2016/8/25.
 */
function showContent(node){
    baseData = null;
    if (node.type == 'to_pay' || node.type == 'paid') {
        document.getElementById('purchasing_id').value = node.id;
        $.ajax({
            type: 'post',
            url:'/common/getBaseData',
            data: {purchasing_id:node.id},
            dataType: 'json',
            success: function (data) {
                if(data.result == "success") {
                    $('#contentDiv').panel('setTitle', node.text);
                    $('#contentDiv').panel('refresh', '../jsp/payment/project.jsp');
                    baseData = data.base;
                } else {
                    baseData = null;
                }
            },
            error: function (x, e) {
                alert("error");
            }
        });
    }else {
        $.ajax({
            type: 'post',
            url:'/common/getPacketBase',
            data: {packet_id:node.id},
            dataType: 'json',
            success: function (data) {
                if(data.result == "success") {
                    $('#contentDiv').panel('setTitle', node.text);
                    var parentNode = $('#menuTree').tree('getParent', node.target)
                    if (parentNode.type == 'to_pay') {
                        $('#contentDiv').panel('refresh', '../jsp/payment/to_pay.jsp');
                    } else {
                        $('#contentDiv').panel('refresh', '../jsp/payment/paid.jsp');
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
}

function onLoadCreatePacket() {
    if (baseData != null) {
        document.getElementById("packet_id").value = baseData['packet_id'];
        $('#pack_code').textbox('setText', baseData['pack_code']);
        $('#pack_code').textbox('disable');
        $('#pur_address').textbox('setText', baseData['pur_address']);
        $('#expert_count').textbox('setText', baseData['expert_count']);
        $('#pur_date').datebox('setValue', baseData['pur_date']);
        $('#pur_method').combobox('setValue', baseData['pur_method']);
        $('#pur_publicity').switchbutton('setValue', baseData['pur_publicity']);
        $('#pur_supplier').textbox('setText', baseData['pur_supplier']);
        $('#pur_amount').textbox('setText', baseData['pur_amount']);
    }
}
