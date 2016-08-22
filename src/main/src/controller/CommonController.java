package controller;

import bean.AttachFileBean;
import bean.PurchasingBean;
import com.jfinal.core.Controller;
import model.PurchasingModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Joo on 2016/8/20.
 */
public class CommonController extends Controller {
    public void getBaseData() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("result", "success");
            setAttr("base", purchasingBean.getJSONBaseData());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    public void commodityList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("rows", purchasingBean.getJSONProductData(PurchasingBean.COMMODITY));
            setAttr("total", purchasingBean.getJSONProductData(PurchasingBean.COMMODITY).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void serviceList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("rows", purchasingBean.getJSONProductData(PurchasingBean.SERVICE));
            setAttr("total", purchasingBean.getJSONProductData(PurchasingBean.SERVICE).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void engineeringList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("rows", purchasingBean.getJSONProductData(PurchasingBean.ENGINEERING));
            setAttr("total", purchasingBean.getJSONProductData(PurchasingBean.ENGINEERING).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void opinionList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("rows", purchasingBean.getJSONOpinionData());
            setAttr("total", purchasingBean.getJSONOpinionData().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void downloadFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            String strFileID = getPara("file_id");
            AttachFileBean item = purchasingBean.getAttachFileItem(strFileID);
            if (item != null) {
                renderFile(new File(item.strFilePath));
            }
        } else {
            renderJson();
        }
    }

    public void getAttachFiles() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            ArrayList<Map<String, String>> lst = purchasingBean.getJSONAttachFile();
            setAttr("files", lst);
        }
        renderJson();
    }

    public void removeFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            String strFileID = getPara("file_id");
            AttachFileBean item = purchasingBean.getAttachFileItem(strFileID);
            if (item != null) {
                File file = new File(item.strFilePath);
                if (file != null) {
                    file.delete();
                }
            }
            purchasingBean.delAttachFile(strFileID);
        }
        setAttr("result", "success");
        renderJson();
    }
}
