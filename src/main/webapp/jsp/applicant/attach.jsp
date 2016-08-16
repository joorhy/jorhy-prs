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
<div id="fileList"></div>
<div id="container"></div>

<!--br/>
<pre id="console"></pre-->
<script type="text/javascript">
    // Custom example logic
    /*var uploader = new plupload.Uploader({
        runtimes : 'html5,html4',
        browse_button : 'pickfiles', // you can pass in id...
        container: document.getElementById('container'), // ... or DOM Element itself
        url : "/applicant/uploadFile",
        filters : {
            max_file_size : '10mb',
            mime_types: [
                {title : "Image files", extensions : "jpg,gif,png"},
                {title : "Zip files", extensions : "zip"}
            ]
        },
        init: {
            PostInit: function() {
                document.getElementById('fileList').innerHTML = '';
                ///document.getElementById('uploadfiles').onclick = function() {
                //    uploader.start();
                //    return false;
                //};
            },

            FilesAdded: function(up, files) {
                plupload.each(files, function(file) {
                    //document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' + file.name +
                    //' (' + plupload.formatSize(file.size) + ') <b></b></div>';
                    //document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">';
                    document.getElementById('fileList').innerHTML += '<a id="' + file.id + '"  href="javascript:;">'
                            + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></a>&nbsp;';
                    document.getElementById('fileList').innerHTML += '<a id="uploadFiles" href="javascript:void(0)" ' +
                            'class="easyui-linkbutton" data-options="iconCls:\'icon-remove\',plain:true" ' +
                            'onclick="delAttachFile()">删除</a>';
                    //document.getElementById('fileList').innerHTML += '<b></b></div><br/>';
                });
                uploader.start();
                alert("start");
            },

            UploadProgress: function(up, file) {
                //alert("progress");
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent
                        + "%</span>";
            },

            Error: function(up, err) {
                //document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
            }
        }
    });
    uploader.init();*/
</script>
</body>
</html>

