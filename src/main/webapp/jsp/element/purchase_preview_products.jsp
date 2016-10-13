<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <table id="dgPreviewCommodity" class="easyui-datagrid" title="商品类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/purchase/commodity_list',method:'post',
                       queryParams:{purchase_id:document.getElementById('purchase_id').value}">
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
    <table id="dgPreviewService" class="easyui-datagrid" title="服务类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/purchase/service_list',method:'post',
                       queryParams:{purchase_id:document.getElementById('purchase_id').value}">
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
<table id="dgPreviewEngineering" class="easyui-datagrid" title="工程类" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/purchase/engineering_list',method:'post',
                   queryParams:{purchase_id:document.getElementById('purchase_id').value}">
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
</body>
</html>
