/**
 * Created by Joo on 2016/8/20.
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
                if (node.type == 'to_approve') {
                    $('#contentDiv').panel('setTitle','待审批项目');
                    if (node.role == "ACCOUNTING") {
                        $('#contentDiv').panel('refresh', '../jsp/approval/acc_to_approve.jsp');
                    } else if (node.role == "DIRECTOR") {
                        $('#contentDiv').panel('refresh', '../jsp/approval/dir_to_approve.jsp');
                    } else if (node.role == "REGULATORY") {
                        $('#contentDiv').panel('refresh', '../jsp/approval/reg_to_approve.jsp');
                    }else if (node.role == "BUREAU") {
                        $('#contentDiv').panel('refresh', '../jsp/approval/to_approve.jsp');
                    }
                } else if (node.type == 'approved') {
                    $('#contentDiv').panel('setTitle','已审批项目');
                    if (node.role == "REGULATORY") {
                        $('#contentDiv').panel('refresh', '../jsp/approval/reg_approved.jsp');
                    } else {
                        $('#contentDiv').panel('refresh', '../jsp/approval/approved.jsp');
                    }
                } else if (node.type == 'rejected') {
                    $('#contentDiv').panel('setTitle','审批未通过项目');
                    $('#contentDiv').panel('refresh', '../jsp/approval/rejected.jsp');
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
    var approve_content = "确认同意";
    if ( $('#approve_person').length > 0) {
        var approve_id = $('#approve_person').combobox('getValue');
        if (approve_id == 'default') {
            $.messager.alert("警告", "请选择上级审核人");
            return;
        } else {
            approve_content = '确认需要' + $('#approve_person').combobox('getText') + '审核?';
        }
    }
    if ($('#approve_department').length > 0) {
        var approve_id = $('#approve_department').combobox('getValue');
        if (approve_id == 'default') {
            $.messager.alert("警告", "请选择财政局资金分管股室");
            return;
        } else {
            approve_content = '确认需要提交财政局' + $('#approve_department').combobox('getText') + '进行资金审核?';
        }
    }
    if ($('#purchasing_nature').length > 0) {
        var approve_id = $('#purchasing_nature').combobox('getValue');
        if (approve_id == 'default') {
            $.messager.alert("警告", "请选择采购性质");
            return;
        } else {
            approve_content = '确认提交审核?';
        }
    }

    $.messager.confirm('操作提示', approve_content, function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/common/approve',
                data: {purchasing_id:document.getElementById("purchasing_id").value,
                       content:$('#opinion').textbox('getText'),
                       opinion:'agree'},
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
                url:'/common/approve',
                data: {purchasing_id:document.getElementById("purchasing_id").value,
                       content:$('#opinion').textbox('getText'),
                       opinion:'disagree'},
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

function complaintsPurchasing () {
    $('#dlgComplaints').dialog('open').dialog('center').dialog('setTitle','投诉处理');
}

function submitComplaintsOpinion () {
    $('#dlgComplaints').dialog('close');
}