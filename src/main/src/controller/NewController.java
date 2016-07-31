package controller;

import com.jfinal.core.Controller;
import data.PrjData;
import model.ProjectInfo;
import org.activiti.engine.impl.util.json.JSONObject;

/**
 * Created by JooLiu on 2016/7/28.
 */
public class NewController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/new.jsp");
    }

    public void uploader() {
        renderJsp("/jsp/uploader.jsp");
    }

    // 提交按钮
    public void save(){
        String strBaseData = getPara("base");
        String strCommodity = getPara("commodity");
        String strService = getPara("service");
        String strEngineering = getPara("engineering");

        PrjData prjData = new PrjData();

        JSONObject objBaseData = new JSONObject(strBaseData);
        prjData.ReadBaseData(objBaseData);

        JSONObject objCommodity = new JSONObject(strCommodity);
        prjData.ReadProductData(objCommodity, PrjData.COMMODITY);

        JSONObject objService = new JSONObject(strService);
        prjData.ReadProductData(objService, PrjData.SERVIDE);

        JSONObject objEngineering = new JSONObject(strEngineering);
        prjData.ReadProductData(objEngineering, PrjData.ENGINEERING);

        ProjectInfo.dao.AddProject(prjData);
    }

    public void uploadFile() {
        System.out.print("");
    }
}
