/**
 * Created by Joo on 2016/7/28.
 */
/**
 * Created by Joo on 2016/7/28.
 */

var editEngineeringIndex = undefined;
function endEngineeringEditing(){
    if (editEngineeringIndex == undefined) {
        return true
    }
    if ($('#dgEngineering').datagrid('validateRow', editEngineeringIndex)){
        $('#dgEngineering').datagrid('endEdit', editEngineeringIndex);
        editEngineeringIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickEngineeringCell(index, field){
    if (editEngineeringIndex != index){
        if (endEngineeringEditing()){
            $('#dgEngineering').datagrid('selectRow', index).datagrid('beginEdit', index);
            var ed = $('#dgEngineering').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editEngineeringIndex = index;
        } else {
            setTimeout(function(){
                $('#dgEngineering').datagrid('selectRow', editEngineeringIndex);
            },0);
        }
    }
}
function onEndEngineeringEdit(index, row){
    var ed = $('#dgEngineering').datagrid('getEditor', {
        index: index,
        field: 'prj_name'
    });
    row.productname = $(ed.target).textbox('getText');
}
function appendEngineering(){
    if (endEngineeringEditing()) {
        $('#dgEngineering').datagrid('appendRow', {});
        editEngineeringIndex = $('#dgEngineering').datagrid('getRows').length - 1;
        $('#dgEngineering').datagrid('selectRow', editEngineeringIndex).datagrid('beginEdit', editEngineeringIndex);
    }
}
function removeEngineering(){
    if (editEngineeringIndex == undefined){
        return
    }
    $('#dgEngineering').datagrid('cancelEdit', editEngineeringIndex).datagrid('deleteRow', editEngineeringIndex);
    editEngineeringIndex = undefined;
}
function acceptEngineering(){
    if (endEngineeringEditing()){
        $('#dgEngineering').datagrid('acceptChanges');
    }
}
function rejectEngineering(){
    $('#dgEngineering').datagrid('rejectChanges');
    editEngineeringIndex = undefined;
}