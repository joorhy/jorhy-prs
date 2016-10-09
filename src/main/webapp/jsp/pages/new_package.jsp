<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/23
  Time: 22:23
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
        <jsp:include page="../element/unpackaged_products.jsp"/>
    </div><br/>
    <div>
        <jsp:include page="../element/attach_package_file.jsp"/>
    </div><br/>
    <div align="right">
        <td align="right">
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
               onclick="savePackage()">保存</a></td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
               onclick="submitPackage()">提交分包</a></td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
               onclick="cancelPackage()">撤销消</a></td>
        </td>
    </div>
</body>
</html>
