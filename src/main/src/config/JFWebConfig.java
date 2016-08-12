package config;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import controller.*;
import model.ProjectInfo;

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
        me.add("/applicant", ApplicantController.class);
        me.add("/leftMenu", LeftMenuController.class);
        me.add("/toApprove", ToApproveController.class);
    }
    public void configPlugin(Plugins me) {
        me.add(new EhCachePlugin());
        /**
         * 配置数据源和数据库连接池插件
         */
        C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbcUrl"),
                getProperty("user"), getProperty("password"),
                getProperty("driverClass"));
        me.add(c3p0);

        /**
         * 配置数据库表映射插件
         */
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
        me.add(arp);

        /**
         * 配置映射表到模型
         */
        arp.addMapping("cg_xm_jbxx", ProjectInfo.class);
    }
    public void configInterceptor(Interceptors me) {

    }
    public void configHandler(Handlers me) {

    }
}