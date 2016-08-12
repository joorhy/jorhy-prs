package controller;

import com.jfinal.core.Controller;
import bean.PrjData;
import com.jfinal.upload.UploadFile;
import model.ProjectInfo;
import org.activiti.engine.impl.util.json.JSONObject;

import java.io.File;
import java.util.*;

/**
 * Created by JooLiu on 2016/7/28.
 */
public class ApplicantController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/applicant/applicant.jsp");
    }

    public void uploader() {
        renderJsp("/jsp/applicant/attach.jsp");
    }

    // 提交按钮
    public void save() {
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
        UploadFile uploadFile = getFile();
        String fileName = uploadFile.getOriginalFileName();
        File file = uploadFile.getFile();
        file.delete();
    }

    public void commodityList() {
        /*List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> dataInfo;
        for (int i = 0; i < 8; i++) {
            dataInfo = new HashMap<String, String>();
            dataInfo.put("prj_name", "XX采购申请");
            dataInfo.put("prj_count", "FI-SW-" + String.valueOf(i + 1));
            dataInfo.put("prj_price", "张三");
            dataInfo.put("prj_total_price", "同意");
            dataInfo.put("prj_param", "2016-07-07 15:28:35");
            dataInfo.put("prj_spec", "同意采购");
            dataInfo.put("prj_attach", "同意采购");
            dataList.add(dataInfo);
        }*/

        setAttr("rows", new ArrayList<Map<String, String>>());
        setAttr("total", 0);
        renderJson();
    }

    public void serviceList() {
        setAttr("rows", new ArrayList<Map<String, String>>());
        setAttr("total", 0);
        renderJson();
    }

    public void engineeringList() {
        setAttr("rows", new ArrayList<Map<String, String>>());
        setAttr("total", 0);
        renderJson();
    }
}
