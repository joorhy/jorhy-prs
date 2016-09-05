package controller;

import bean.*;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import model.PackageModel;
import model.PurchaseModel;
import model.UserModel;
import org.activiti.engine.impl.util.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Joo on 2016/9/1.
 */
public class PackageController extends Controller {
    // 保存
    public void save() {
        String strBaseData = getPara("base");
        String strProducts = getPara("products");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PackageBean packageBean = PackageModel.dao.getPackage(objBaseData.getString("package_id"));
        if (packageBean == null) {
            packageBean = new PackageBean();
        }

        JSONObject objProducts = new JSONObject(strProducts);
        packageBean.parseBaseData(objBaseData);
        packageBean.parseProductData(objProducts);
        PackageModel.dao.savePackage(packageBean);

        setAttr("result", "success");
        renderJson();
    }

    public void submit() {

    }

    // 取消
    public void cancel() {
        String strPacketID = getPara("package_id");
        PackageModel.dao.removePackage(strPacketID);

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
    public void upload_file() {
        String strPackageID = getPara("package_id");
        if (strPackageID != null) {
            PackageAttachFileBean item = new PackageAttachFileBean();
            item.strPackageID = strPackageID;
            item.strFileID = getPara("file_id");

            UploadFile uploadFile = getFile();
            item.strFileName = uploadFile.getOriginalFileName();

            File file = uploadFile.getFile();
            item.strFileSize = String.valueOf(file.length());
            item.strFilePath = file.getPath();

            PackageModel.dao.addAttachFile(strPackageID, item);
        }
        setAttr("result", "success");
        renderJson();
    }

    // 下载附件
    public void download_file() {
        String strPackageID = getPara("package_id");
        PackageBean packageBean = PackageModel.dao.getPackage(strPackageID);
        if (packageBean != null) {
            String strFileID = getPara("file_id");
            PackageAttachFileBean item = packageBean.getAttachFileItem(strFileID);
            if (item != null) {
                renderFile(new File(item.strFilePath));
            }
        } else {
            renderJson();
        }
    }

    // 删除附件
    public void remove_file() {
        String strPackageID = getPara("package_id");
        if (strPackageID != null) {
            PackageBean packageBean = PackageModel.dao.getPackage(strPackageID);
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

    // 附件列表
    public void attach_files() {
        String strPacketID = getPara("package_id");
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

    // 基础信息
    public void base_info() {
        String strPacketID = getPara("package_id");
        PackageBean packageBean = PackageModel.dao.getPackage(strPacketID);
        if (packageBean != null) {
            setAttr("result", "success");
            setAttr("base", packageBean.getJSONBaseData());
        } else {
            setAttr("result", "failed");
        }
        renderJson();
    }

    // 分包列表
    public void package_list() {
        String strPacketID = getPara("package_id");
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
}
