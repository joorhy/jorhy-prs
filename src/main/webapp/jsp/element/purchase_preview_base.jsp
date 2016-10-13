<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/10/11
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="easyui-panel" title="基础信息" style="width:100%"
     data-options="onLoad:onLoadPreviewPurchase()">
    <table id="privew_base" class="easyui-propertygrid" style="width:100%" data-options="
                url:'/purchase/preview_base_info',
                queryParams:{purchase_id:document.getElementById('purchase_id').value},
                method:'post',showGroup:false,showHeader:false,scrollbarSize:0">
    </table>
</div>
</body>
</html>
