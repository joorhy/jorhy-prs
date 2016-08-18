<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
    <head>
        <script type="text/javascript" src="../../js/applicant/items.js"></script>
        <script type="text/javascript" src="../../js/applicant/applicant.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <jsp:include page="../easyui_header.jsp"/>
        <title>采购过程管理平台</title>
        <style>
            html,body{text-align:center;margin:0px auto;width:1200px;}
        </style>
    </head>
    <body onload=" loadData();">
        <div id="header" style="min-height:100px;width:100%;border:1px green solid;"><jsp:include page="../header.jsp"/></div>
        <div class="easyui-layout" style="width:1200px;height:100%;">
            <div region="west" split="true" title="导航" style="width:250px;">
                <ul id="menuTree" class="easyui-tree" data-options="url:'/applicant/applicantTree',method:'get',
                    animate:true,onContextMenu: function(e,node){
                        e.preventDefault();
                        if (node.id == 'new_proj') {
                            $(this).tree('select',node.target);
                            $('#mm').menu('show',{
                                left: e.clientX,
                                top: e.clientY
                            });}
                        },onClick: function(node) { showContent(node); }">
                </ul>
                <div id="mm" align="left" class="easyui-menu" style="width:120px;">
                    <div onclick="showNewPage()" data-options="iconCls:'icon-add'">新建</div>
                </div>
            </div>
            <div id="contentDiv" class="easyui-panel" region="center" title="详细信息" style="padding:5px;width:100%;">
                <jsp:include page="welcome.jsp" />
            </div>
        </div>
        <div id="footer" style="width:100%;border:1px goldenrod solid;"><jsp:include page="../footer.jsp"/></div>
    </body>
</html>
