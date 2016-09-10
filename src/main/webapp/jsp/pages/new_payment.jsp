<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/9/10
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <jsp:include page="../element/payment_base.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/payment_applicant_table.jsp"/>
</div>
<div align="right">
    <td align="right">
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="repacket()">申请支付</a></td>
    </td>
</div>
</body>
</html>
