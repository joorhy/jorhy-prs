<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>登陆欢迎页</title>
    <style>
        html,body{text-align:center;margin:0px auto;width:1024px;}
    </style>
</head>
<body>
    <div id="header" style="min-height:100px;"><jsp:include page="header.jsp"/></div>

    <div class="center" style="width:100%;min-height:500px;">
        <iframe width=200px height=330 src="/jsp/left.jsp" name="left"></iframe>
        <iframe width=800px height=330 src="/jsp/right.jsp" name="right"></iframe>
    </div>

    <div id="footer"><jsp:include page="footer.jsp"/></div>

</body>
</html>
