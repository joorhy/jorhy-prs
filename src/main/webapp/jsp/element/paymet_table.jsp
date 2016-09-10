<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/9/1
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table id="dgPayment" class="easyui-datagrid" title="申请支付表" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/common/commodityList',method:'post',
                   queryParams:{purchase_id:document.getElementById('purchase_id').value},
                   toolbar:tbPayment">
    <thead>
    <tr>
        <th data-options="field:'project_id',hidden:true"></th>
        <th data-options="field:'product_type',hidden:true"></th>
        <th data-options="field:'prj_name',width:80">支付序号</th>
        <th data-options="field:'prj_count',width:100">申请支付金额</th>
        <th data-options="field:'prj_price',width:100">申请支付...</th>
        <th data-options="field:'prj_spec',width:160">签署支付意见负责人</th>
        <th data-options="field:'prj_pre_price',width:160">审核确认支付金额</th>
        <th data-options="field:'prj_param',width:160">审核通过时间</th>
    </tr>
    </thead>
</table>
<div id="tbPayment" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       onclick="addPaymentItem()">添加</a>
</div>
</body>
</html>
