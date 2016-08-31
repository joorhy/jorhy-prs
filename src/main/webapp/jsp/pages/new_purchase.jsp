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
    <div id="page_type" value="purchase" hidden="true"></div>
    <div>
        <jsp:include page="../element/purchase_base.jsp"/>
    </div><br/>
    <div>
        <jsp:include page="../element/new_purchase_products.jsp"/>
    </div><br/>
    <div>
       <jsp:include page="../element/attach_purchase_file.jsp"/>
    </div>
    <div>
        <div align="right">
            <td align="right">
            <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
                   onclick="savePurchase()">保存</a></td>
            <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
                   onclick="submitPurchase()">提交审核</a></td>
            <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
                   onclick="cancelPurchase()">撤销</a></td>
            </td>
        </div>
    </div>
</body>
</html>