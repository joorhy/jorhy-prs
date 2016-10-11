<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <span id="print_area">
        <h1 id="preview_title" style="width:100%;text-align:center"></h1>
        <div>
            <jsp:include page="../element/purchase_preview_base.jsp"/>
        </div><br/>
        <div>
            <jsp:include page="../element/purchase_preview_opinion.jsp"/>
        </div><br/>
        <div>
            <jsp:include page="../element/purchase_preview_products.jsp"/>
        </div>
    </span>
</body>
</html>
