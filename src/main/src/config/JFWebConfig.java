package config;

import bean.PurchaseAttachFileBean;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import controller.*;
import model.*;

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
        me.add("/prs", PrsController.class);
        me.add("/purchase", PurchaseController.class);
        me.add("/package", PackageController.class);
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
        arp.addMapping("user", UserModel.class);
        arp.addMapping("funds_nature", FundsNatureModel.class);
        arp.addMapping("purchase_attach_file", PurchaseFileAttachModel.class);
        arp.addMapping("product", ProductModel.class);
        arp.addMapping("purchase", PurchaseModel.class);
        arp.addMapping("approve_record", ApproveRecordModel.class);
        arp.addMapping("package", PackageModel.class);
        arp.addMapping("package_product", PackageProductModel.class);
        arp.addMapping("package_attach_file", PackageFileAttachModel.class);
        arp.addMapping("directors", DirectorsModel.class);
        arp.addMapping("finance_dept", FinanceDeptModel.class);
    }
    public void configInterceptor(Interceptors me) {

    }
    public void configHandler(Handlers me) {

    }
}