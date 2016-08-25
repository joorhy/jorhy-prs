package model;

import bean.RoleBean;
import bean.UserBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/6/22.
 */
public class UserModel extends Model<UserModel> {
    public static final UserModel dao = new UserModel();
    private ArrayList<UserBean> lstUser = new ArrayList<UserBean>();
    public UserModel() {
        // 申请者
        UserBean applicant = new UserBean();
        applicant.strUsername = "1";
        applicant.strRealName = "张三";
        applicant.strDepartment = "消防队";
        lstUser.add(applicant);
        // 财务
        UserBean accounting = new UserBean();
        accounting.strUsername = "2";
        accounting.strRealName = "李四";
        accounting.strDepartment = "财务部";
        lstUser.add(accounting);
        // 局长
        UserBean director = new UserBean();
        director.strUsername = "3";
        director.strRealName = "王五";
        director.strDepartment = "农业局";
        lstUser.add(director);
        // 监管股
        UserBean regulatory = new UserBean();
        regulatory.strUsername = "4";
        regulatory.strRealName = "赵六";
        regulatory.strDepartment = "财政监管股";
        lstUser.add(regulatory);
        // 财政分管副局长
        UserBean bureau = new UserBean();
        bureau.strUsername = "5";
        bureau.strRealName = "马局长";
        bureau.strDepartment = "财政局";
        lstUser.add(bureau);
        // 采购实施单位
        UserBean purchase = new UserBean();
        purchase.strUsername = "6";
        purchase.strRealName = "小杨";
        purchase.strDepartment = "采购执行中心";
        lstUser.add(purchase);
        // 资金支付中心
        UserBean payment = new UserBean();
        payment.strUsername = "7";
        payment.strRealName = "刘部长";
        payment.strDepartment = "资金支付中心";
        lstUser.add(payment);
    }

    public String getUserRole(String userName){
        if (userName.equals("1")) {
            return RoleBean.APPLICANT;
        } else if(userName.equals("2")) {
            return RoleBean.ACCOUNTING;
        } else if (userName.equals("3")) {
            return RoleBean.DIRECTOR;
        } else if (userName.equals("4")) {
            return RoleBean.REGULATORY;
        } else if (userName.equals("5")) {
            return RoleBean.BUREAU;
        } else if (userName.equals("6")) {
            return RoleBean.PURCHASE;
        } else if (userName.equals("7")) {
            return RoleBean.PAYMENT;
        }
        return null;
    }

    public String login(String userName, String password) {
        return null;
    }

    public UserBean getUser(String strUsername) {
        for (int i=0; i<lstUser.size(); i++) {
            if (lstUser.get(i).strUsername.equals(strUsername)) {
                return lstUser.get(i);
            }
        }
        return null;
    }
}
