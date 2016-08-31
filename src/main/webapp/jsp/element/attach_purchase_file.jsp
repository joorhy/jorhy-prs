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
    </head>
<body>
<div class="easyui-panel" title="附件" style="width:100%;height:225px;"
     data-options="fit:false,border:true,onLoad:onLoadAttachFiles()">
    <input id="page_type" hidden="true" value="purchase"/>
    <div id="container">
        <a id='pickFiles' href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-add',plain:true">添加附件</a>
    </div>
    <div id="fileList"></div>
    <br/>
    <pre id="console"></pre>
</div>
</body>
</html>

