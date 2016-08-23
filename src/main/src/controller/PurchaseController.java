package controller;

import bean.PurchasingBean;
import com.jfinal.core.Controller;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PurchaseController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/purchase/purchase.jsp");
    }

    public void purchaseTree() {
        renderText(PurchasingBean.getPurchaseTree().toString());
    }
}
