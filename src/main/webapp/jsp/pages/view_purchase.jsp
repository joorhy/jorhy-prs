<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/19
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>已提交采购过程</title>
</head>
<body>
    <div>
        <jsp:include page="../element/purchase_base.jsp"/>
    </div><br/>
    <div>
        <jsp:include page="../element/view_purchase_products.jsp"/>
    </div><br/>
    <div>
        <jsp:include page="../element/view_purchase_files.jsp"/>
    </div><br/>
    <div>
        <jsp:include page="../element/purchase_opinion.jsp"/>
    </div>
</body>
</html>
