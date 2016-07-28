/**
 * Created by Joo on 2016/7/28.
 */

var editCommodityIndex = undefined;
function endCommodityEditing(){
    if (editCommodityIndex == undefined) {
        return true
    }
    var ed = $('#dgCommodity').datagrid('getEditor', {
        index: editCommodityIndex,
        field: 'prj_name'
    });
    if ($(ed.target).textbox('getText') == "") {
        return false;
    }

    if ($('#dgCommodity').datagrid('validateRow', editCommodityIndex)){
        $('#dgCommodity').datagrid('endEdit', editCommodityIndex);
        editCommodityIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCommodityCell(index, field){
    if (editCommodityIndex != index){
        if (endCommodityEditing()){
            $('#dgCommodity').datagrid('selectRow', index).datagrid('beginEdit', index);
            var ed = $('#dgCommodity').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editCommodityIndex = index;
        } else {
            setTimeout(function(){
                $('#dgCommodity').datagrid('selectRow', editCommodityIndex);
            },0);
        }
    }
}
function onEndCommodityEdit(index, row){
    var ed = $('#dgCommodity').datagrid('getEditor', {
        index: index,
        field: 'prj_name'
    });
    row.productname = $(ed.target).textbox('getText');
}
function appendCommodity(){
    if (endCommodityEditing()) {
        $('#dgCommodity').datagrid('appendRow', {});
        editCommodityIndex = $('#dgCommodity').datagrid('getRows').length - 1;
        $('#dgCommodity').datagrid('selectRow', editCommodityIndex).datagrid('beginEdit', editCommodityIndex);
    }
}
function removeCommodity(){
    if (editCommodityIndex == undefined){
        return
    }
    $('#dgCommodity').datagrid('cancelEdit', editCommodityIndex).datagrid('deleteRow', editCommodityIndex);
    editCommodityIndex = undefined;
}
function acceptCommodity(){
    if (endCommodityEditing()){
        $('#dgCommodity').datagrid('acceptChanges');
    }
}
function rejectCommodity(){
    $('#dgCommodity').datagrid('rejectChanges');
    editCommodityIndex = undefined;
}