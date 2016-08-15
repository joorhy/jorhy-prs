<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
    <head>
        <script type="text/javascript" src="../../js/applicant/commodity.js"></script>
        <script type="text/javascript" src="../../js/applicant/service.js"></script>
        <script type="text/javascript" src="../../js/applicant/engineering.js"></script>
        <script type="text/javascript" src="../../js/applicant/applicant.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <jsp:include page="../easyui_header.jsp"/>
        <title>采购过程管理平台</title>
        <style>
            html,body{text-align:center;margin:0px auto;width:1200px;}
        </style>
        <script type="text/javascript">
            function showNewPage() {
                $('#contentDiv').panel('setTitle','新建采购过程');
                $('#contentDiv').panel('refresh','../jsp/applicant/create.jsp');
            }

            //获取菜单中的数量
            function loadData(){
                /*$.ajax({
                    type: 'POST',
                    url: '/leftMenu',
                    data:{},
                    dataType: 'json',
                    success: function(data){
                        var newPrj = $('#menuTree').tree('find', "new");
                        if(newPrj){
                            $('#menuTree').tree('update', {
                                target: newPrj.target,
                                text:"新建采购过程（"+ data.toApprove +"）"
                            });
                        }

                        var toApprove = $('#menuTree').tree('find', "toApprove");
                        if(toApprove){
                            $('#menuTree').tree('update', {
                                target: toApprove.target,
                                text:"已提交采购过程（"+ data.toApprove +"）"
                            });
                        }

                        var approved = $('#menuTree').tree('find', "approved");
                        if(approved){
                            $('#menuTree').tree('update', {
                                target: approved.target,
                                text:"已执行采购过程（"+ data.approved +"）"
                            });
                        }

                        var rejected = $('#menuTree').tree('find', "rejected");
                        if(rejected){
                            $('#menuTree').tree('update', {
                                target: rejected.target,
                                text:"已完成采购过程（"+ data.rejected +"）"
                            });
                        }
                    }
                });*/
            }
        </script>

    </head>
    <body onload=" loadData();">
        <div id="header" style="min-height:100px;width:100%;border:1px green solid;"><jsp:include page="../header.jsp"/></div>
        <div class="easyui-layout" style="width:1200px;height:100%;">
            <div region="west" split="true" title="导航" style="width:250px;">
                <ul id="menuTree" class="easyui-tree" data-options="url:'/applicant/applicantTree',method:'get',
                    animate:true,onContextMenu: function(e,node){
                        e.preventDefault();
                        $(this).tree('select',node.target);
                        $('#mm').menu('show',{
                            left: e.pageX,
                            top: e.pageY
                        });}"></ul>
                <div id="mm" class="easyui-menu" style="width:120px;">
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
