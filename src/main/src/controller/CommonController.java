package controller;

import bean.*;
import com.jfinal.core.Controller;
import model.PacketModel;
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
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            setAttr("result", "success");
            setAttr("base", purchaseBean.getJSONBaseData());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    public void getPacketBase() {
        String strPacketID = getPara("packet_id");
        PackageBean packageBean = PacketModel.dao.getPackage(strPacketID);
        if (packageBean != null) {
            setAttr("result", "success");
            setAttr("base", packageBean.getJSONBaseData());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    public void getPacketList() {
        String strPacketID = getPara("packet_id");
        PackageBean packageBean = PacketModel.dao.getPackage(strPacketID);
        if (packageBean != null) {
            setAttr("rows", packageBean.getJSONPacketItems());
            setAttr("total", packageBean.getJSONPacketItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void commodityList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONProductItems(ProductTypeBean.COMMODITY));
            setAttr("total", purchaseBean.getJSONProductItems(ProductTypeBean.COMMODITY).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void serviceList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONProductItems(ProductTypeBean.SERVICE));
            setAttr("total", purchaseBean.getJSONProductItems(ProductTypeBean.SERVICE).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void engineeringList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONProductItems(ProductTypeBean.ENGINEERING));
            setAttr("total", purchaseBean.getJSONProductItems(ProductTypeBean.ENGINEERING).size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void opinionList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONOpinionItems());
            setAttr("total", purchaseBean.getJSONOpinionItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void complaintsList() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONComplaintsItems());
            setAttr("total", purchaseBean.getJSONComplaintsItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void downloadFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            String strFileID = getPara("file_id");
            AttachFileBean item = purchaseBean.getAttachFileItem(strFileID);
            if (item != null) {
                renderFile(new File(item.strFilePath));
            }
        } else {
            renderJson();
        }
    }

    public void getAttachFiles() {
        String strPurchasingID = getPara("purchasing_id");
        String strPacketID = getPara("packet_id");
        if (strPacketID.isEmpty()) {
            PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
            if (purchaseBean != null) {
                ArrayList<Map<String, String>> lst = purchaseBean.getJSONAttachFiles();
                setAttr("files", lst);
            }

        } else {
            setAttr("files", new ArrayList<Map<String, String>>());
        }
        renderJson();
    }

    public void removeFile() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchaseBean != null) {
            String strFileID = getPara("file_id");
            AttachFileBean item = purchaseBean.getAttachFileItem(strFileID);
            if (item != null) {
                File file = new File(item.strFilePath);
                if (file != null) {
                    file.delete();
                }
            }
            purchaseBean.delAttachFile(strFileID);
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
