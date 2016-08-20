package controller;

import bean.AttachFileItem;
import com.jfinal.core.Controller;
import bean.PurchasingData;
import com.jfinal.upload.UploadFile;
import model.PurchasingInfo;
import org.activiti.engine.impl.util.json.JSONArray;
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

    // 保存按钮
    public void save() {
        String strBaseData = getPara("base");
        String strCommodity = getPara("commodity");
        String strService = getPara("service");
        String strEngineering = getPara("engineering");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PurchasingData purchasingData = PurchasingInfo.dao.getPurchasing(objBaseData.getString("purchasing_id"));
        if (purchasingData == null) {
            purchasingData = new PurchasingData();
        }
        purchasingData.parseBaseData(objBaseData);

        JSONObject objCommodity = new JSONObject(strCommodity);
        purchasingData.parseProductData(objCommodity, PurchasingData.COMMODITY);

        JSONObject objService = new JSONObject(strService);
        purchasingData.parseProductData(objService, PurchasingData.SERVICE);

        JSONObject objEngineering = new JSONObject(strEngineering);
        purchasingData.parseProductData(objEngineering, PurchasingData.ENGINEERING);

        PurchasingInfo.dao.savePurchasing(purchasingData);

        setAttr("result", "success");
        renderJson();
    }

    // 提交审核按钮
    public void submit() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingInfo.dao.submitPurchasing(strPurchasingID);
        setAttr("result", "success");
        renderJson();
    }

    public void uploadFile() {
        AttachFileItem item = new AttachFileItem();
        item.strPurchasingID = getPara("purchasing_id");
        item.strFileID = getPara("file_id");

        UploadFile uploadFile = getFile();
        item.strFileName = uploadFile.getOriginalFileName();

        File file = uploadFile.getFile();
        item.strFileSize = String.valueOf(file.length());
        item.strFilePath = file.getPath();

        PurchasingInfo.dao.addAttachFile(item.strPurchasingID, item);
        setAttr("result", "success");
        renderJson();
    }

    public void downloadFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingData purchasingData = PurchasingInfo.dao.getPurchasing(strPurchasingID);
        if (purchasingData != null) {
            String strFileID = getPara("file_id");
            AttachFileItem item = purchasingData.getAttachFileItem(strFileID);
            if (item != null) {
                renderFile(new File(item.strFilePath));
            }
        } else {
            renderJson();
        }
    }

    public void getAttachFiles() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingData purchasingData = PurchasingInfo.dao.getPurchasing(strPurchasingID);
        if (purchasingData != null) {
            ArrayList<Map<String, String>> lst = purchasingData.getJSONAttachFile();
            setAttr("files", lst);
        }
        renderJson();
    }

    public void removeFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingData purchasingData = PurchasingInfo.dao.getPurchasing(strPurchasingID);
        if (purchasingData != null) {
            String strFileID = getPara("file_id");
            AttachFileItem item = purchasingData.getAttachFileItem(strFileID);
            if (item != null) {
                File file = new File(item.strFilePath);
                if (file != null) {
                    file.delete();
                }
            }
            purchasingData.delAttachFile(strFileID);
        }
        setAttr("result", "success");
        renderJson();
    }

    public void applicantTree() {
        renderText(PurchasingData.getApplicantTree().toString());
    }

    public void getBaseData() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPurchasing(strID);
        if (data != null) {
            setAttr("result", "success");
            setAttr("base", data.getJSONBaseData());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    public void commodityList() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPurchasing(strID);
        if (data != null) {
            setAttr("rows", data.getJSONProductData(PurchasingData.COMMODITY));
            setAttr("total", data.getJSONProductData(PurchasingData.COMMODITY).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void serviceList() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPurchasing(strID);
        if (data != null) {
            setAttr("rows", data.getJSONProductData(PurchasingData.SERVICE));
            setAttr("total", data.getJSONProductData(PurchasingData.SERVICE).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void engineeringList() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPurchasing(strID);
        if (data != null) {
            setAttr("rows", data.getJSONProductData(PurchasingData.ENGINEERING));
            setAttr("total", data.getJSONProductData(PurchasingData.ENGINEERING).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }
}
