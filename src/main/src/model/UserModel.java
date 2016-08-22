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
    }

    public UserModel getUserInfo(String userName, String password){
        //@Before(CacheInterceptor.class)
        //@CacheName("userList")

        //@Before(EvictInterceptor.class)
        //@CacheName("userList")
        //String sql = "select * from user_info where user_name = ? and password = ?";
        //UserModel userModel = UserModel.dao.findFirst(sql, userName, password);
        return null;//userInfo;
    }

    public String login(String userName, String password) {
        if (userName.equals("1")) {
            return RoleBean.APPLICANT;
        } else if(userName.equals("2")) {
            return RoleBean.ACCOUNTING;
        } else if (userName.equals("3")) {
            return RoleBean.DIRECTOR;
        }
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
