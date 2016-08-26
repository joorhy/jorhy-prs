<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/20
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <div class="easyui-panel" title="基础信息" style="width:100%" data-options="onLoad:onLoadPurchase()">
        <table>
            <input id="purchasing_id" type="hidden" value=""/></td>
            <td align="left">
            <td style="width:15%">采购函编号</td>
            <td style="width:35%;"><input id="pur_code" class="easyui-textbox"/></td>
            <td style="width:15%;">资金来源</td>
            <td style="width:35%;"><input id="funds_src" class="easyui-textbox"/></td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%;">联系人</td>
            <td style="width:35%;"><input id="contacts" class="easyui-textbox"/></td>
            <td style="width:15%;">联系电话</td>
            <td style="width:35%;"><input id="phone_num" class="easyui-textbox"/></td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%;">资金性质</td>
            <td style="width:35%;">
                <select id="funds_nature" class="easyui-combobox" style="width:50%;"">
                    <option value="ncys" selected>年初预算</option>
                    <option value="zxzj">专项资金</option>
                    <option value="zczj">自筹资金</option>
                    <option value="jzzj">捐赠资金</option>
                </select>
            </td>
            </td>
        </table>
    </div>
</body>
</html>
