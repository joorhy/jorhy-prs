<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>已分包</title>
</head>
<body>
<div>
    <jsp:include page="../element/package_base.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/packaged_products.jsp"/>
</div>
<div>
    <jsp:include page="../element/view_package_files.jsp"/>
</div><br/>
<div align="right">
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="complaintsPurchase()">投诉处理</a></td>
</div>
<div id="dlgComplaintPurchase" class="easyui-dialog" style="width:400px"
     closed="true" buttons="#dlg_complaints_buttons">
    <div style="margin:0;padding:5px 5px">
        <div style="margin-bottom:10px">
            <td>项目执行状态</td>
            <input id="prj_status" class="easyui-switchbutton" tyle="width:200px"
                   data-options="onText:'正常',offText:'暂停'">
        </div>
        <div>
            <td>投诉处理意见</td>
            <input id="complaints_opinion" class="easyui-textbox" required="true" multiline="true"
                   style="width:100%;height:120px" >
        </div>
    </div>
</div>
<div id="dlg_complaints_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="submitComplaintsOpinion()" style="width:90px">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlgComplaintPurchase').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>
