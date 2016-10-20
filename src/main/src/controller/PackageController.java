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
import model.PackageModel;

/**
 * Created by Joo on 2016/9/1.
 */
public class PackageController extends Controller {
    public void submit() {
        String strPackageID = getPara("package_id");
        PackageModel.dao.submitPackage(strPackageID);

        setAttr("result", "success");
        renderJson();
    }
    // 保存
    public void save() {
        String strBaseData = getPara("base");
        String strProducts = getPara("products");

        JSONObject objBaseData = new JSONObject(strBaseData);
        JSONObject objProducts = new JSONObject(strProducts);
        String strPackageID = objBaseData.getString("package_id");
        String strPurchaseID = objBaseData.getString("purchase_id");
        PackageModel.dao.addProducts(strPackageID, strPurchaseID, objProducts);
        PackageModel.dao.savePackage(objBaseData);

        setAttr("result", "success");
        renderJson();
    }

    // 取消
    public void cancel() {
        String strPackageID = getPara("package_id");
        PackageModel.dao.removePackage(strPackageID);

        setAttr("result", "success");
        renderJson();
    }

    // 审批意见
    public void approve() {
        String strPurchaseID = getPara("package_id");
        String strContent = getPara("content");
        String strOpinion = getPara("opinion");

        String strUsername = String.valueOf(getSession().getAttribute("loginUser"));
        UserBean userBean = UserModel.dao.getUser(strUsername);

        PackageOpinionBean packageOpinionBean = new PackageOpinionBean();
        packageOpinionBean.strApprovePerson = userBean.strRealName;
        packageOpinionBean.strApproveDepartment = userBean.strDepartment;
        packageOpinionBean.strOpinion = strContent;
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        packageOpinionBean.strApproveDate = sdf.format(currentTime);
        if (strOpinion.equals("agree")) {
            PackageModel.dao.agreePackage(strPurchaseID, packageOpinionBean);
        } else {
            PackageModel.dao.disagreePackage(strPurchaseID, packageOpinionBean);
        }
        setAttr("result", "success");
        renderJson();
    }

    // 上传附件
    private void upload_file(String strPurchaseID, String strPackageID, String strFileType) {
        if (strPurchaseID != null && strPackageID != null) {
            PackageAttachFileBean item = new PackageAttachFileBean();
            item.strPackageID = strPackageID;
            item.strFileID = getPara("file_id");

            UploadFile uploadFile = getFile();
            item.strFileName = uploadFile.getOriginalFileName();

            File file = uploadFile.getFile();
            item.fileSize = (int)file.length();
            item.strFilePath = file.getPath();

            PackageModel.dao.addAttachFile(strPurchaseID, strPackageID, strFileType, item);
        }
    }

    public void upload_evaluation_file() {
        String strPackageID = getPara("package_id");
        String strPurchaseID = getPara("purchase_id");
        upload_file(strPurchaseID, strPackageID, PackageAttachFileBean.EVALUATION);
        setAttr("result", "success");
        renderJson();
    }

    public void upload_acceptance_file() {
        String strPackageID = getPara("package_id");
        String strPurchaseID = getPara("purchase_id");
        upload_file(strPurchaseID, strPackageID, PackageAttachFileBean.ACCEPTANCE);
        setAttr("result", "success");
        renderJson();
    }

    // 下载附件
    public void download_file() {
        String strPackageID = getPara("package_id");
        String strFileID = getPara("file_id");
        PackageAttachFileBean item = PackageFileAttachModel.dao.getAttachFileItem(strFileID);
        if (item != null) {
            renderFile(new File(item.strFilePath));
        } else {
            renderJson();
        }
    }

    // 删除附件
    public void remove_file() {
        String strPackageID = getPara("package_id");
        if (strPackageID != null) {
            String strFileID = getPara("file_id");
            PackageAttachFileBean item = PackageFileAttachModel.dao.getAttachFileItem(strFileID);
            if (item != null) {
                File file = new File(item.strFilePath);
                if (file != null) {
                    file.delete();
                }
            }
            PackageFileAttachModel.dao.removeAttachFile(strFileID);
        }
        setAttr("result", "success");
        renderJson();
    }

    // 附件列表
    public void attach_evaluation_files() {
        String strPackageID = getPara("package_id");
        if (strPackageID != null)
        {
            ArrayList<Map<String, String>> lst = PackageModel.dao.getAttachFiles(strPackageID,
                    PackageAttachFileBean.EVALUATION);
            setAttr("files", lst);
        }
        renderJson();
    }

    public void attach_acceptance_files() {
        String strPackageID = getPara("package_id");
        if (strPackageID != null)
        {
            ArrayList<Map<String, String>> lst = PackageModel.dao.getAttachFiles(strPackageID,
                    PackageAttachFileBean.ACCEPTANCE);
            setAttr("files", lst);
        }
        renderJson();
    }

    // 基础信息
    public void base_info() {
        String strPurchaseID = getPara("purchase_id");
        String strPackageID = getPara("package_id");
        setAttr("result", "success");
        if (strPurchaseID != null) {
            setAttr("base", PackageModel.dao.getBaseDataByPurchaseID(strPurchaseID));
        } else {
            setAttr("base", PackageModel.dao.getBaseDataByPackageID(strPackageID));
        }
        renderJson();
    }

    // 分包列表
    public void package_list() {
        String strPackageID = getPara("package_id");
        ArrayList<Map<String, String>> productArr = PackageModel.dao.getProductItems(strPackageID);
        setAttr("rows", productArr);
        setAttr("total", productArr.size());

        renderJson();
    }
}
