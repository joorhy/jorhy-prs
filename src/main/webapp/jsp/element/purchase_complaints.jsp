<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/23
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<table id="dgComplaintsLst" class="easyui-datagrid" title="投诉处理意见" style="width:100%;height:225px"
       data-options="rownumbers:true,singleSelect:true,url:'/purchase/complaints_list',method:'post',
                     queryParams:{purchase_id:document.getElementById('purchase_id').value},
                     toolbar:tbComplaints">
    <thead>
        <tr>
            <th data-options="field:'comp_deal_date',width:80">处理日期</th>
            <th data-options="field:'comp_content',width:320">处理意见</th>
        </tr>
    </thead>
</table>
<div id="tbComplaints" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true"
       onclick="viewComplaints()">查看</a>
</div>
</body>
</html>
