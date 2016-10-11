<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="includes.jsp"/>
    <title>采购过程管理平台</title>
    <style>
        html,body{text-align:center;margin:0px auto;width:1200px;}
    </style>
</head>
<body>
<div id="main_layout" class="easyui-layout" style="width:1200px;height:100%;">
    <div id="previewDiv" class="easyui-panel" region="center" title="简介"
         style="padding:5px;width:100%;">
        <jsp:include page="pages/purchase_print_preview.jsp" />
    </div>
</div>
</html>
