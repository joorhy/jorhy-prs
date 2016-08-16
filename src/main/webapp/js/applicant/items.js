/**
 * Created by Joo on 2016/7/28.
 */

var dlg_type;
var edit_index;
var project;
function addProjectItem(prj_type){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增项目');
    $('#prj_name').textbox('clear');
    $('#prj_count').textbox('clear');
    $('#prj_price').textbox('clear');
    $('#prj_spec').textbox('clear');
    $('#prj_param').textbox('clear');
    $('#prj_pre_price').textbox('clear');
    dlg_type = 'new';

    if (prj_type == "commodity") {
        project = $('#dgCommodity');
    } else if (prj_type == "service") {
        project = $('#dgService');
    } else if (prj_type == "engineering") {
        project = $('#dgEngineering');
    }
}

function editProjectItem(prj_type){
    if (prj_type == "commodity") {
        project = $('#dgCommodity');
    } else if (prj_type == "service") {
        project = $('#dgService');
    } else if (prj_type == "engineering") {
        project = $('#dgEngineering');
    }

    var row = project.datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑项目');
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

function saveProjectItem(){
    var data = {};
    data["prj_name"] = $('#prj_name').textbox('getText');
    data["prj_count"] = $('#prj_count').textbox('getText');
    data["prj_price"] = $('#prj_price').textbox('getText');
    data["prj_spec"] = $('#prj_spec').textbox('getText');
    data["prj_param"] = $('#prj_param').textbox('getText');
    data["prj_pre_price"] = $('#prj_pre_price').textbox('getText');;
    if (dlg_type == 'edit') {
        var updateData = {};
        updateData['index'] = edit_index;
        updateData['row'] = data;
        project.datagrid('updateRow', updateData);
    } else {
        project.datagrid('appendRow', data);
    }
    $('#dlg').dialog('close');
}

function removeProjectItem(prj_type){
    if (prj_type == "commodity") {
        project = $('#dgCommodity');
    } else if (prj_type == "service") {
        project = $('#dgService');
    } else if (prj_type == "engineering") {
        project = $('#dgEngineering');
    }

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
