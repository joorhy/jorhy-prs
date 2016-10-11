<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/23
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>已审批项目</title>
</head>
<body>
<div>
    <jsp:include page="../element/purchase_base.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/view_purchase_products.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/view_purchase_files.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/purchase_opinion.jsp"/>
</div><br/>
<div>
    <jsp:include page="../element/purchase_complaints.jsp"/>
</div><br>
<div align="right">
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="printPreviewPurchase()">打印预览</a></td>
</div><br/>

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
