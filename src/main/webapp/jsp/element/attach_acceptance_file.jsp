<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/10/19
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
<div class="easyui-panel" title="验收报告" style="width:100%;height:225px;"
     data-options="fit:false,border:true,onLoad:onLoadAttachFiles()">
    <input id="page_type" hidden="true" value="acceptance"/>
    <div id="container">
        <a id='pickFiles' href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-add',plain:true">添加验收报告</a>
    </div>
    <div id="fileList"></div>
    <br/>
    <pre id="console"></pre>
</div>
</body>
</html>
