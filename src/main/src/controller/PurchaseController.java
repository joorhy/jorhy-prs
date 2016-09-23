package controller;

import bean.*;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import model.FundsNatureModel;
import model.PurchaseModel;
import model.UserModel;
import org.activiti.engine.impl.util.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PurchaseController extends Controller {
    public void toDivideItems() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONToDivideItems());
            setAttr("total", purchaseBean.getJSONToDivideItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    // 保存按钮
    public void save() {
        String strBaseData = getPara("base");
        String strCommodity = getPara("commodity");
        String strService = getPara("service");
        String strEngineering = getPara("engineering");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PurchaseModel.dao.savePurchase(objBaseData);

        String strPurchaseID = objBaseData.getString("purchase_id");
        JSONObject objCommodity = new JSONObject(strCommodity);
        PurchaseModel.dao.addProducts(strPurchaseID, objCommodity, ProductTypeBean.COMMODITY);

        JSONObject objService = new JSONObject(strService);
        PurchaseModel.dao.addProducts(strPurchaseID, objService, ProductTypeBean.SERVICE);

        JSONObject objEngineering = new JSONObject(strEngineering);
        PurchaseModel.dao.addProducts(strPurchaseID, objEngineering, ProductTypeBean.ENGINEERING);

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

    // 提交分包
    public void submit_packages() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseModel.dao.submitPurchase(strPurchaseID);

        setAttr("result", "success");
        renderJson();
    }

    // 审批意见
    public void approve() {
        String strPurchaseID = getPara("purchase_id");
        String strContent = getPara("content");
        String strOpinion = getPara("opinion");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        UserBean userBean = UserModel.dao.getUser(strUsername);

        PurchaseOpinionBean purchaseOpinionBean = new PurchaseOpinionBean();
        purchaseOpinionBean.strApprovePerson = userBean.strRealName;
        purchaseOpinionBean.strApproveDepartment = userBean.strDepartment;
        purchaseOpinionBean.strOpinion = strContent;
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        purchaseOpinionBean.strApproveDate = sdf.format(currentTime);
        if (strOpinion.equals("agree")) {
            PurchaseModel.dao.agreePurchase(strPurchaseID, purchaseOpinionBean);
        } else {
            PurchaseModel.dao.disagreePurchase(strPurchaseID, purchaseOpinionBean);
        }
        setAttr("result", "success");
        renderJson();
    }

    // 上传附件
    public void upload_file() {
        String strPurchaseID = getPara("purchase_id");
        if (strPurchaseID != null) {
            PurchaseAttachFileBean item = new PurchaseAttachFileBean();
            item.strPurchaseID = strPurchaseID;
            item.strFileID = getPara("file_id");

            UploadFile uploadFile = getFile();
            item.strFileName = uploadFile.getOriginalFileName();

            File file = uploadFile.getFile();
            item.fileSize = (int)file.length();
            item.strFilePath = file.getPath();

            PurchaseModel.dao.addAttachFile(strPurchaseID, item);
        }
        setAttr("result", "success");
        renderJson();
    }

    // 下载附件
    public void download_file() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            String strFileID = getPara("file_id");
            PurchaseAttachFileBean item = purchaseBean.getAttachFileItem(strFileID);
            if (item != null) {
                renderFile(new File(item.strFilePath));
            }
        } else {
            renderJson();
        }
    }

    // 删除附件
    public void remove_file() {
        String strPurchaseID = getPara("purchase_id");
        if (strPurchaseID != null) {
            PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
            if (purchaseBean != null) {
                String strFileID = getPara("file_id");
                PurchaseAttachFileBean item = purchaseBean.getAttachFileItem(strFileID);
                if (item != null) {
                    File file = new File(item.strFilePath);
                    if (file != null) {
                        file.delete();
                    }
                }
                purchaseBean.delAttachFile(strFileID);
            }
        }
        setAttr("result", "success");
        renderJson();
    }

    // 附件列表
    public void attach_files() {
        String strPurchaseID = getPara("purchase_id");
        if (strPurchaseID != null) {
            PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
            if (purchaseBean != null) {
                ArrayList<Map<String, String>> lst = purchaseBean.getJSONAttachFiles();
                setAttr("files", lst);
            }
        }
        renderJson();
    }

    // 基础信息
    public void base_info() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("result", "success");
            setAttr("base", purchaseBean.getJSONBaseData());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    public void funds_nature() {
        String strFunds_nature = FundsNatureModel.dao.getFundsNatureList().toString();
        renderText(strFunds_nature);
    }

    // 商品类列表
    public void commodity_list() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONProductItems(ProductTypeBean.COMMODITY));
            setAttr("total", purchaseBean.getJSONProductItems(ProductTypeBean.COMMODITY).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    // 服务类列表
    public void service_list() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONProductItems(ProductTypeBean.SERVICE));
            setAttr("total", purchaseBean.getJSONProductItems(ProductTypeBean.SERVICE).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    // 工程类列表
    public void engineering_list() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONProductItems(ProductTypeBean.ENGINEERING));
            setAttr("total", purchaseBean.getJSONProductItems(ProductTypeBean.ENGINEERING).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    // 审批意见列表
    public void opinion_list() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONOpinionItems());
            setAttr("total", purchaseBean.getJSONOpinionItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    // 投诉列表
    public void complaints_list() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONComplaintsItems());
            setAttr("total", purchaseBean.getJSONComplaintsItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }
}
