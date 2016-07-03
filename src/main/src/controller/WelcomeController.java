package controller;

import com.jfinal.core.Controller;

/**
 * Created by Joo on 2016/7/1.
 */
public class WelcomeController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/welcome.jsp");
    }
}
