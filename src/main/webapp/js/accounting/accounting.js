/**
 * Created by Joo on 2016/8/20.
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
                    $('#contentDiv').panel('refresh', '../jsp/accounting/to_approve.jsp');
                } else if (nodeId.type == 'approved') {
                    $('#contentDiv').panel('setTitle','已审批项目');
                    $('#contentDiv').panel('refresh', '../jsp/accounting/approved.jsp');
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

function agreePurchasing() {
    var approve_id = $('#approve_person').combobox('getValue');
    if (approve_id == 'default') {
        $.messager.alert("警告", "请选择上级审核人");
        return;
    }

    $.messager.confirm('操作提示','确认需要' + $('#approve_person').combobox('getText') + '审核?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/accounting/agree',
                data: {purchasing_id:document.getElementById("purchasing_id").value,
                       content:$('#opinion').textbox('getText')},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#approved_prj').target);
                        var cur_node = {};
                        cur_node.id = document.getElementById("purchasing_id").value;
                        cur_node.type = "approved";
                        showContent(cur_node);
                    } else {
                    }
                },
                error: function (x, e) {
                    alert("error cancelPurchasing");
                }
            });
        }
    });
}

function disagreePurchasing() {
    $.messager.confirm('操作提示','确认退回此项目?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/accounting/disagree',
                data: {purchasing_id:document.getElementById("purchasing_id").value,
                       content:$('#opinion').textbox('getText')},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                    } else {
                    }
                },
                error: function (x, e) {
                    alert("error cancelPurchasing");
                }
            });
        }
    });
}