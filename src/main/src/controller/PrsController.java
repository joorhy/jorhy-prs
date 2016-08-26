package controller;

import bean.*;
import com.jfinal.core.Controller;
import model.UserModel;

/**
 * Created by JooLiu on 2016/8/26.
 */
public class PrsController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/prs.jsp");
    }

    public void getLeftMenu() {
        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        if (strUsername != null) {
            String strUserRole = UserModel.dao.getUserRole(strUsername);
            if (strUserRole != null) {
                if (strUserRole.equals(RoleBean.APPLICANT)) {
                    renderText(ApplicantLeftMenu.getTree().toString());
                } else if (strUserRole.equals(RoleBean.PURCHASE)) {
                    renderText(PurchaseLeftMenu.getTree().toString());
                } else if(strUserRole.equals(RoleBean.PAYMENT)) {
                    renderText(PaymentLeftMenu.getTree().toString());
                } else {
                    renderText(ApprovalLeftMenu.getTree(strUserRole).toString());
                }
            }
        }
    }
}
