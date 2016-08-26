<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/23
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>待审批项目</title>
</head>
<body>
<div>
    <jsp:include page="common/purchase_base.jsp"/>
</div>
<br/><br/>
<div>
    <jsp:include page="view_purchase_products.jsp"/>
</div>
<br/><br/>
<div>
    <jsp:include page="common/view_files.jsp"/>
</div><br/>
<div>
    <jsp:include page="common/opinion.jsp"/>
</div>
<div>
    <td align="left">
    <td style="width:15%;">审批意见</td><br/>
    <td >
        <input id="opinion" class="easyui-textbox" multiline="true"
               style="width:100%;height:200px"/>
    </td>
    </td>
</div><br/>
<div align="right">
    <td>
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:120"
           onclick="agreePurchase()">同意</a></td>
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="disagreePurchase()">不同意</a></td>
    </td>
</div>
</body><br/>
</html>
