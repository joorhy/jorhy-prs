/**
 * Created by Joo on 2016/8/20.
 */
// Private array of chars to use


function onLoadBase() {
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

function onLoadAttachFile() {
    $.ajax({
        type: 'post',
        url:'/common/getAttachFiles',
        data: {purchasing_id:document.getElementById("purchasing_id").value,
               packet_id:document.getElementById("packet_id") == null ? null :
                         document.getElementById("packet_id").value},
        dataType: 'json',
        success: function (data) {
            for(var key in data.files) {
                var file = data.files[key];
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/common/downloadFile?file_id=' + file.id + '&purchasing_id=' +
                    document.getElementById("purchasing_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size)  + ')</a> <b></b><br/></div>';
            }
        },
        error: function (x, e) {
            alert("error");
        }
    });
}

function delAttachFile(file_id) {
    $.ajax({
        type: 'post',
        url:'/common/removeFile',
        data: {file_id:file_id.id,purchasing_id:document.getElementById("purchasing_id").value},
        dataType: 'json',
        success: function (data) {
            if(data.result == "success") {
                file_id.parentNode.removeChild(file_id);
            }
        },
        error: function (x, e) {
            alert("error");
        }
    });
}

function viewProjectItem(prj_type) {
    if (prj_type == "commodity") {
        project = $('#dgCommodity');
    } else if (prj_type == "service") {
        project = $('#dgService');
    } else if (prj_type == "engineering") {
        project = $('#dgEngineering');
    }

    var row = project.datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','查看项目');
        $('#prj_name').textbox('setText', row["prj_name"]);
        $('#prj_count').textbox('setText',row["prj_count"]);
        $('#prj_price').textbox('setText',row["prj_price"]);
        $('#prj_spec').textbox('setText', row["prj_spec"]);
        $('#prj_param').textbox('setText', row["prj_param"]);
        $('#prj_pre_price').textbox('setText', row["prj_pre_price"]);
        dlg_type = 'edit';
        edit_index = project.datagrid('getRowIndex', row);
    }
}

function viewOpinion() {

}

function viewComplaints() {

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