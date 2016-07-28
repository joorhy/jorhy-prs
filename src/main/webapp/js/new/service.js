/**
 * Created by Joo on 2016/7/28.
 */

/**
 * Created by Joo on 2016/7/28.
 */

var editServiceIndex = undefined;
function endServiceEditing(){
    if (editServiceIndex == undefined) {
        return true
    }
    if ($('#dgService').datagrid('validateRow', editServiceIndex)){
        $('#dgService').datagrid('endEdit', editServiceIndex);
        editServiceIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickServiceCell(index, field){
    if (editServiceIndex != index){
        if (endServiceEditing()){
            $('#dgService').datagrid('selectRow', index).datagrid('beginEdit', index);
            var ed = $('#dgService').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editServiceIndex = index;
        } else {
            setTimeout(function(){
                $('#dgService').datagrid('selectRow', editServiceIndex);
            },0);
        }
    }
}
function onEndServiceEdit(index, row){
    var ed = $('#dgService').datagrid('getEditor', {
        index: index,
        field: 'prj_name'
    });
    row.productname = $(ed.target).textbox('getText');
}
function appendService(){
    if (endServiceEditing()) {
        $('#dgService').datagrid('appendRow', {});
        editServiceIndex = $('#dgService').datagrid('getRows').length - 1;
        $('#dgService').datagrid('selectRow', editServiceIndex).datagrid('beginEdit', editServiceIndex);
    }
}
function removeService(){
    if (editServiceIndex == undefined){
        return
    }
    $('#dgService').datagrid('cancelEdit', editServiceIndex).datagrid('deleteRow', editServiceIndex);
    editServiceIndex = undefined;
}
function acceptService(){
    if (endServiceEditing()){
        $('#dgService').datagrid('acceptChanges');
    }
}
function rejectService(){
    $('#dgService').datagrid('rejectChanges');
    editServiceIndex = undefined;
}