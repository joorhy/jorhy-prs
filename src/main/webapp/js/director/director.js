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
                } else if (nodeId.type == 'rejected') {
                    $('#contentDiv').panel('setTitle','审批未通过项目');
                    $('#contentDiv').panel('refresh', '../jsp/director/rejected.jsp');
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
        $.messager.alert("警告", "请选择财政局资金分管股室");
        return;
    }

    $.messager.confirm('操作提示','确认需要提交财政局' + $('#approve_person').combobox('getText') +
        '进行资金审核?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/director/agree',
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
    $.messager.confirm('操作提示','确认退回上一级?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/director/disagree',
                data: {purchasing_id:document.getElementById("purchasing_id").value,
                    content:$('#opinion').textbox('getText')},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#approved_prj').target);
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