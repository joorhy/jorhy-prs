package controller;

import bean.OpinionBean;
import bean.PurchasingBean;
import bean.UserBean;
import com.jfinal.core.Controller;
import model.PurchasingModel;
import model.UserModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joo on 2016/8/13.
 */
public class AccountingController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/accounting/accounting.jsp");
    }

    public void accountingTree() {
        renderText(PurchasingBean.getAccountingTree().toString());
    }

    // 同意并提交按钮
    public void agree() {
        String strPurchasingID = getPara("purchasing_id");
        String strContent = getPara("content");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        UserBean userBean = UserModel.dao.getUser(strUsername);

        OpinionBean opinionBean = new OpinionBean();
        opinionBean.strApprovePerson = userBean.strRealName;
        opinionBean.strApproveDepartment = userBean.strDepartment;
        opinionBean.strOpinion = strContent;
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        opinionBean.strApproveDate = sdf.format(currentTime);
        PurchasingModel.dao.agreePurchasing(strPurchasingID, opinionBean);
        setAttr("result", "success");
        renderJson();
    }

    // 不同意按钮
    public void disagree() {
        String strPurchasingID = getPara("purchasing_id");
        String strContent = getPara("content");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        UserBean userBean = UserModel.dao.getUser(strUsername);

        OpinionBean opinionBean = new OpinionBean();
        opinionBean.strApprovePerson = userBean.strRealName;
        opinionBean.strApproveDepartment = userBean.strDepartment;
        opinionBean.strOpinion = strContent;
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        opinionBean.strApproveDate = sdf.format(currentTime);
        PurchasingModel.dao.disagreePurchasing(strPurchasingID, opinionBean);
        setAttr("result", "success");
        renderJson();
    }
}
