<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/26
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table id="tbToDivide" class="easyui-datagrid" title="未分包项目" style="width:100%;height:500px"
       data-options="onCheck:onClickToDivide,
                onUncheck:onEndEditToDivide,
                fitColumns:true,
                url:'/purchase/toDivideItems',
                method:'post',
                queryParams:{purchase_id:document.getElementById('purchase_id').value},
                view:groupview,
                groupField:'product_type',
                groupFormatter:function(value,rows){return value;}">
    <thead>
    <tr>
        <th data-options="field:'project_id',hidden:true"></th>
        <th data-options="field:'product_type',hidden:true"></th>
        <th data-options="field:'prj_name',width:80">项目名称</th>
        <th data-options="field:'prj_package_count',width:80,editor:'numberbox'">分包数量</th>
        <th data-options="field:'prj_count',width:80">总数量</th>
        <th data-options="field:'prj_price',width:80">单价</th>
        <th data-options="field:'prj_spec',width:80">规格型号</th>
        <th data-options="field:'prj_pre_price',width:80">预算总价</th>
        <th data-options="field:'prj_param',width:160">技术参数及售后</th>
        <th data-options="field:'prj_select',checkbox:true"></th>
    </tr>
    </thead>
</table>
</body>
</html>
