<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="easyui-panel" title="基础信息" style="width:100%" data-options="onLoad:onLoadPreviewPurchase()">
    <table>
        <input id="purchase_id" type="hidden" value=""/></td>
        <td align="left">
        <td style="width:15%">采购项目名称</td>
        <td style="width:35%"><u id="preview_pur_name"></u></td>
        <td style="width:15%">采购函编号</td>
        <td style="width:35%;"><input id="preview_pur_code" class="easyui-textbox"/></td>
        </td>
        <tr/>
        <td align="left">
        <td style="width:15%;">资金来源</td>
        <td style="width:35%;"><input id="preview_funds_src" class="easyui-textbox"/></td>
        <td style="width:15%;">资金性质</td>
        <td style="width:35%;">
            <select id="preview_funds_nature" class="easyui-combobox" style="width:50%;"
                    data-options="
                    valueField: 'id',
                    textField: 'text',
                    url: '/purchase/funds_nature'">
            </select>
        </td>
        </td>
        <tr/>
        <td align="left">
        <td style="width:15%;">联系人</td>
        <td style="width:35%;"><input id="preview_contacts" class="easyui-textbox"/></td>
        <td style="width:15%;">联系电话</td>
        <td style="width:35%;"><input id="preview_phone_num" class="easyui-textbox"/></td>
        </td>
    </table>
</div>
</body>
</html>
