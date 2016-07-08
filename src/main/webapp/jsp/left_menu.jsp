<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/7/1
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <title>LeftMenu</title>
    <script type="text/javascript">
        $(function(){
            $("#menuTree").tree({
                onClick: function(node){
                    alert(node.id);
                    showcontent(node.text);
                }
            });
        });

        function showcontent(language){
            $('#content').html(language);
        }

        function loadData(){
            $.ajax({
                type: 'POST',
                url: '/leftMenu',
                data:{},
                dataType: 'json',
                success: function(data){
                    $("#toApprove").text(data.toApprove);
                    $("#approved").text(data.approved);
                    $("#rejected").text(data.rejected);
                }
            });
        }
    </script>
</head>
<body  onload=" loadData();">
    <div>
        <ul  id="menuTree" class="easyui-tree">
            <li data-options="iconCls:'icon-cut'">
                <span>项目审批中心</span>
                <ul>
                    <li>
                        <span>项目审批</span>
                        <ul>
                            <li><span>待审批项目（<span id="toApprove" style="color: red"></span>）</span></li>
                            <li><span>已审批项目（<span id="approved" style="color: blue" ></span>）</span></li>
                            <li><span>审批未通过项目（<span id="rejected" style="color:darkgreen"></span>）</span></li>
                        </ul>
                    </li>
                    <li><span>采购执行</span></li>
                </ul>
            </li>
        </ul>
        <ul class="easyui-tree">
            <li>数据查询中心</li>
        </ul>
        <ul class="easyui-tree">
            <li>基础信息管理</li>
        </ul>
        <ul class="easyui-tree">
            <li>系统管理</li>
        </ul>
    </div>
</body>
</html>
