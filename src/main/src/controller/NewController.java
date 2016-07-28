package controller;

import com.jfinal.core.Controller;

/**
 * Created by JooLiu on 2016/7/28.
 */
public class NewController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/new.jsp");
    }
}
