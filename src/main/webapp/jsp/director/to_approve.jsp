<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/8/22
  Time: 17:59
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
    <jsp:include page="../common/opinion.jsp"/>
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
    <td>财政局资金分管股室</td>
    <td>
        <select id="approve_person" class="easyui-combobox" style="width:120px">
            <option value="default">请选择分管股室</option>
            <option value="nyg">农业股</option>
            <option value="jkwg">教科文股</option>
            <option value="tzg">投资股</option>
            <option value="xcg">行财股</option>
            <option value="sbg">社保股</option>
        </select>
    </td>
    </td>
    <td>
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:120"
           onclick="agreePurchasing()">同意并提交政府采购管理股审核</a></td>
    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:80"
           onclick="disagreePurchasing()">退回下一级</a></td>
    </td>
</div>
</body>
</html>
