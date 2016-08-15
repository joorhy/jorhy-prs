/**
 * Created by Joo on 2016/7/28.
 */

var dlg_type;
var edit_index;
function addCommodity(){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增项目');
    $('#prj_name').textbox('clear');
    $('#prj_count').textbox('clear');
    $('#prj_price').textbox('clear');
    $('#prj_spec').textbox('clear');
    $('#prj_param').textbox('clear');
    $('#prj_total_price').textbox('clear');
    dlg_type = 'new';
}

function editCommodity(){
    var row = $('#dgCommodity').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑项目');
        $('#prj_name').textbox('setText', row["prj_name"]);
        $('#prj_count').textbox('setText',row["prj_count"]);
        $('#prj_price').textbox('setText',row["prj_price"]);
        $('#prj_spec').textbox('setText', row["prj_spec"]);
        $('#prj_param').textbox('setText', row["prj_param"]);
        $('#prj_total_price').textbox('setText', '');
        dlg_type = 'edit';
        edit_index = $('#dgCommodity').datagrid('getRowIndex', row);
    }
}

function saveCommodity(){
    var data = {};
    data["prj_name"] = $('#prj_name').textbox('getText');
    data["prj_count"] = $('#prj_count').textbox('getText');
    data["prj_price"] = $('#prj_price').textbox('getText');
    data["prj_spec"] = $('#prj_spec').textbox('getText');
    data["prj_param"] = $('#prj_param').textbox('getText');
    data["prj_total_price"] = '';
    if (dlg_type == 'edit') {
        var updateData = {};
        updateData['index'] = edit_index;
        updateData['row'] = data;
        $('#dgCommodity').datagrid('updateRow', updateData);
    } else {
        $('#dgCommodity').datagrid('appendRow', data);
    }

    $('#dlg').dialog('close');
}

function removeCommodity(){
    var row = $('#dgCommodity').datagrid('getSelected');
    if (row){
        //$.messager.defaults = { ok: "确 认", cancel: "取 消" };
        $.messager.confirm('操作提示','是否确认删除此项目?',function(r){
            if (r){
                var index =  $('#dgCommodity').datagrid('getRowIndex', row);
                $('#dgCommodity').datagrid('deleteRow', index);
            }
        });
    }
}
