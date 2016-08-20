<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/20
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</div>
<div id="fileList"></div>
<br/>
<pre id="console"></pre>

<script type="text/javascript">
    function loadAttachFile2() {
        $.ajax({
            type: 'post',
            url:'/applicant/getAttachFiles',
            data: {purchasing_id:document.getElementById("purchasing_id").value},
            dataType: 'json',
            success: function (data) {
                for(var key in data.files) {
                    var file = data.files[key];
                    document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                            '<a href=/applicant/downloadFile?file_id=' + file.id + '&purchasing_id=' +
                            document.getElementById("purchasing_id").value + '> ' + file.name +
                            '(' + plupload.formatSize(file.size) + ')</a> <b></b><br/></div>';
                }
            },
            error: function (x, e) {
                alert("error");
            }
        });
    }
</script>
</body>
</html>
