<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/23
  Time: 9:50
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
    <jsp:include page="../element/purchase_base.jsp"/>
</div>
<br/><br/>
<div>
    <jsp:include page="../element/view_purchase_products.jsp"/>
</div>
<br/><br/>
<div>
    <jsp:include page="../element/view_purchase_files.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/purchase_opinion.jsp"/>
</div><br/>
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
        <td>采购性质</td>
        <td>
            <select id="purchase_nature" class="easyui-combobox" style="width:120px"
                    data-options="
                    valueField: 'id',
                    textField: 'text',
                    url: '/purchase/purchase_nature'">
            </select>
        </td>
    </td>
    <td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:120"
               onclick="agreePurchase()">同意并提交执行</a></td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
               onclick="disagreePurchase()">不同意</a></td>
    </td>
</div><br/>
</body>
</html>
