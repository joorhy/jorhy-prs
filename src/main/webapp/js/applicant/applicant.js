/**
 * Created by Joo on 2016/7/30.
 */

var baseData = {};
function showContent(nodeId){
    baseData = {};
    $.ajax({
        type: 'post',
        url:'/applicant/getBaseData',
        data: {id:nodeId.text},
        dataType: 'json',
        success: function (data) {
            if(data.result == "success") {
                $('#contentDiv').panel('refresh','../jsp/applicant/create.jsp');
                baseData = JSON.parse(data.base);
            } else {
                /*if(data.errorType == "user") {
                 showAlertMsg("提示",data.msg,"warning");
                 } else {
                 showRightBottomMsg("系统提示",data.msg,'slide',5000);
                 }*/
            }
        },
        error: function (x, e) {
            alert("error");
        }
    });
    //}
    //else if(nodeId == "approved"){
    //$('#contentDiv').load("../jsp/approved.jsp");
    //    $('#contentDiv').panel('refresh','../jsp/approved.jsp');
    //}
}

function onLoadCreate() {
    $('#pur_code').textbox('setText', baseData['pur_code']);
    $('#funds_src').textbox('setText', baseData['funds_src']);
    $('#contacts').textbox('setText', baseData['contacts']);
    $('#phone_num').textbox('setText', baseData['phone_num']);
    $('#funds_nature').combobox('setValue', baseData['funds_nature']);
    $('#commodity_pre_price').textbox('setText', baseData['commodity_pre_price']);
    $('#service_pre_price').textbox('setText', baseData['service_pre_price']);
    $('#engineering_pre_price').textbox('setText', baseData['engineering_pre_price']);
}

function savePrj() {
    $.messager.confirm('操作提示','是否确认保存此项目?',function(r){
        if (r){
            var baseData = {};
            baseData['pur_code'] = $('#pur_code').textbox('getText');
            baseData['funds_src'] = $('#funds_src').textbox('getText');
            baseData['contacts'] = $('#contacts').textbox('getText');
            baseData['phone_num'] = $('#phone_num').textbox('getText');
            baseData['funds_nature'] = $('#funds_nature').combobox('getValue');
            baseData['commodity_pre_price'] = $('#commodity_pre_price').textbox('getText');
            baseData['service_pre_price'] = $('#service_pre_price').textbox('getText');
            baseData['engineering_pre_price'] = $('#engineering_pre_price').textbox('getText');
            $.ajax({
                type: 'post',
                url:'/applicant/save',
                data: {base:JSON.stringify(baseData),commodity:JSON.stringify($('#dgCommodity').datagrid('getData')),
                    service:JSON.stringify($('#dgService').datagrid('getData')),
                    engineering:JSON.stringify($('#dgEngineering').datagrid('getData'))},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#new_proj').target);
                    } else {
                        /*if(data.errorType == "user") {
                            showAlertMsg("提示",data.msg,"warning");
                        } else {
                            showRightBottomMsg("系统提示",data.msg,'slide',5000);
                        }*/
                    }
                },
                error: function (x, e) {
                    alert("error");
                }
            });
        }
    });
}

function submitPrj() {

}

function cancelPrj() {

}