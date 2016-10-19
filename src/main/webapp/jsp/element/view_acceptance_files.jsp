<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/10/19
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="easyui-panel" title="验收报告" style="width:100%;height:225px;"
     data-options="fit:false,border:true,onLoad:onLoadAttachFiles()">
    <input id="page_type" hidden="true" value="acceptance"/>
    <div id="fileList"></div>
</div>
</body>
</html>
