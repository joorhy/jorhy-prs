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
    <title>待审批项目</title>
</head>
<body>
<div>
    <div class="easyui-panel" title="基础信息" style="width:100%">
        <table>
            <td align="left">
                <td style="width:15%">采购函编号</td>
                <td style="width:35%;"><input id="purc_code" class="easyui-textbox"/></td>
                <td style="width:15%;">资金来源</td>
                <td style="width:35%;"><input id="funds_src" class="easyui-textbox" /></td>
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
                <td style="width:35%;"><input id="funds_nature" class="easyui-textbox"/></td>
            </td>
        </table>
    </div>
</div>
<br/><br/>
<div region="center">
    <td align="left">
    <td>商品类预算总价</td>
    <td><input id="commodity_total_price" class="easyui-textbox" /></td>
    </td>
    <table id="dgCommodity" class="easyui-datagrid" title="商品类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/new/commodityList',method:'get',
               toolbar:tbCommodity">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">项目名称</th>
            <th data-options="field:'prj_count',width:80">数量</th>
            <th data-options="field:'prj_price',width:80">单价</th>
            <th data-options="field:'prj_spec',width:80">规格型号</th>
            <th data-options="field:'prj_total_price',width:80">预算总价</th>
            <th data-options="field:'prj_param',width:160">技术参数及售后</th>
            <th data-options="field:'prj_attach',width:80">相关附件</th>
        </tr>
        </thead>
    </table>
    <td align="left">
        <td style="width:15%;">服务类预算总价</td>
        <td style="width:35%;"><input id="service_total_price" class="easyui-textbox" /></td>
    </td>
    <table id="dgService" class="easyui-datagrid" title="服务类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/new/serviceList',method:'get',
               toolbar:tbService">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">项目名称</th>
            <th data-options="field:'prj_count',width:80">数量</th>
            <th data-options="field:'prj_price',width:80">单价</th>
            <th data-options="field:'prj_spec',width:80">规格型号</th>
            <th data-options="field:'prj_total_price',width:80">预算总价</th>
            <th data-options="field:'prj_param',width:160">技术参数及售后</th>
            <th data-options="field:'prj_attach',width:80">相关附件</th>
        </tr>
        </thead>
    </table>
    <td align="left">
    <td style="width:15%;">工程类预算总价</td>
    <td style="width:35%;"><input id="engineering_total_price" class="easyui-textbox" /></td>
    </td>
    <table id="dgEngineering" class="easyui-datagrid" title="工程类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/new/engineeringList',method:'get',
               toolbar:tbEngineering">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">项目名称</th>
            <th data-options="field:'prj_count',width:80">数量</th>
            <th data-options="field:'prj_price',width:80">单价</th>
            <th data-options="field:'prj_spec',width:80">规格型号</th>
            <th data-options="field:'prj_total_price',width:80">预算总价</th>
            <th data-options="field:'prj_param',width:160">技术参数及售后</th>
            <th data-options="field:'prj_attach',width:80">相关附件</th>
        </tr>
        </thead>
    </table>
</div>
<br/><br/>
<div style="padding:0px 0px 0px 0px">
    <div class="easyui-panel" title="附件" style="width:100%;height:225px;">
    </div>
</div>
<br/><br/>
<div>
    <table id="dgOpinionLst" class="easyui-datagrid" title="已经审批单位意见" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/new/serviceList',method:'get',
                   toolbar:tbService">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">单位</th>
            <th data-options="field:'prj_count',width:80">意见</th>
            <th data-options="field:'prj_price',width:80">审批人</th>
            <th data-options="field:'prj_spec',width:80">审批日期</th>
            <th data-options="field:'prj_total_price',width:80">备注</th>
        </tr>
        </thead>
    </table>
</div>
<br/><br/>
<div>
    <td align="left">
        <td style="width:15%;">审批意见</td><br/>
        <td >
            <input id="opinion" class="easyui-textbox" multiline="true"
                   style="width:100%;height:200px"/>
        </td>
    </td>
</div>
<br/><br/>
<div align="right">
    <td align="right">
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:120"
               onclick="savePrj()">同意并提交审核</a></td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
               onclick="submitPrj()">不同意</a></td>
    </td>
</div>
<!--/form-->
<div id="tbCommodity" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true"
       onclick="editCommodity()">查看</a>
</div>
<div id="tbService" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true"
       onclick="appendService()">查看</a>
</div>
<div id="tbEngineering" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true"
       onclick="appendEngineering()">查看</a>
</div>

<div id="dlg" class="easyui-dialog" style="width:400px"
     closed="true" buttons="#dlg-buttons">
    <!--form id="fm" method="post" novalidate style="margin:0;padding:20px 50px"-->
    <div style="margin:0;padding:20px 50px">
        <div style="margin-bottom:10px">
            <td>项目名称</td>
            <input id="prj_name" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>数量</td>
            <input id="prj_count" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>单价</td>
            <input id="prj_price" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>规格型号</td>
            <input id="prj_spec" class="easyui-textbox" required="true" multiline="true"
                   style="width:100%;height:40px">
        </div>
        <div style="margin-bottom:10px">
            <td>技术参数及售后</td>
            <input id="prj_param" class="easyui-textbox" required="true" multiline="true" label="Email:"
                   style="width:100%;height:120px">
        </div>
    </div>
    <!--/form-->
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCommodity()"
       style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<!--/div-->
</body>
</html>
