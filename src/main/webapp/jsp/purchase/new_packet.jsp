<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/23
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic TextBox - jQuery EasyUI Demo</title>
    <link rel="stylesheet" href="../../js/jquery-easyui-1.4.5/demo/demo.css" type="text/css">
    <link rel="stylesheet" href="../../js/jquery-easyui-1.4.5/themes/icon.css" type="text/css">
    <link rel="stylesheet" href="../../js/jquery-easyui-1.4.5/themes/default/easyui.css" type="text/css">
    <script type="text/javascript" src="../../js/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<h2>Basic TextBox</h2>
<p>The textbox allows a user to enter information.</p>
<div style="margin:20px 0;"></div>
<div class="easyui-panel" title="Register" style="width:100%;max-width:400px;padding:30px 60px;">
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" labelPosition="top" data-options="prompt:'Enter a email address...',validType:'email',label:'Email:'" style="width:100%;height:52px">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" style="width:100%;height:52px" data-options="label:'你好',labelPosition:'top'">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" label="Last Name:" labelPosition="top" style="width:100%;height:52px">
    </div>
    <div style="margin-bottom:20px">
        <input id='t1' type="text" style="width:100%;height:52px">
    </div>

    <div>
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:100%;height:32px">Register</a>
    </div>
</div>
<script>
    $('#t1').textbox({
        width: 300,
        label: 'Name:'
    });
</script>
</body>
</html>
<!--html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>新建分包</title>
</head>
<body>
<div>
    <div class="easyui-panel" title="基础信息" style="width:100%">
        <table>
            <input id="packet_id" type="hidden" value=""/></td>
            <td align="left">
                <td style="width:15%">采购文号</td>
                <td style="width:35%;"><input id="pack_code" class="easyui-textbox"
                                              label="First Name:" labelPosition="top"/></td>
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
</div><br/>
</body>
</html-->
