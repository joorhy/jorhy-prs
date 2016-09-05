<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/25
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div>
    <div class="easyui-panel" title="基础信息" style="width:100%" data-options="onLoad:onLoadPackage()">
        <table>
            <input id="package_id" type="hidden" value=""/>
            <td align="left">
            <td style="width:15%">采购文号</td>
            <td style="width:35%;"><input id="pack_code" class="easyui-textbox"/></td>
            <td style="width:15%;">采购地点</td>
            <td style="width:35%;"><input id="pur_address" class="easyui-textbox" /></td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%;">抽取专家人数</td>
            <td style="width:35%;"><input id="expert_count" class="easyui-textbox"/></td>
            <td style="width:15%;">采购日期</td>
            <td style="width:35%;"><input id="pur_date" class="easyui-datebox"></td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%;">采购方式</td>
            <td style="width:35%;">
                <select id="pur_method" class="easyui-combobox" style="width:50%;">
                    <option value="gkzb">公开招标</option>
                    <option value="xjcg">询价采购</option>
                    <option value="jzxtp">竞争性谈判</option>
                    <option value="jzxcs">竞争性磋商</option>
                    <option value="dyly">单一来源</option>
                    <option value="sczg">商场直购</option>
                    <option value="wsjj">网上竞价</option>
                    <option value="zxcg">自行采购</option>
                </select>
            </td>
            <td style="width:15%;">采购需求公告</td>
            <td style="width:35%;">
                <input id="pur_publicity" class="easyui-switchbutton" style="width: 50%;"
                       data-options="onText:'是',offText:'否'">
            </td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%">中标供应商</td>
            <td style="width:35%;"><input id="pur_supplier" class="easyui-textbox"/></td>
            <td style="width:15%;">中标金额</td>
            <td style="width:35%;"><input id="pur_amount" class="easyui-textbox" /></td>
            </td>
        </table>
    </div>
</div>
</body>
</html>
