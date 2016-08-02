package controller;

import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LeftMenuController extends Controller {
    // 页面初始化
    public void index() {
        //查询待审批，已经审批，审批未通过数量
        int toApprove = 1;
        int approved = 2;
        int rejected = 3;
        setAttr("toApprove",toApprove);
        setAttr("approved",approved);
        setAttr("rejected",rejected);
        renderJson();
    }
}
