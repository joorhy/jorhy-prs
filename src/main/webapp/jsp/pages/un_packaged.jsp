<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/25
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>新建分包</title>
</head>
<body>
<div>
    <jsp:include page="../element/package_base.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/packaged_products.jsp"/>
</div>
<div>
    <jsp:include page="../element/view_package_files.jsp"/>
</div>
<div align="right">
    <td align="right">
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="rePackage()">重新分包</a></td>
    </td>
</div>
</body>
</html>
