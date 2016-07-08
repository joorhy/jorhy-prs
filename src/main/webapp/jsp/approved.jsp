<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/7/1
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>已审批项目</title>
</head>
<body>
     <div region="center" border="false">
        <table class="easyui-datagrid" title="已审批项目" style="width:100%;height:250px"
              data-options="singleSelect:true,collapsible:true,url:'../js/to_approve_data.json',method:'get'">
           <thead>
           <tr>
               <th data-options="field:'itemName',width:180">项目名称</th>
               <th data-options="field:'itemNo',width:120">采购函号</th>
               <th data-options="field:'person',width:100">审批人</th>
               <th data-options="field:'advice',width:200">意见</th>
               <th data-options="field:'approveDate',width:100">日期</th>
               <th data-options="field:'remark',width:200,align:'center'">备注</th>
           </tr>
           </thead>
       </table>
    </div>
    <br/>
    <div class="easyui-panel" title="项目详细信息"  collapsible="true" style="padding-top:10px;height:200px;">
        <h3 style="color:#0099FF;">项目详细信息</h3>
        <p>项目详细信息</p>
    </div>
     <br/>
    <div class="easyui-panel" title="审批信息"  collapsible="true" selected="true" style="padding:10px;height:200px;">
     审批信息
    </div>

</body>
</html>
