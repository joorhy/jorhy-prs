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

function showNewPage() {
    baseData = null;
    $('#contentDiv').panel('setTitle','新建采购过程');
    $('#contentDiv').panel('refresh','../jsp/applicant/create.jsp');
}

function showContent(node){
    baseData = {};
    $.ajax({
        type: 'post',
        url:'/common/getBaseData',
        data: {purchasing_id:node.id},
        dataType: 'json',
        success: function (data) {
            if(data.result == "success") {
                if (node.type == 'create') {
                    showNewPage();
                } else {
                    $('#contentDiv').panel('setTitle','已提交采购过程');
                    $('#contentDiv').panel('refresh', '../jsp/applicant/submitted.jsp');
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

function onLoadCreate() {
    if (baseData != null) {
        document.getElementById("purchasing_id").value = baseData['purchasing_id'];
        $('#pur_code').textbox('setText', baseData['pur_code']);
        $('#pur_code').textbox('disable');
        $('#funds_src').textbox('setText', baseData['funds_src']);
        $('#contacts').textbox('setText', baseData['contacts']);
        $('#phone_num').textbox('setText', baseData['phone_num']);
        $('#funds_nature').combobox('setValue', baseData['funds_nature']);
        $('#commodity_pre_price').textbox('setText', baseData['commodity_pre_price']);
        $('#service_pre_price').textbox('setText', baseData['service_pre_price']);
        $('#engineering_pre_price').textbox('setText', baseData['engineering_pre_price']);
    } else {
        document.getElementById("purchasing_id").value = Math.uuid(36, 62);
    }
}

function savePurchasing() {
    $.messager.confirm('操作提示','确认保存此项目?',function(r){
        if (r){
            var baseData = {};
            baseData['purchasing_id'] = document.getElementById("purchasing_id").value;
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
                        $('#menuTree').tree('reload', $('#new_prj').target);
                        var cur_node = {};
                        cur_node.id = document.getElementById("purchasing_id").value;
                        cur_node.type = "create";
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

function submitPurchasing() {
    $.messager.confirm('操作提示','确认提交审核?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/applicant/submit',
                data: {purchasing_id:document.getElementById("purchasing_id").value},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#new_proj').target);
                        showNewPage();
                    } else {
                    }
                },
                error: function (x, e) {
                    alert("error submitPurchasing");
                }
            });
        }
    });
}

function cancelPurchasing() {
    $.messager.confirm('操作提示','确认撤销此项目?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:'/applicant/cancel',
                data: {purchasing_id:document.getElementById("purchasing_id").value},
                dataType: 'json',
                success: function (data) {
                    if(data.result == "success") {
                        $('#menuTree').tree('reload', $('#new_proj').target);
                        showNewPage();
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

function getProjectByType(prj_type) {
    if (prj_type == "commodity") {
        project = $('#dgCommodity');
    } else if (prj_type == "service") {
        project = $('#dgService');
    } else if (prj_type == "engineering") {
        project = $('#dgEngineering');
    }
}

function clearItemsData() {
    $('#prj_name').textbox('clear');
    $('#prj_count').textbox('clear');
    $('#prj_price').textbox('clear');
    $('#prj_spec').textbox('clear');
    $('#prj_param').textbox('clear');
    $('#prj_pre_price').textbox('clear');
}
function fillItmesData(row) {
    $('#prj_name').textbox('setText', row["prj_name"]);
    $('#prj_count').textbox('setText',row["prj_count"]);
    $('#prj_price').textbox('setText',row["prj_price"]);
    $('#prj_spec').textbox('setText', row["prj_spec"]);
    $('#prj_param').textbox('setText', row["prj_param"]);
    $('#prj_pre_price').textbox('setText', row["prj_pre_price"]);
}

function getItemsData() {
    var data = {};
    data["prj_name"] = $('#prj_name').textbox('getText');
    data["prj_count"] = $('#prj_count').textbox('getText');
    data["prj_price"] = $('#prj_price').textbox('getText');
    data["prj_spec"] = $('#prj_spec').textbox('getText');
    data["prj_param"] = $('#prj_param').textbox('getText');
    data["prj_pre_price"] = $('#prj_pre_price').textbox('getText');
    return data;
}

function addProjectItem(prj_type){
    $('#dlgCreate').dialog('open').dialog('center').dialog('setTitle','新增项目');
    clearItemsData();
    dlg_type = 'new';
    getProjectByType(prj_type);
}

function editProjectItem(prj_type){
    getProjectByType(prj_type);
    var row = project.datagrid('getSelected');
    if (row){
        $('#dlgCreate').dialog('open').dialog('center').dialog('setTitle','编辑项目');
        fillItmesData(row);
        dlg_type = 'edit';
        edit_index = project.datagrid('getRowIndex', row);
    }
}

function saveProjectItem(){
    var data = getItemsData();
    if (dlg_type == 'edit') {
        var updateData = {};
        updateData['index'] = edit_index;
        updateData['row'] = data;
        project.datagrid('updateRow', updateData);
    } else {
        project.datagrid('appendRow', data);
    }
    $('#dlgCreate').dialog('close');
}

function removeProjectItem(prj_type){
    getProjectByType(prj_type);
    var row = project.datagrid('getSelected');
    if (row){
        $.messager.confirm('操作提示','是否确认删除此项目?',function(r){
            if (r){
                var index =  project.datagrid('getRowIndex', row);
                project.datagrid('deleteRow', index);
            }
        });
    }
}