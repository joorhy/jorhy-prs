/**
 * Created by Joo on 2016/8/20.
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