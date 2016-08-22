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
        <script type="text/javascript" src="../../js/plupload-2.1.9/js/jquery.plupload.queue/jquery.plupload.queue.js">
        </script>
        <script type="text/javascript" src="../../js/applicant/attach.js"></script>
    </head>
<body>
<div id="container">
    <a id='pickFiles' href="javascript:void(0)" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true">添加附件</a>
</div>
<div id="fileList"></div>
<br/>
<pre id="console"></pre>
</body>
</html>

