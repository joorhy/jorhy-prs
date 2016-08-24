package controller;

import bean.AttachFileBean;
import bean.OpinionBean;
import bean.PurchasingBean;
import bean.UserBean;
import com.jfinal.core.Controller;
import model.PurchasingModel;
import model.UserModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            setAttr("rows", purchasingBean.getJSONProductItems(PurchasingBean.COMMODITY));
            setAttr("total", purchasingBean.getJSONProductItems(PurchasingBean.COMMODITY).size());
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
            setAttr("rows", purchasingBean.getJSONProductItems(PurchasingBean.SERVICE));
            setAttr("total", purchasingBean.getJSONProductItems(PurchasingBean.SERVICE).size());
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
            setAttr("rows", purchasingBean.getJSONProductItems(PurchasingBean.ENGINEERING));
            setAttr("total", purchasingBean.getJSONProductItems(PurchasingBean.ENGINEERING).size());
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
            setAttr("rows", purchasingBean.getJSONOpinionItems());
            setAttr("total", purchasingBean.getJSONOpinionItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void complaintsList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("rows", purchasingBean.getJSONComplaintsItems());
            setAttr("total", purchasingBean.getJSONComplaintsItems().size());
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
            ArrayList<Map<String, String>> lst = purchasingBean.getJSONAttachFiles();
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

    // 提交审核
    public void approve() {
        String strPurchasingID = getPara("purchasing_id");
        String strContent = getPara("content");
        String strOpinion = getPara("opinion");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        UserBean userBean = UserModel.dao.getUser(strUsername);

        OpinionBean opinionBean = new OpinionBean();
        opinionBean.strApprovePerson = userBean.strRealName;
        opinionBean.strApproveDepartment = userBean.strDepartment;
        opinionBean.strOpinion = strContent;
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        opinionBean.strApproveDate = sdf.format(currentTime);
        if (strOpinion.equals("agree")) {
            PurchasingModel.dao.agreePurchasing(strPurchasingID, opinionBean);
        } else {
            PurchasingModel.dao.disagreePurchasing(strPurchasingID, opinionBean);
        }
        setAttr("result", "success");
        renderJson();
    }
}
