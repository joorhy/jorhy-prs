package controller;

import bean.AttachFileItem;
import bean.PurchasingData;
import com.jfinal.core.Controller;
import model.PurchasingInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Joo on 2016/8/20.
 */
public class CommonController extends Controller {
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
}
