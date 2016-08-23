<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/7/28
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>待审批项目</title>
</head>
<body>
<div>
    <jsp:include page="../common/base.jsp"/>
</div>
<br/><br/>
<div>
    <jsp:include page="../common/project.jsp"/>
</div>
<br/><br/>
<div>
    <jsp:include page="../common/attach_file.jsp"/>
</div><br/>
<div>
    <td align="left">
        <td style="width:15%;">审批意见</td><br/>
        <td >
            <input id="opinion" class="easyui-textbox" multiline="true"
                   style="width:100%;height:200px"/>
        </td>
    </td>
</div><br/>
<div align="right">
    <td>
    <td>上级审核人</td>
    <td>
        <select id="approve_person" class="easyui-combobox" style="width:120px">
            <option value="default">请选择审批人</option>
            <option value="nyjjz">农业局局长</option>
            <option value="czjjz">财政局局长</option>
            <option value="xcbz">宣传部长</option>
            <option value="zzbz">组织部长</option>
        </select>
    </td>
    </td>
    <td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:120"
               onclick="agreePurchasing()">同意并提交审核</a></td>
        <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
               onclick="disagreePurchasing()">不同意</a></td>
    </td>
</div>
</body><br/>
</html>
