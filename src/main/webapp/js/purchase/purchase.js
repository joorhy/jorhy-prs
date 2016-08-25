/**
 * Created by JooLiu on 2016/8/23.
 */
function showNewPacket() {
    baseData = null;
    $('#contentDiv').panel('setTitle', '新建分包');
    $('#contentDiv').panel('refresh', '../jsp/purchase/new_packet.jsp');
}

function submitPackets() {
    $.messager.confirm('操作提示','确认提交分包?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/purchase/submit',
                data: {purchasing_id:document.getElementById("purchasing_id").value},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#to_divide').target);
                        var cur_node = {};
                        cur_node.id = document.getElementById("purchasing_id").value;
                        cur_node.type = "to_divide";
                        showContent(cur_node);
                    } else {
                    }
                },
                error: function (x, e) {
                    alert("error savePurchasing");
                }
            });
        }
    });
}

function showContent(node){
    baseData = null;
    if (node.type == 'to_divide' || node.type == 'divided' || node.type == 'finished' ||
        node.type == 'interrupt' || node.type == 'failed') {
        document.getElementById('purchasing_id').value = node.id;
        $.ajax({
            type: 'post',
            url:'/common/getBaseData',
            data: {purchasing_id:node.id},
            dataType: 'json',
            success: function (data) {
                if(data.result == "success") {
                    $('#contentDiv').panel('setTitle', node.text);
                    $('#contentDiv').panel('refresh', '../jsp/purchase/project.jsp');
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
                    if (parentNode.type == 'to_divide') {
                        $('#contentDiv').panel('refresh', '../jsp/purchase/temp_packet.jsp');
                    } else {
                        $('#contentDiv').panel('refresh', '../jsp/purchase/packeted.jsp');
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
    } else {
        document.getElementById("packet_id").value = Math.uuid(36, 62);
    }
}

function savePacket() {
    $.messager.confirm('操作提示','确认保存此分包?',function(r){
        if (r){
            var baseData = {};
            baseData['purchasing_id'] = document.getElementById("purchasing_id").value;
            baseData['packet_id'] = document.getElementById("packet_id").value;
            baseData['pack_code'] = $('#pack_code').textbox('getText');
            baseData['pur_address'] = $('#pur_address').textbox('getText');
            baseData['expert_count'] = $('#expert_count').textbox('getText');
            baseData['pur_date'] = $('#pur_date').datebox('getValue');
            baseData['pur_method'] = $('#pur_method').combobox('getValue');
            baseData['pur_publicity'] = $('#pur_publicity').switchbutton('options').checked;
            baseData['pur_supplier'] = $('#pur_supplier').textbox('getText') ;
            baseData['pur_amount'] = $('#pur_amount').textbox('getText');

            var items = $('#tbToDivide').datagrid('getChecked');
            var checkedData = {};
            checkedData['total'] = items.length;
            checkedData['rows'] = items;
            $.ajax({
                type: 'post',
                url:'/purchase/save',
                data: {base:JSON.stringify(baseData),products:JSON.stringify(checkedData)},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#to_divide').target);
                        var cur_node = {};
                        cur_node.id = document.getElementById("purchasing_id").value;
                        cur_node.type = "to_divide";
                        showContent(cur_node);
                    } else {
                    }
                },
                error: function (x, e) {
                    alert("error savePurchasing");
                }
            });
        }
    });
}

function cancelPacket() {
    $.messager.confirm('操作提示','确认取消此分包?',function(r) {
        if (r) {
            showNewPacket();
        }
    });
}

function repacket() {
    $.messager.confirm('操作提示','确认重新分包?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/purchase/repacket',
                data: {packet_id:document.getElementById("packet_id").value},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#to_divide').target);
                        var cur_node = {};
                        cur_node.id = document.getElementById("purchasing_id").value;
                        cur_node.type = "to_divide";
                        showContent(cur_node);
                    } else {
                    }
                },
                error: function (x, e) {
                    alert("error savePurchasing");
                }
            });
        }
    });
}