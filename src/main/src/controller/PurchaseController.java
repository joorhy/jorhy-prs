package controller;

import bean.*;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import model.*;
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
        ArrayList<Map<String, String>> lst = ProductModel.dao.getToDivideItems(strPurchaseID);
        setAttr("rows", lst);
        setAttr("total", lst.size());
        renderJson();
    }

    // 保存按钮
    public void save() {
        String strBaseData = getPara("base");
        String strCommodity = getPara("commodity");
        String strService = getPara("service");
        String strEngineering = getPara("engineering");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        JSONObject objBaseData = new JSONObject(strBaseData);
        PurchaseModel.dao.savePurchase(objBaseData, strUsername);

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
        int nPurchaseType = Integer.valueOf(getPara("purchase_type"));
        PurchaseModel.dao.submitPurchase(strPurchasingID, nPurchaseType);
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

    // 审批意见
    public void approve() {
        String strPurchaseID = getPara("purchase_id");
        String strContent = getPara("content");
        String strOpinion = getPara("opinion");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        UserBean userBean = UserModel.dao.getUser(strUsername);

        PurchaseOpinionBean purchaseOpinionBean = new PurchaseOpinionBean();
        purchaseOpinionBean.userID = userBean.userID;
        purchaseOpinionBean.strApprovePerson = userBean.strRealName;
        purchaseOpinionBean.strApproveDepartment = userBean.strDepartment;
        purchaseOpinionBean.strOpinion = strContent;
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        purchaseOpinionBean.strApproveDate = sdf.format(currentTime);
        if (strOpinion.equals("agree")) {
            purchaseOpinionBean.nextApproveRoleId = Integer.valueOf(getPara("next_role_id"));
            purchaseOpinionBean.purchaseNatureId = Integer.valueOf(getPara("purchase_nature_id"));
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
        String strFileID = getPara("file_id");
        PurchaseAttachFileBean item = PurchaseFileAttachModel.dao.getAttachFileItem(strFileID);
        if (item != null) {
            renderFile(new File(item.strFilePath));
        } else {
            renderJson();
        }
    }

    // 删除附件
    public void remove_file() {
        String strPurchaseID = getPara("purchase_id");
        if (strPurchaseID != null) {
            String strFileID = getPara("file_id");
            PurchaseAttachFileBean item = PurchaseFileAttachModel.dao.getAttachFileItem(strFileID);
            if (item != null) {
                File file = new File(item.strFilePath);
                if (file != null) {
                    file.delete();
                }
            }
            PurchaseFileAttachModel.dao.delAttachFile(strFileID);
        }
        setAttr("result", "success");
        renderJson();
    }

    // 附件列表
    public void attach_files() {
        String strPurchaseID = getPara("purchase_id");
        if (strPurchaseID != null) {
            ArrayList<Map<String, String>> lst = PurchaseModel.dao.getAttachFiles(strPurchaseID);
            setAttr("files", lst);
        }
        renderJson();
    }

    // 基础信息
    public void base_info() {
        String strPurchaseID = getPara("purchase_id");
        setAttr("result", "success");
        setAttr("base", PurchaseModel.dao.getBaseData(strPurchaseID));

        renderJson();
    }

    public void purchase_type() {
        String strPurchaseType = PurchaseTypeModel.dao.getPurchaseTypeList().toString();
        renderText(strPurchaseType);
    }

    public void funds_nature() {
        String strFunds_nature = FundsNatureModel.dao.getFundsNatureList().toString();
        renderText(strFunds_nature);
    }

    public void approve_leader() {
        String strApproveLeader = DirectorsModel.dao.getDirectorList().toString();
        renderText(strApproveLeader);
    }

    public void approve_sector() {
        String strApproveSector = FinanceDeptModel.dao.getFinanceDeptList().toString();
        renderText(strApproveSector);
    }

    public void purchase_nature() {
        String strPurchaseNature = PurchaseNatureModel.dao.getPurchaseNatureList().toString();
        renderText(strPurchaseNature);
    }

    // 商品类列表
    public void commodity_list() {
        String strPurchaseID = getPara("purchase_id");
        ArrayList<Map<String, String>> lst =
                ProductModel.dao.getProductItems(strPurchaseID, ProductTypeBean.COMMODITY);
        setAttr("rows", lst);
        setAttr("total", lst.size());
        renderJson();
    }

    // 服务类列表
    public void service_list() {
        String strPurchaseID = getPara("purchase_id");
        ArrayList<Map<String, String>> lst =
                ProductModel.dao.getProductItems(strPurchaseID, ProductTypeBean.SERVICE);
        setAttr("rows", lst);
        setAttr("total", lst.size());
        renderJson();
    }

    // 工程类列表
    public void engineering_list() {
        String strPurchaseID = getPara("purchase_id");
        ArrayList<Map<String, String>> lst =
                ProductModel.dao.getProductItems(strPurchaseID, ProductTypeBean.ENGINEERING);
        setAttr("rows", lst);
        setAttr("total", lst.size());
        renderJson();
    }

    // 审批意见列表
    public void opinion_list() {
        String strPurchaseID = getPara("purchase_id");
        ArrayList<Map<String, String>> lst = PurchaseModel.dao.getOpinionItems(strPurchaseID);
        setAttr("rows", lst);
        setAttr("total", lst.size());
        renderJson();
    }

    // 投诉列表
    public void complaints_list() {
        String strPurchaseID = getPara("purchase_id");
        ArrayList<Map<String, String>> lst = PurchaseModel.dao.getComplaintsItems(strPurchaseID);
        setAttr("rows", lst);
        setAttr("total", lst.size());
        renderJson();
    }
}
