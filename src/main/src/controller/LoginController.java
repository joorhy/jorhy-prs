package controller;

import com.jfinal.core.Controller;
import com.jfinal.render.CaptchaRender;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LoginController  extends Controller{
    // 页面初始化
    public void index() {
        renderJsp("/jsp/login.jsp");
    }

    // 登录按钮
    public void login(){
        String randomCode = getPara("randomCode");
        boolean loginSuccess = CaptchaRender.validate(this, randomCode);
        if (loginSuccess) {
            forwardAction("/welcome");
        } else {
            forwardAction("/welcome");
        }
    }

    // 验证码
    public void img() {
        CaptchaRender img = new CaptchaRender();
        render(img);
    }
}
