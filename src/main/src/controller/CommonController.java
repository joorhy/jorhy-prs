package controller;

import bean.*;
import com.jfinal.core.Controller;
import model.PackageModel;
import model.PurchaseModel;
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

    public void getPacketBase() {
        String strPacketID = getPara("packet_id");
        PackageBean packageBean = PackageModel.dao.getPackage(strPacketID);
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
        PackageBean packageBean = PackageModel.dao.getPackage(strPacketID);
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

    public void serviceList() {
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

    public void engineeringList() {
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

    public void opinionList() {
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

    public void complaintsList() {
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

    public void downloadFile() {
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

    public void getAttachFiles() {
        String strPurchaseID = getPara("purchase_id");
        String strPacketID = getPara("package_id");
        if (strPurchaseID != null) {
            PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
            if (purchaseBean != null) {
                ArrayList<Map<String, String>> lst = purchaseBean.getJSONAttachFiles();
                setAttr("files", lst);
            }

        }

        if (strPacketID != null)
        {
            PackageBean packageBean = PackageModel.dao.getPackage(strPacketID);
            if (packageBean != null) {
                ArrayList<Map<String, String>> lst = packageBean.getJSONAttachFiles();
                setAttr("files", lst);
            }
        }
        renderJson();
    }

    public void removeFile() {
        String strPurchaseID = getPara("purchase_id");
        String strPackageID = getPara("package_id");
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
        if (strPackageID != null) {
            PackageBean packageBean = PackageModel.dao.getPackage(strPurchaseID);
            if (packageBean != null) {
                String strFileID = getPara("file_id");
                PackageAttachFileBean item = packageBean.getAttachFileItem(strFileID);
                if (item != null) {
                    File file = new File(item.strFilePath);
                    if (file != null) {
                        file.delete();
                    }
                }
                packageBean.delAttachFile(strFileID);
            }
        }
        setAttr("result", "success");
        renderJson();
    }

    // 提交审核
    public void approve() {
        String strPurchaseID = getPara("purchase_id");
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
            PurchaseModel.dao.agreePurchase(strPurchaseID, opinionBean);
        } else {
            PurchaseModel.dao.disagreePurchase(strPurchaseID, opinionBean);
        }
        setAttr("result", "success");
        renderJson();
    }
}
