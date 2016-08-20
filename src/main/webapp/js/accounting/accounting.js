/**
 * Created by Joo on 2016/8/20.
 */

var baseData = {};
function showContent(nodeId){
    baseData = {};
    $.ajax({
        type: 'post',
        url:'/applicant/getBaseData',
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

function onLoad() {
    if (baseData != null) {
        document.getElementById("purchasing_id").value = baseData['purchasing_id'];
        $('#pur_code').textbox('setText', baseData['pur_code']);
        $('#funds_src').textbox('setText', baseData['funds_src']);
        $('#contacts').textbox('setText', baseData['contacts']);
        $('#phone_num').textbox('setText', baseData['phone_num']);
        $('#funds_nature').combobox('setValue', baseData['funds_nature']);
        $('#commodity_pre_price').textbox('setText', baseData['commodity_pre_price']);
        $('#service_pre_price').textbox('setText', baseData['service_pre_price']);
        $('#engineering_pre_price').textbox('setText', baseData['engineering_pre_price']);
    }
}