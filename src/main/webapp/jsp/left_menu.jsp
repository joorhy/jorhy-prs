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
//        $(document).ready(function(){
//        function loadData() {
//            alert(1);
//            $.ajax({
//                type: 'post',
//                url: '/leftMenu',
//                function(data){
//                    alert(data);
//                    var obj = eval('(' + data + ')');
//                    $("#toApprove").text = obj.toApprove;
//                    $("#approved").text = obj.approved;
//                    $("#rejected").text = obj.rejected;
//                }
//            });
//        }
//        });
    </script>
</head>
<body >
    <div id="menuTree">
        <ul class="easyui-tree">
            <li data-options="iconCls:'icon-cut'">
                <span>项目审批中心</span>
                <ul>
                    <li>
                        <span>项目审批</span>
                        <ul>
                            <li><span><a href="" target="right">待审批项目（<span id="toApprove">2${toApprove}</span>)</a></span></li>
                            <li><span><a href="" target="right">已审批项目（<span id="approved">3${approved}</span>）</a></span></li>
                            <li><span><a href="" target="right">审批未通过项目（<span id="rejected">4${rejected}</span>）</a></span></li>
                        </ul>
                    </li>
                    <li><span><a href="" target="right">采购执行</a></span></li>
                </ul>
            </li>
        </ul>
        <ul class="easyui-tree">
            <li><a href="" target="right">数据查询中心</a></li>
        </ul>
        <ul class="easyui-tree">
            <li><a href="" target="right">基础信息管理</a></li>
        </ul>
        <ul class="easyui-tree">
            <li><a href="" target="right">系统管理</a></li>
        </ul>
    </div>
</body>
</html>
