<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/23
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="../../js/common/common.js"></script>
    <script type="text/javascript" src="../../js/purchase/purchase.js"></script>
    <jsp:include page="../easyui_header.jsp"/>
    <title>采购执行中心</title>
    <style>
        html,body{text-align:center;margin:0px auto;width:1200px;}
    </style>
</head>
<body>
<div id="header" style="min-height:100px;width:100%;border:1px green solid;"><jsp:include page="../header.jsp"/></div>
<input id="purchasing_id" type="hidden" value=""/></td>
<div class="easyui-layout" style="width:1200px;height:100%;">
    <div region="west" split="true" title="导航" style="width:250px;">
        <ul id="menuTree" class="easyui-tree" data-options="url:'/purchase/purchaseTree',method:'get',
                animate:true,onContextMenu: function(e,node){
                        e.preventDefault();
                        if (node.type == 'to_divide') {
						    document.getElementById('purchasing_id').value = node.id;
                            $(this).tree('select',node.target);
                            $('#mm').menu('show',{
                                left: e.clientX,
                                top: e.clientY
                            });}
                        },onClick: function(node) { showContent(node); }">
        </ul>
        <div id="mm" align="left" class="easyui-menu" style="width:120px;">
            <div onclick="showNewPacket()" data-options="iconCls:'icon-add'">新建包</div>
            <div onclick="savePackets()" data-options="iconCls:'icon-save'">提交分包</div>
        </div>
    </div>
    <div id="contentDiv" class="easyui-panel" region="center" title="简介" style="padding:5px;width:100%;">
        <jsp:include page="welcome.jsp" />
    </div>
</div>
<div id="footer" style="width:100%;border:1px goldenrod solid;"><jsp:include page="../footer.jsp"/></div>
</body>
</html>
