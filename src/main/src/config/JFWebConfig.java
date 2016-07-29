package config;

import com.jfinal.config.*;
import com.jfinal.log.Log4jLogFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import controller.*;
import model.UserInfo;

/**
 * Created by JooLiu on 2016/6/22.
 */
public class JFWebConfig extends JFinalConfig {
    public void configConstant(Constants me) {
        loadPropertyFile("properties/prsConfig.Properties");
        me.setViewType(ViewType.JSP);
        me.setDevMode(getPropertyToBoolean("devMode"));
        me.setEncoding("UTF-8");
    }
    public void configRoute(Routes me) {
        /**
         * 前台路由转发设置
         */
        me.add("/", LoginController.class);
        me.add("/welcome", WelcomeController.class);
        me.add("/leftMenu", LeftMenuController.class);
        me.add("/toApprove", ToApproveController.class);
        me.add("/new", NewController.class);
    }
    public void configPlugin(Plugins me) {
        me.add(new EhCachePlugin());
        /**
         * 配置数据源和数据库连接池插件
         */
        //C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbcUrl"),
         //       getProperty("user"), getProperty("password"),
         //       getProperty("driverClass"));
//        C3p0Plugin  c3p0  =  new  C3p0Plugin("jdbc:mysql://127.0.0.1:3306/chen",
//                "root", "000000");
        //me.add(c3p0);

        /**
         * 配置数据库表映射插件
         */
        //ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
        //me.add(arp);

        /**
         * 配置映射表到模型
         */
        //arp.addMapping("user_info", UserInfo.class);
    }
    public void configInterceptor(Interceptors me) {

    }
    public void configHandler(Handlers me) {

    }
}