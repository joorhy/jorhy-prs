/**
 * Created by JooLiu on 2016/8/22.
 */
function onLoadAttachCreate() {
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
                    '(' + plupload.formatSize(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:false" ' +
                    'onclick=delAttachFile(' + file.id + ')> 删除</a><br/></div>';
            }
        },
        error: function (x, e) {
            alert("error");
        }
    });
}
var uploader = new plupload.Uploader({
    runtimes : 'html5,html4',
    browse_button : 'pickFiles', // you can pass in id...
    container: document.getElementById('container'), // ... or DOM Element itself
    url : "/applicant/uploadFile",
    filters : {
        max_file_size : '10mb',
        mime_types: [
            {title : "Image files", extensions : "jpg,gif,png"},
            {title : "Zip files", extensions : "zip"}
        ]
    },
    unique_names: true,
    init: {
        PostInit: function() {
            //document.getElementById('fileList').innerHTML = '';
        },
        FilesAdded: function(up, files) {
            plupload.each(files, function(file) {
                uploader.settings.url = "/applicant/uploadFile?file_id=" + file.id +
                    "&purchasing_id=" + document.getElementById("purchasing_id").value;
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/common/downloadFile?file_id=' + file.id + '&purchasing_id=' +
                    document.getElementById("purchasing_id").value + '> ' + file.name +
                    '(' + plupload.formatSize(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:true" ' +
                    'onclick=delAttachFile(' + file.id + ')> 删除</a><br/></div>';
            });
            uploader.start();
        },

        FilesRemoved: function(up, files) {

        },

        UploadProgress: function(up, file) {
            document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent
                + "%</span>";
        },

        FileUploaded: function(up, file, resp) {

        },

        Error: function(up, err) {
            document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
        }
    }
});
uploader.init();