<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/9/1
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-panel" title="基础信息" style="width:100%" data-options="onLoad:onLoadPurchase()">
    <table>
        <input id="purchase_id" type="hidden" value=""/></td>
        <td align="left">
            <td style="width:15%">申请单位</td>
            <td style="width:35%;"><input id="pay_applicant" class="easyui-textbox"/></td>
            <td style="width:15%;">项目名称</td>
            <td style="width:35%;"><input id="pay_pur_name" class="easyui-textbox"/></td>
        </td>
        <tr/>
        <td align="left">
            <td style="width:15%;">采购函文号</td>
            <td style="width:35%;"><input id="pay_pur_code" class="easyui-textbox"/></td>
            <td style="width:15%;">预算总价</td>
            <td style="width:35%;"><input id="pay_package_budget" class="easyui-textbox"/></td>
        </td>
        <tr/>
        <td align="left">
            <td style="width:15%;">中标金额</td>
            <td style="width:35%;"><input id="pay_bid_amount" class="easyui-textbox"/></td>
            <td style="width:15%;">增量金额</td>
            <td style="width:35%;"><input id="pay_increment_amount" class="easyui-textbox"/></td>
        </td>
        <tr/>
        <td align="left">
            <td style="width:15%;">应付款总金额</td>
            <td style="width:35%;"><input id="pay_payables" class="easyui-textbox"/></td>
            <td style="width:15%;">已支付金额</td>
            <td style="width:35%;"><input id="pay_paid" class="easyui-textbox"/></td>
        </td>
        <tr/>
        <td align="left">
            <td style="width:15%;">供应商户名</td>
            <td style="width:35%;"><input id="pay_supplier_name" class="easyui-textbox"/></td>
            <td style="width:15%;">供应商开户行</td>
            <td style="width:35%;"><input id="pay_supplier_bank" class="easyui-textbox"/></td>
        </td>
        <tr/>
        <td align="left">
        <td style="width:15%;">供应商户名</td>
        <td style="width:35%;"><input id="pay_supplier_account" class="easyui-textbox"/></td>
        </td>
    </table>
</div>
</body>
</html>
