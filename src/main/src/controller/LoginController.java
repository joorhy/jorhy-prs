package controller;

import com.jfinal.core.Controller;
import com.jfinal.render.CaptchaRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LoginController  extends Controller{
    private Logger log = LoggerFactory.getLogger(LoginController.class);
    // 页面初始化
    public void index() {
        log.debug("LoginController index");
        log.info("LoginController index");
        renderJsp("/jsp/login.jsp");
    }

    // 登录按钮
    public void login(){
        String randomCode = getPara("randomCode");
        boolean loginSuccess = CaptchaRender.validate(this, randomCode);
        if (loginSuccess) {
            forwardAction("/applicant");
        } else {
            forwardAction("/applicant");
        }
    }

    // 验证码
    public void img() {
        CaptchaRender img = new CaptchaRender();
        render(img);
    }
}
