/**
 * Created by Joo on 2016/7/28.
 */

function addCommodity(){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增项目');
    $('#fm').form('clear');
    //url = 'save_user.php';
}

function editCommodity(){
    var row = $('#dgCommodity').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑项目');
        $('#fm').form('load',row);
       //url = 'update_user.php?id='+row.id;
    }
}

function saveCommodity(){
    $('#fm').form('submit',{
        url: "",
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            //var result = eval('('+result+')');
            //if (result.errorMsg){
            //    $.messager.show({
            //        title: 'Error',
            //        msg: result.errorMsg
            //    });
            //} else {
            //    $('#dlg').dialog('close');        // close the dialog
            //    $('#dg').datagrid('reload');    // reload the user data
            //}
        }
    });
}

function removeCommodity(){
    var row = $('#dgCommodity').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
            if (r){
                $.post('destroy_user.php',{id:row.id},function(result){
                    if (result.success){
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    }
                },'json');
            }
        });
    }
}
