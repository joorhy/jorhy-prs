/**
 * Created by Joo on 2016/7/30.
 */


// Private array of chars to use
var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
Math.uuid = function (len, radix) {
    var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;
    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        // rfc4122, version 4 form
        var r;
        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}

var baseData = {};
function showNewPage() {
    baseData = null;
    $('#contentDiv').panel('setTitle','新建采购过程');
    $('#contentDiv').panel('refresh','../jsp/applicant/create.jsp');
}

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
                baseData = null;
            }
        },
        error: function (x, e) {
            alert("error");
        }
    });
}

function onLoadCreate() {
    if (baseData != null) {
        $('#pur_code').textbox('setText', baseData['pur_code']);
        $('#funds_src').textbox('setText', baseData['funds_src']);
        $('#contacts').textbox('setText', baseData['contacts']);
        $('#phone_num').textbox('setText', baseData['phone_num']);
        $('#funds_nature').combobox('setValue', baseData['funds_nature']);
        $('#commodity_pre_price').textbox('setText', baseData['commodity_pre_price']);
        $('#service_pre_price').textbox('setText', baseData['service_pre_price']);
        $('#engineering_pre_price').textbox('setText', baseData['engineering_pre_price']);
    } else {
        document.getElementById("purchasing_id").value = Math.uuid(36, 62);
        /*$('#funds_nature').combobox('setValue', 'ncys');
        $('#commodity_pre_price').textbox('setText', '0');
        $('#service_pre_price').textbox('setText', '0');
        $('#engineering_pre_price').textbox('setText', '0');*/
    }
}

function savePurchasing() {
    $.messager.confirm('操作提示','是否确认保存此项目?',function(r){
        if (r){
            var baseData = {};
            baseData['pur_code'] = $('#pur_code').textbox('getText');
            baseData['funds_src'] = $('#funds_src').textbox('getText');
            baseData['contacts'] = $('#contacts').textbox('getText');
            baseData['phone_num'] = $('#phone_num').textbox('getText');
            baseData['funds_nature'] = $('#funds_nature').combobox('getValue');
            baseData['commodity_pre_price'] = $('#commodity_pre_price').textbox('getText') == ''
                ? '0' : $('#commodity_pre_price').textbox('getText');
            baseData['service_pre_price'] = $('#service_pre_price').textbox('getText') == ''
                ? '0' : $('#service_pre_price').textbox('getText');
            baseData['engineering_pre_price'] = $('#engineering_pre_price').textbox('getText') == ''
                ? '0' : $('#engineering_pre_price').textbox('getText');
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

function submitPurchasing() {

}

function cancelPurchasing() {

}