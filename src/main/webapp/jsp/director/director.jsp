<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/13
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="../../js/common/common.js"></script>
    <script type="text/javascript" src="../../js/director/director.js"></script>
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
        <ul id="menuTree" class="easyui-tree" data-options="url:'/director/directorTree',method:'get',
        animate:true,onClick: function(node) { showContent(node); }"></ul>
    </div>
    <div id="contentDiv" class="easyui-panel" region="center" title="简介" style="padding:5px;width:100%;">
        <jsp:include page="welcome.jsp" />
    </div>
</div>
<div id="footer" style="width:100%;border:1px goldenrod solid;"><jsp:include page="../footer.jsp"/></div>
</body>
</html>
