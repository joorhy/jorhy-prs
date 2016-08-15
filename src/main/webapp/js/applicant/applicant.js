/**
 * Created by Joo on 2016/7/30.
 */
$(function(){
    $("#menuTree").tree({
        onClick: function(node){
            showcontent(node);
        }
    });
});

function showcontent(nodeId){
    var parm = {};
    paramData['purc_code'] = nodeId.text;
    if(nodeId == "new_proj"){
        $.ajax({
            type: 'post',
            url:'/applicant/getBaseData',
            data: {param:JSON.stringify(paramData)},
            dataType: 'json',
            success: function (data) {
                if(data.resultCode == 1) {

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
        var baseData =
        $('#contentDiv').panel('refresh','../jsp/applicant/create.jsp');

    }
    else if(nodeId == "approved"){
        //$('#contentDiv').load("../jsp/approved.jsp");
        $('#contentDiv').panel('refresh','../jsp/approved.jsp');
    }

}

function savePrj() {
    $.messager.confirm('操作提示','是否确认保存此项目?',function(r){
        if (r){
            var baseData = {};
            baseData['purc_code'] = $('#purc_code').textbox('getText');
            baseData['funds_src'] = $('#funds_src').textbox('getText');
            baseData['contacts'] = $('#contacts').textbox('getText');
            baseData['phone_num'] = $('#phone_num').textbox('getText');
            baseData['funds_nature'] = $('#funds_nature').combobox('getValue');
            baseData['commodity_total_price'] = $('#commodity_total_price').textbox('getText');
            baseData['service_total_price'] = $('#service_total_price').textbox('getText');
            baseData['engineering_total_price'] = $('#engineering_total_price').textbox('getText');
            $.ajax({
                type: 'post',
                url:'/applicant/save',
                data: {base:JSON.stringify(baseData),commodity:JSON.stringify($('#dgCommodity').datagrid('getData')),
                    service:JSON.stringify($('#dgService').datagrid('getData')),
                    engineering:JSON.stringify($('#dgEngineering').datagrid('getData'))},
                dataType: 'json',
                success: function (data) {
                    if(data.resultCode == 1) {
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