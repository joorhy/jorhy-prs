package controller;

import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LoginController  extends Controller{
    // 页面初始化
    public void index() {
        renderJsp("/jsp/login.jsp");
    }

    //登录按钮
    public void login(){
        forwardAction("/welcome");
    }
}
