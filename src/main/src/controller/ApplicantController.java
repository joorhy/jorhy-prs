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

    // 提交按钮
    public void save() {
        String strBaseData = getPara("base");
        String strCommodity = getPara("commodity");
        String strService = getPara("service");
        String strEngineering = getPara("engineering");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PurchasingData purchasingData = PurchasingInfo.dao.getPrjData(objBaseData.getString("purchasing_id"));
        if (purchasingData == null) {
            purchasingData = new PurchasingData();
        }
        purchasingData.ReadBaseData(objBaseData);

        JSONObject objCommodity = new JSONObject(strCommodity);
        purchasingData.ReadProductData(objCommodity, PurchasingData.COMMODITY);

        JSONObject objService = new JSONObject(strService);
        purchasingData.ReadProductData(objService, PurchasingData.SERVICE);

        JSONObject objEngineering = new JSONObject(strEngineering);
        purchasingData.ReadProductData(objEngineering, PurchasingData.ENGINEERING);

        PurchasingInfo.dao.addPurchasing(purchasingData);

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
        System.setProperty("UPLOAD_PATH", uploadFile.getUploadPath());

        PurchasingData purchasingData = PurchasingInfo.dao.getPrjData(item.strPurchasingID);
        if (purchasingData == null) {
            purchasingData = new PurchasingData();
        }
        purchasingData.addAttachFile(item);

        setAttr("result", "success");
        renderJson();
    }

    public void downloadFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingData purchasingData = PurchasingInfo.dao.getPrjData(strPurchasingID);
        if (purchasingData != null) {
            String strFileID = getPara("file_id");
            AttachFileItem item = purchasingData.getAttachFileItem(strFileID);
            if (item != null) {
                String strFilePath = System.getProperty("UPLOAD_PATH");
                renderFile(new File(strFilePath + File.separator + item.strFileName));
            }
        } else {
            renderJson();
        }
    }

    public void getAttachFiles() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingData purchasingData = PurchasingInfo.dao.getPrjData(strPurchasingID);
        if (purchasingData != null) {
            ArrayList<Map<String, String>> lst = purchasingData.getAttachFileList();
            setAttr("files", lst);
        }
        renderJson();
    }

    public void removeFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingData purchasingData = PurchasingInfo.dao.getPrjData(strPurchasingID);
        if (purchasingData != null) {
            String strFileID = getPara("file_id");
            purchasingData.delAttachFile(strFileID);
        }
        setAttr("result", "success");
        renderJson();
    }

    public void applicantTree() {
        ArrayList<PurchasingData> lstPurchasingData = PurchasingInfo.dao.getPrjDataList();

        JSONArray newProjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasingData.size(); i++) {
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasingData.get(i).getPurchasingID());
            childrenNode.put("text", lstPurchasingData.get(i).getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            newProjChildren.put(childrenNode);
        }
        JSONObject newProj = new JSONObject();
        newProj.put("id", "new_proj");
        newProj.put("text", "新建采购过程");
        newProj.put("iconCls", "icon-cut");
        newProj.put("children", newProjChildren);
        JSONObject committedProj = new JSONObject();
        committedProj.put("id", "committed_proj");
        committedProj.put("text", "已提交采购过程");
        committedProj.put("iconCls", "icon-cut");
        JSONObject executedProj = new JSONObject();
        executedProj.put("id", "executed_proj");
        executedProj.put("text", "已执行采购过程");
        executedProj.put("iconCls", "icon-cut");
        JSONObject implementedProj = new JSONObject();
        implementedProj.put("id", "implemented_proj");
        implementedProj.put("text", "已完成采购过程");
        implementedProj.put("iconCls", "icon-cut");

        JSONArray lstChildren = new JSONArray();
        lstChildren.put(newProj);
        lstChildren.put(committedProj);
        lstChildren.put(executedProj);
        lstChildren.put(implementedProj);

        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "root");
        rootNode.put("text", "采购信息管理中心");
        rootNode.put("iconCls", "icon-cut");
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        renderText(lstRoot.toString());
    }

    public void getBaseData() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("result", "success");
            setAttr("base", data.GetBaseDataObj().toString());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    public void commodityList() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("rows", data.getProductDataObj(PurchasingData.COMMODITY));
            setAttr("total", data.getProductDataObj(PurchasingData.COMMODITY).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void serviceList() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("rows", data.getProductDataObj(PurchasingData.SERVICE));
            setAttr("total", data.getProductDataObj(PurchasingData.SERVICE).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void engineeringList() {
        String strID = getPara("id");
        PurchasingData data = PurchasingInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("rows", data.getProductDataObj(PurchasingData.ENGINEERING));
            setAttr("total", data.getProductDataObj(PurchasingData.ENGINEERING).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }
}
