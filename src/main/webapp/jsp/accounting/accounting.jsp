<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="../easyui_header.jsp"/>
    <title>采购过程管理平台</title>
    <style>
        html,body{text-align:center;margin:0px auto;width:1200px;}
    </style>
</head>
<body>
<div id="header" style="min-height:100px;width:100%;border:1px green solid;"><jsp:include page="../header.jsp"/></div>
<div class="easyui-layout" style="width:1200px;height:100%;">
    <div region="west" split="true" title="导航" style="width:250px;">
        <ul id="menuTree" class="easyui-tree" data-options="url:'../js/accountingTree.json',method:'get',animate:true"></ul>
    </div>
    <div id="contentDiv" class="easyui-panel" region="center" title="详细信息" style="padding:5px;width:100%;">
        <jsp:include page="to_approve.jsp" />
    </div>
</div>
<div id="footer" style="width:100%;border:1px goldenrod solid;"><jsp:include page="../footer.jsp"/></div>
</body>
</html>
