package controller;

import bean.ApprovalLeftMenu;
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
public class ApprovalController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/approval/approval.jsp");
    }

    public void approvalTree() {
        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        if (strUsername != null) {
            String strUserRole = UserModel.dao.getUserRole(strUsername);
            if (strUserRole != null) {
                renderText(ApprovalLeftMenu.getTree(strUserRole).toString());
            }
        }
    }
}
