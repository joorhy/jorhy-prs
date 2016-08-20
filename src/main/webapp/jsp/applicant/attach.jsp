<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/7/30
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <script type="text/javascript" src="../../js/plupload-2.1.9/js/plupload.full.min.js"></script>
        <script type="text/javascript" src="../../js/plupload-2.1.9/js/plupload.dev.js"></script>
        <script type="text/javascript" src="../../js/plupload-2.1.9/js/i18n/zh_CN.js"></script>
        <script type="text/javascript" src="../../js/plupload-2.1.9/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
    </head>
<body>
<div id="container">
    <a id='pickFiles' href="javascript:void(0)" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true">添加附件</a>
</div>
<div id="fileList"></div>
<br/>
<pre id="console"></pre>

<script type="text/javascript">
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
</script>
</body>
</html>

