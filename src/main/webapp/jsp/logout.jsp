<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/6/22
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>注销页面 - JAVAWEB.CC</title>
</head>
<body>
注销前 SessionID 为：<%= session.getId() %> <br /> 属性为：<%= session.getAttribute("userid") %> <br />
<%
    response.setHeader("refresh", "2; URL = login.jsp");  // 定时跳转
    session.invalidate(); // 注销 session
%>
注销后 SessionID 为：<%= session.getId() %> 属性为： <br />
<h3>您已成功推出本系统，两秒钟后跳转到登陆页</h3>
<h3>如果没有自动跳转，请点击<a href="login.jsp">这里</a></h3>
</body>
</html>