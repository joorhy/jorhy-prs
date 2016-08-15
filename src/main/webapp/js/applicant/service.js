/**
 * Created by Joo on 2016/7/28.
 */

/**
 * Created by Joo on 2016/7/28.
 */
var dlg_type;
var edit_index;
function appendService(){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增项目');
    $('#prj_name').textbox('clear');
    $('#prj_count').textbox('clear');
    $('#prj_price').textbox('clear');
    $('#prj_spec').textbox('clear');
    $('#prj_param').textbox('clear');
    $('#prj_total_price').textbox('clear');
    dlg_type = 'new';
}

function editService(){
    var row = $('#dgService').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑项目');
        $('#prj_name').textbox('setText', row["prj_name"]);
        $('#prj_count').textbox('setText',row["prj_count"]);
        $('#prj_price').textbox('setText',row["prj_price"]);
        $('#prj_spec').textbox('setText', row["prj_spec"]);
        $('#prj_param').textbox('setText', row["prj_param"]);
        $('#prj_total_price').textbox('setText', '');
        dlg_type = 'edit';
        edit_index = $('#dgService').datagrid('getRowIndex', row);
    }
}

function removeService(){
    var row = $('#dgService').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作提示','是否确认删除此项目?',function(r){
            if (r){
                var index =  $('#dgService').datagrid('getRowIndex', row);
                $('#dgService').datagrid('deleteRow', index);
            }
        });
    }
}
