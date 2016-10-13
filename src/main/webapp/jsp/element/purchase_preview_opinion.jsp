<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<table id="dgPreviewOpinionLst" class="easyui-datagrid" title="已经审批单位意见" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/purchase/opinion_list',method:'post',
                         queryParams:{purchase_id:document.getElementById('purchase_id').value}">
    <thead>
    <tr>
        <th data-options="field:'op_department',width:80">单位</th>
        <th data-options="field:'op_approve_person',width:80">审批人</th>
        <th data-options="field:'op_approve_date',width:80">审批日期</th>
        <th data-options="field:'op_content',width:320">意见</th>
    </tr>
    </thead>
</table>
</body>
</html>
