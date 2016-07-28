<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/6/22
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <meta http-equiv="Context-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="easyui_header.jsp"/>
    <title>登录</title>
    <style>
        body{
            text-align:center;
        }
        .div{
            margin-left:auto;
            margin-right:auto;
            margin-top: 10%;
            width:400px;
            height:100px;
        }
    </style>
</head>
<body>
<div class="div">
    <form  action="/login/login" method="post">
        <div class="fl"><img src="login.jpg"></div>
        <table>
            <tr align="left">
                <td>用户名：</td>
                <td><input name="uName" class="easyui-textbox" data-options="iconCls:'icon-man'"/>${nameMsg}</td>
            </tr>
            <tr align="left">
                <td>密码：</td>
                <td><input type="password"  name="uPass" class="easyui-textbox" data-options="iconCls:'icon-lock'"/>${passMsg}</td>
            </tr>
            <tr align="left">
                <td>验证码：</td>
                <td><input name="randomCode" class="easyui-textbox" />${codeMsg}</td>
                <td><img src="img" alt="点击刷新" style="padding-center: 7px;" id="randomCode" onclick="refreshCode();" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value=" 登 录 "  />
                    <input type="reset" value=" 重 置 " />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    ${errorMsg}
                </td>
            </tr>
        </table>
    </form>
</div>
</body>

<script type="text/javascript">
    function refreshCode(){
        var d = new Date();
        //为了避免服务器或者浏览器缓存，添加了一个额外的参数
        document.getElementById("randomCode").src="img?t=" + d.toString(40);
    }
</script>

</html>
