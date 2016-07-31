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
    <link rel="stylesheet" href="../js/plupload-2.1.9/js/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css"></link>
    <script type="text/javascript" src="../js/jquery-3.1.0/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../js/plupload-2.1.9/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="../js/plupload-2.1.9/js/plupload.dev.js"></script>
    <script type="text/javascript" src="../js/plupload-2.1.9/js/i18n/zh_CN.js"></script>
    <script type="text/javascript" src="../js/plupload-2.1.9/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<body style="padding: 0;margin: 0;">
<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
<br />

<div id="container">
    <a id="pickfiles" href="javascript:;">[Select files]</a>
    <a id="uploadfiles" href="javascript:;">[Upload files]</a>
</div>

<br />
<pre id="console"></pre>


<script type="text/javascript">
    // Custom example logic

    var uploader = new plupload.Uploader({
        runtimes : 'html5,flash,silverlight,html4',

        browse_button : 'pickfiles', // you can pass in id...
        container: document.getElementById('container'), // ... or DOM Element itself

        url : "/examples/upload",

        filters : {
            max_file_size : '10mb',
            mime_types: [
                {title : "Image files", extensions : "jpg,gif,png"},
                {title : "Zip files", extensions : "zip"}
            ]
        },

        // Flash settings
        flash_swf_url : '/plupload/js/Moxie.swf',

        // Silverlight settings
        silverlight_xap_url : '/plupload/js/Moxie.xap',


        init: {
            PostInit: function() {
                document.getElementById('filelist').innerHTML = '';

                document.getElementById('uploadfiles').onclick = function() {
                    uploader.start();
                    return false;
                };
            },

            FilesAdded: function(up, files) {
                plupload.each(files, function(file) {
                    document.getElementById('filelist').innerHTML += '<a id="' + file.id + '"  href="javascript:;">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></a>';
                });
            },

            UploadProgress: function(up, file) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
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

