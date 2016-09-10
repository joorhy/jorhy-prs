<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/9/10
  Time: 23:10
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
        <th data-options="field:'prj_price',width:100">申请支付时间</th>
        <th data-options="field:'prj_spec',width:160">签署支付意见负责人</th>
    </tr>
    </thead>
</table>
<div id="tbPayment" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       onclick="addPaymentApplicantItem()">添加</a>
</div>
<div id="dlgPaymentApplicant" class="easyui-dialog" style="width:400px"
     closed="true" buttons="#dlgPaymentApplicantButtons">
    <div style="margin:0;padding:20px 50px">
        <div style="margin-bottom:10px">
            <td>支付序号</td>
            <input id="prj_name" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>申请支付金额</td>
            <input id="prj_count" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>申请支付时间</td>
            <input id="prj_price" class="easyui-textbox" required="true" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <td>签署支付意见负责人</td>
            <input id="prj_param" class="easyui-textbox" required="true" multiline="true"
                   style="width:100%;height:120px">
        </div>
    </div>
</div>
<div id="dlgPaymentApplicantButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="savePaymentApplicantItem()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlgPaymentApplicant').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>
