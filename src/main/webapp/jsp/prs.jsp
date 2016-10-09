<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <jsp:include page="includes.jsp"/>
        <title>采购过程管理平台</title>
        <style>
            html,body{text-align:center;margin:0px auto;width:1200px;}
        </style>
    </head>
    <body>
        <div id="header" style="min-height:100px;width:100%;border:1px green solid;">
            <jsp:include page="header.jsp"/>
        </div>
        <input id="purchase_id" type="hidden" value=""/>
        <input id="package_id" type="hidden" value=""/>
        <div id="main_layout" class="easyui-layout" style="width:1200px;height:100%;">
            <div region="west" split="true" title="导航" style="width:250px;">
                <ul id="menuTree" class="easyui-tree" data-options="url:'/prs/getLeftMenu',method:'get',
                    animate:true,onContextMenu:onLeftMenuRightClick,onClick:onLeftMenuLeftClick">
                </ul>
                <div id="menuPurchase" align="left" class="easyui-menu" style="width:120px;">
                    <div onclick="showNewPurchasePage()" data-options="iconCls:'icon-add'">新建</div>
                </div>
                <div id="menuPackage" align="left" class="easyui-menu" style="width:120px;">
                    <div onclick="showNewPackagePage()" data-options="iconCls:'icon-add'">新建包</div>
                </div>
                <div id="menuPayment" align="left" class="easyui-menu" style="width:120px;">
                    <div onclick="showNewPaymentPage()" data-options="iconCls:'icon-add'">申请支付</div>
                </div>
            </div>
            <div id="contentDiv" class="easyui-panel" region="center" title="简介"
                 style="padding:5px;width:100%;">
                <jsp:include page="pages/welcome.jsp" />
            </div>
        </div>
        <div id="footer" style="width:100%;border:1px goldenrod solid;"><jsp:include page="footer.jsp"/></div>
    </body>
</html>
