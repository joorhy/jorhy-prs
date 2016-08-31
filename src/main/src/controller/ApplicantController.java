package controller;

import bean.PackageAttachFileBean;
import bean.PurchaseAttachFileBean;
import bean.ProductTypeBean;
import com.jfinal.core.Controller;
import bean.PurchaseBean;
import com.jfinal.upload.UploadFile;
import model.PackageModel;
import model.PurchaseModel;
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
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(objBaseData.getString("purchase_id"));
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

        PurchaseModel.dao.savePurchase(purchaseBean);

        setAttr("result", "success");
        renderJson();
    }

    // 提交审核按钮
    public void submit() {
        String strPurchasingID = getPara("purchase_id");
        PurchaseModel.dao.submitPurchase(strPurchasingID);
        setAttr("result", "success");
        renderJson();
    }

    // 撤销按钮
    public void cancel() {
        String strPurchasingID = getPara("purchase_id");
        PurchaseModel.dao.cancelPurchase(strPurchasingID);
        setAttr("result", "success");
        renderJson();
    }

    public void uploadFile() {
        String strPurchaseID = getPara("purchase_id");
        String strPackageID = getPara("package_id");
        if (strPurchaseID != null) {
            PurchaseAttachFileBean item = new PurchaseAttachFileBean();
            item.strPurchaseID = strPurchaseID;
            item.strFileID = getPara("file_id");

            UploadFile uploadFile = getFile();
            item.strFileName = uploadFile.getOriginalFileName();

            File file = uploadFile.getFile();
            item.strFileSize = String.valueOf(file.length());
            item.strFilePath = file.getPath();

            PurchaseModel.dao.addAttachFile(strPurchaseID, item);
        }

        if (strPackageID != null) {
            PackageAttachFileBean item = new PackageAttachFileBean();
            item.strPackageID = strPackageID;
            item.strFileID = getPara("file_id");

            UploadFile uploadFile = getFile();
            item.strFileName = uploadFile.getOriginalFileName();

            File file = uploadFile.getFile();
            item.strFileSize = String.valueOf(file.length());
            item.strFilePath = file.getPath();

            PackageModel.dao.addAttachFile(strPackageID, item);
        }

        setAttr("result", "success");
        renderJson();
    }
}
