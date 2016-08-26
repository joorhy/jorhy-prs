<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/26
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<table id="dgCommodity" class="easyui-datagrid" title="商品类" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/common/commodityList',method:'post',
                   queryParams:{purchasing_id:document.getElementById('purchasing_id').value},
                   toolbar:tbCommodity">
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
    </tr>
    </thead>
</table>
<table id="dgService" class="easyui-datagrid" title="服务类" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/common/serviceList',method:'post',
                   queryParams:{purchasing_id:document.getElementById('purchasing_id').value},
                   toolbar:tbService">
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
    </tr>
    </thead>
</table>
<table id="dgEngineering" class="easyui-datagrid" title="工程类" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/common/engineeringList',method:'post',
                   queryParams:{purchasing_id:document.getElementById('purchasing_id').value},
                   toolbar:tbEngineering">
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
    </tr>
    </thead>
</table>
<div id="tbCommodity" style="height:auto">
    <td align="left">
    <td>商品类预算总价</td>
    <td><input id="commodity_pre_price" class="easyui-textbox"/></td>
    <td>商品类实际总价</td>
    <td><input id="commodity_total_price" class="easyui-textbox"/></td>
    </td>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
       onclick="viewPurchaseProductItem('commodity')">查看</a>
</div>
<div id="tbService" style="height:auto">
    <td align="left">
    <td>服务类预算总价</td>
    <td><input id="service_pre_price" class="easyui-textbox"/></td>
    <td>服务类实际总价</td>
    <td><input id="service_total_price" class="easyui-textbox"/></td>
    </td>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
       onclick="viewPurchaseProductItem('service')">查看</a>
</div>
<div id="tbEngineering" style="height:auto">
    <td align="left">
    <td>工程类预算总价</td>
    <td><input id="engineering_pre_price" class="easyui-textbox"/></td>
    <td>工程类实际总价</td>
    <td><input id="engineering_total_price" class="easyui-textbox"/></td>
    </td>
    <a id="viewEngineering" href="javascript:void(0)" class="easyui-linkbutton"
       data-options="iconCls:'icon-edit',plain:true"
       onclick="viewPurchaseProductItem('engineering')">查看</a>
</div>
<div id="dlgPurchaseProductView" class="easyui-dialog" style="width:400px"
     closed="true" buttons="#dlgPurchaseProductButtonsView">
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
            <td>预算总价</td>
            <input id="prj_pre_price" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>规格型号</td>
            <input id="prj_spec" class="easyui-textbox" required="true" multiline="true"
                   style="width:100%;height:40px">
        </div>
        <div style="margin-bottom:10px">
            <td>技术参数及售后</td>
            <input id="prj_param" class="easyui-textbox" required="true" multiline="true"
                   style="width:100%;height:120px">
        </div>
    </div>
</div>
<div id="dlgPurchaseProductButtonsView">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlgPurchaseProductView').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>