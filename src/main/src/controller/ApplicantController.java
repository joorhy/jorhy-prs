package controller;

import com.jfinal.core.Controller;
import bean.PrjData;
import com.jfinal.upload.UploadFile;
import model.ProjectInfo;
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

        PrjData prjData = new PrjData();

        JSONObject objBaseData = new JSONObject(strBaseData);
        prjData.ReadBaseData(objBaseData);

        JSONObject objCommodity = new JSONObject(strCommodity);
        prjData.ReadProductData(objCommodity, PrjData.COMMODITY);

        JSONObject objService = new JSONObject(strService);
        prjData.ReadProductData(objService, PrjData.SERVICE);

        JSONObject objEngineering = new JSONObject(strEngineering);
        prjData.ReadProductData(objEngineering, PrjData.ENGINEERING);

        ProjectInfo.dao.addProject(prjData);

        setAttr("result", "success");
        renderJson();
    }

    public void uploadFile() {
        UploadFile uploadFile = getFile();
        String fileName = uploadFile.getOriginalFileName();
        File file = uploadFile.getFile();
        file.delete();
    }

    public void applicantTree() {
        ArrayList<PrjData> lstPrjData = ProjectInfo.dao.getPrjDataList();

        JSONArray newProjChildren = new JSONArray();
        for (int i=0; i<lstPrjData.size(); i++) {
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", String.valueOf(i));
            childrenNode.put("text", lstPrjData.get(i).getPurCode());
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
        PrjData data = ProjectInfo.dao.getPrjData(strID);
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
        PrjData data = ProjectInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("rows", data.getProductDataObj(PrjData.COMMODITY));
            setAttr("total", data.getProductDataObj(PrjData.COMMODITY).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void serviceList() {
        String strID = getPara("id");
        PrjData data = ProjectInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("rows", data.getProductDataObj(PrjData.SERVICE));
            setAttr("total", data.getProductDataObj(PrjData.SERVICE).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void engineeringList() {
        String strID = getPara("id");
        PrjData data = ProjectInfo.dao.getPrjData(strID);
        if (data != null) {
            setAttr("rows", data.getProductDataObj(PrjData.ENGINEERING));
            setAttr("total", data.getProductDataObj(PrjData.ENGINEERING).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }
}
