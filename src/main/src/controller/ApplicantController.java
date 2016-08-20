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

    public void applicantTree() {
        renderText(PurchasingData.getApplicantTree().toString());
    }
}
