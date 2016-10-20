<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/30
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验收项目</title>
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
</div><br/>
<div>
    <jsp:include page="../element/attach_acceptance_file.jsp"/>
</div><br/>
<div align="right">
    <td align="right">
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="approvePackage()">提交审核</a></td>
    </td>
</div>
</body>
</html>
