<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/7/28
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title></title>
</head>
<body>
    <div>
        <jsp:include page="common/purchase_base.jsp"/>
    </div><br/>
    <div>
        <jsp:include page="new_purchase_products.jsp"/>
    </div><br/>
    <div>
       <jsp:include page="common/attach_file.jsp"/>
    </div>
    <div>
        <jsp:include page="active_purchase.jsp"/>
    </div>
</body>
</html>