package controller;

import bean.PurchasingBean;
import com.jfinal.core.Controller;

/**
 * Created by JooLiu on 2016/8/22.
 */
public class DirectorController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/director/director.jsp");
    }

    public void directorTree() {
        renderText(PurchasingBean.getDirectorTree().toString());
    }
}
