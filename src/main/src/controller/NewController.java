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

    // 提交按钮
    public void add(){
        String strPurcCode = getPara("txtPurcCode");
        String strFundsSrc = getPara("txtFundsSrc");
        String strContacts = getPara("txtContacts");
        String strPhoneNum = getPara("txtPhoneNum");
        String strFundsNature = getPara("txtFundsNature");
        String strCommodityTotalPrice = getPara("txtCommodityTotalPrice");
        String strServiceTotalPrice = getPara("txtServiceTotalPrice");
        String strEngineeringTotalPrice = getPara("txtEngineeringTotalPrice");

        String strListCommodity = getPara("lstCommodity");
    }
}
