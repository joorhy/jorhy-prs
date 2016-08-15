/**
 * Created by Joo on 2016/7/30.
 */
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