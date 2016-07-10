package model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;

/**
 * Created by JooLiu on 2016/6/22.
 */
public class UserInfo extends Model<UserInfo> {
    public static final UserInfo dao = new UserInfo();

    public UserInfo getUserInfo(String userName, String password){
        //@Before(CacheInterceptor.class)
        //@CacheName("userList")

        //@Before(EvictInterceptor.class)
        //@CacheName("userList")
        //String sql = "select * from user_info where user_name = ? and password = ?";
        //UserInfo userInfo = UserInfo.dao.findFirst(sql, userName, password);
        return null;//userInfo;
    }
}
