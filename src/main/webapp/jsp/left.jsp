<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/7/1
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="easyui_header.jsp"/>
    <title>Left</title>

</head>
<body>
    <div>
        <ul id="myTree" class="easyui-tree" data-options="url:'../js/prsTree.json',method:'get',animate:true"></ul>
    </div>
</body>
</html>
