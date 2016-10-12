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

<div id="dlgPreviewPurchase" class="easyui-dialog" style="width:1200px"
     closed="true" buttons="#dlg_preview_buttons">
    <span id="print_area">
        <h1 id="preview_title" style="width:100%;text-align:center"></h1>
        <div>
            <jsp:include page="../element/purchase_preview_base.jsp"/>
        </div><br/>
        <div>
            <jsp:include page="../element/purchase_preview_opinion.jsp"/>
        </div><br/>
        <div>
            <jsp:include page="../element/purchase_preview_products.jsp"/>
        </div>
    </span>
</div>
<div id="dlg_preview_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="printPurchase()" style="width:90px">打印</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlgPreviewPurchase').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>
