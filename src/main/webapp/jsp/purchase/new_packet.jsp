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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>新建分包</title>
</head>
<body>
<div>
    <div class="easyui-panel" title="基础信息" style="width:100%" data-options="onLoad:onLoadCreatePacket()">
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
<table id="tbToDivide" class="easyui-datagrid" title="未分包项目" style="width:100%;height:500px"
       data-options="
                fitColumns:true,
                url:'/purchase/toDivideItems',
                method:'post',
                queryParams:{purchasing_id:document.getElementById('purchasing_id').value},
                view:groupview,
                groupField:'product_type',
                groupFormatter:function(value,rows){
                    return value;
                }
            ">
    <thead>
    <tr>
        <th data-options="field:'project_id',hidden:true"></th>
        <th data-options="field:'product_type',hidden:true"></th>
        <th data-options="field:'prj_name',width:80">项目名称</th>
        <th data-options="field:'prj_count',width:80">数量</th>
        <th data-options="field:'prj_price',width:80">单价</th>
        <th data-options="field:'prj_spec',width:80">规格型号</th>
        <th data-options="field:'prj_pre_price',width:80">预算总价</th>
        <th data-options="field:'prj_param',width:160">技术参数及售后</th>
        <th data-options="field:'prj_select',checkbox:true"></th>
    </tr>
    </thead>
</table><br/>
<div style="padding:0px 0px 0px 0px">
    <div class="easyui-panel" title="附件" style="width:100%;height:225px;"
         data-options="fit:false,border:true,onLoad:onLoadAttachCreate()">
        <jsp:include page="../common/attach_file.jsp"/>
    </div>
</div><br/>
<div align="right">
    <td align="right">
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="savePacket()">保存</a></td>
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="cancelPacket()">取消</a></td>
    </td>
</div>
</body>
</html>
