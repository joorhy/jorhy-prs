package controller;

import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LeftMenuController extends Controller {
    // 页面初始化
    public void index() {
        //查询待审批，已经审批，审批未通过数量
        int newPrj = 1;
        int toApprove = 2;
        int approved = 3;
        int rejected = 4;
        setAttr("new",newPrj);
        setAttr("toApprove",toApprove);
        setAttr("approved",approved);
        setAttr("rejected",rejected);
        renderJson();
    }
}
