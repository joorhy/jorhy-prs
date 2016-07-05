<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="easyui_header.jsp"/>
    <title>登陆欢迎页</title>
    <style>
        html,body{text-align:center;margin:0px auto;width:1200px;}
    </style>
</head>
<body>
    <div id="header" style="min-height:100px;width:100%;border:1px green solid;"><jsp:include page="header.jsp"/></div>

    <div class="center" style="width:100%;min-height:500px;border:1px darkslategray solid;">
        <div  name="left" style="float:left;border:1px red solid;text-align: left;width:280px;height:480px;">
            <jsp:include page="left_menu.jsp"/></div>
        <div name="right" style="position: relative;float:left;border:1px blue solid;width:900px;height:480px;">
            <jsp:include page="to_approve.jsp"/></div>
    </div>

    <div id="footer" style="width:100%;border:1px goldenrod solid;"><jsp:include page="footer.jsp"/></div>

</body>
</html>
