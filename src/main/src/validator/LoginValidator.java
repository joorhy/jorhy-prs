package validator;

import com.jfinal.validate.Validator;
import com.jfinal.core.Controller;
import model.UserInfo;

/**
 * Created by Joo on 2016/6/28.
 */
public class LoginValidator extends Validator {
    protected void validate(Controller c) {
        validateRequiredString("uName", "nameMsg", "请输入用户名");
        validateRequiredString("uPass", "passMsg", "请输入密码");
    }

    protected void handleError(Controller c) {
        c.keepPara("uName");
        c.render("login.jsp");
    }
}
