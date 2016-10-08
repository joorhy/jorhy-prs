package model;

import bean.RoleBean;
import bean.UserBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JooLiu on 2016/6/22.
 */
public class UserModel extends Model<UserModel> {
    public static final UserModel dao = new UserModel();

    public String getUserRole(String userName){
        String url = "select m.perm from role r left join permission m on r.permission_id=m.id where " +
                "r.user_id=" + userName;
        List<UserModel> m = dao.find(url);
        return m.get(0).getStr("perm");
    }

    public String login(String userName, String password) {
        return null;
    }

    public UserBean getUser(String strUsername) {
        String url = "select u.*, d.dept_name, r.post from user u " +
                "left join dept d on u.dept_id=d.id left join role r on r.user_id=u.id where u.id=" +
                strUsername;

        List<UserModel> m = dao.find(url);
        UserBean userBean = new UserBean();
        userBean.userID = m.get(0).getInt("id");
        userBean.strUsername = m.get(0).getStr("username");
        userBean.strPassword = m.get(0).getStr("passwd");
        userBean.strRealName = m.get(0).getStr("real_name");
        userBean.strDepartment = m.get(0).getStr("dept_name");
        userBean.strPost = m.get(0).getStr("post");

        return userBean;
    }
}
