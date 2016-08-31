<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/31
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="easyui-panel" title="附件" style="width:100%;height:225px;"
     data-options="fit:false,border:true,onLoad:onLoadAttachFiles()">
    <input id="page_type" hidden="true" value="package"/>
    <div id="fileList"></div>
</div>
</body>
</html>

