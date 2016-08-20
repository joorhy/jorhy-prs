<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/20
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <table id="dgOpinionLst" class="easyui-datagrid" title="已经审批单位意见" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/new/serviceList',method:'get',
                       toolbar:tbOpinion">
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
    <div id="tbOpinion" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true"
           onclick="viewOpinion()">查看</a>
    </div>
</body>
</html>
