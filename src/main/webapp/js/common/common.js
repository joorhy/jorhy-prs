/**
 * Created by Joo on 2016/8/20.
 */
function getReadableFileSizeString(fileSizeInBytes) {
    var i = -1;
    var byteUnits = [' kb', ' mb', ' gb', ' tb', 'pb', 'eb', 'zb', 'yb'];
    do {
        fileSizeInBytes = fileSizeInBytes / 1024;
        i++;
    } while (fileSizeInBytes > 1024);

    return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
};

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
        data: {purchasing_id:document.getElementById("purchasing_id").value},
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