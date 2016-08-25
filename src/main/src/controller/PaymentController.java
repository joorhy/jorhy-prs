package controller;

import bean.PaymentLeftMenu;
import com.jfinal.core.Controller;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PaymentController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/payment/payment.jsp");
    }

    public void paymentTree() {
        renderText(PaymentLeftMenu.getTree().toString());
    }
}
