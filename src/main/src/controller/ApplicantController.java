package controller;

import bean.AttachFileBean;
import bean.ProductTypeBean;
import com.jfinal.core.Controller;
import bean.PurchaseBean;
import com.jfinal.upload.UploadFile;
import model.PurchasingModel;
import org.activiti.engine.impl.util.json.JSONObject;

import java.io.File;

/**
 * Created by JooLiu on 2016/7/28.
 */
public class ApplicantController extends Controller {
    // 保存按钮
    public void save() {
        String strBaseData = getPara("base");
        String strCommodity = getPara("commodity");
        String strService = getPara("service");
        String strEngineering = getPara("engineering");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(objBaseData.getString("purchasing_id"));
        if (purchaseBean == null) {
            purchaseBean = new PurchaseBean();
        }
        purchaseBean.parseBaseData(objBaseData);

        JSONObject objCommodity = new JSONObject(strCommodity);
        purchaseBean.parseProductData(objCommodity, ProductTypeBean.COMMODITY);

        JSONObject objService = new JSONObject(strService);
        purchaseBean.parseProductData(objService, ProductTypeBean.SERVICE);

        JSONObject objEngineering = new JSONObject(strEngineering);
        purchaseBean.parseProductData(objEngineering, ProductTypeBean.ENGINEERING);

        PurchasingModel.dao.savePurchasing(purchaseBean);

        setAttr("result", "success");
        renderJson();
    }

    // 提交审核按钮
    public void submit() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingModel.dao.submitPurchasing(strPurchasingID);
        setAttr("result", "success");
        renderJson();
    }

    // 撤销按钮
    public void cancel() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingModel.dao.cancelPurchasing(strPurchasingID);
        setAttr("result", "success");
        renderJson();
    }

    public void uploadFile() {
        AttachFileBean item = new AttachFileBean();
        item.strPurchasingID = getPara("purchasing_id");
        item.strFileID = getPara("file_id");

        UploadFile uploadFile = getFile();
        item.strFileName = uploadFile.getOriginalFileName();

        File file = uploadFile.getFile();
        item.strFileSize = String.valueOf(file.length());
        item.strFilePath = file.getPath();

        PurchasingModel.dao.addAttachFile(item.strPurchasingID, item);
        setAttr("result", "success");
        renderJson();
    }
}
