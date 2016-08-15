package controller;

import com.jfinal.core.Controller;

/**
 * Created by Joo on 2016/8/13.
 */
public class AccountingController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/accounting/accounting.jsp");
    }
}
