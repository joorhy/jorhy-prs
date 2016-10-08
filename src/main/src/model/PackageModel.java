package model;

import bean.*;
import com.jfinal.plugin.activerecord.Model;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PackageModel extends Model<PackageModel> {
    public static final PackageModel dao = new PackageModel();

    public void savePackage(JSONObject obj) {
        String strPackID = obj.getString("package_uuid");
        String strPackCode = obj.getString("package_code");
        String strPurAddress = obj.getString("package_address");
        String strExpertCount = obj.getString("expert_count");
        String strPurDate = obj.getString("pur_date");
        String strPurMethod = obj.getString("pur_method");
        String strPublicity = obj.getString("publicity");
        String strSupplier = obj.getString("vendor");
        String strAmount = obj.getString("amount");
        String strPurchaseID = obj.getString("purchase_id");

        String url = "select id from package p where p.package_uuid=" + strPackID;
        int nPackageId = 0;
                PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageId = packageModel.get("id");
            packageModel.set("package_uuid", strPurchaseID).set("package_code", strPackCode)
                    .set("package_address", strPurAddress).set("expert_count", strExpertCount)
                    .set("pur_date", strPurDate).set("pur_method", strPurMethod)
                    .set("publicity", strPublicity).set("vendor", strSupplier)
                    .set("amount", strAmount).set("package_activity_id", 1).update();
        } else {
            packageModel = new PackageModel();
            packageModel.set("package_uuid", strPurchaseID).set("package_code", strPackCode)
                    .set("package_address", strPurAddress).set("expert_count", strExpertCount)
                    .set("pur_date", strPurDate).set("pur_method", strPurMethod)
                    .set("publicity", strPublicity).set("vendor", strSupplier)
                    .set("amount", strAmount).set("package_activity_id", 1).save();
            nPackageId = packageModel.get("id");
        }
        ProductModel.dao.updatePackagedCount(strPurchaseID, 0);
    }

    public void removePackage(String strPacketID) {
        String url = "select p.id,pur.purchase_uuid from package p left join purchase pur on " +
                "p.purchase_id=pur.id where p.package_uuid=" + strPacketID;
        int nPackageId = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageId = packageModel.get("id");
            String strPurchaseID = packageModel.get("purchase_uuid");
            PackageFileAttachModel.dao.removePackageAttachFiles(nPackageId);
            ProductModel.dao.updatePackagedCount(strPurchaseID, 0);
        }
    }

    public String submitPackage(String strPackageID) {
        String url = "select id, package_activity_id from package p where p.package_uuid=" + strPackageID;

        int nPackageID = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageID = packageModel.get("id");
            int nPackageActivityID =  packageModel.get("package_activity_id");
            packageModel.set("package_activity_id", nPackageActivityID + 1);
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPackage(String strPackageID) {
        return ErrorCode.SUCCESS;
    }

    public String agreePackage(String strPackageID, PackageOpinionBean packageOpinionBean) {
        String url = "select id, package_activity_id from package p where p.package_uuid=" + strPackageID;

        int nPackageID = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageID = packageModel.get("id");
            int nPackageActivityID =  packageModel.get("package_activity_id");
            if (PackageRecordModel.dao.addApproveRecord(nPackageID, nPackageActivityID + 1, packageOpinionBean)
                    == ErrorCode.SUCCESS) {
                packageModel.set("package_activity_id", nPackageActivityID + 1);
            }
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePackage(String strPackageID, PackageOpinionBean packageOpinionBean) {
        String url = "select id from package p where package_uuid=" + strPackageID;

        int nPurchaseID = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPurchaseID = packageModel.get("id");
            packageModel.set("paclage_activity_id", 1);
        }
        return ErrorCode.SUCCESS;
    }

    public PackageBean getPackage(String strPacketID) {
        String sql = "select p.*, a.status from package p left join package_activity a on " +
                "p.package_activity_id=a.id where p.package_uuid='" + strPacketID + "'";
        PackageModel packageModel = PackageModel.dao.findFirst(sql);
        PackageBean purchaseBean = null;
        if (packageModel != null) {
            purchaseBean = new PackageBean();
        }

        return purchaseBean;
    }

    public ArrayList<Map<String, String>> getPackageList(String strPurchaseID) {
        String sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                "on p.purchase_id=pur.id";

        return getPackageList(strPurchaseID, sql);
    }

    public ArrayList<Map<String, String>> getToPayPackageList(String strPurchaseID) {
        String sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                "on p.purchase_id=pur.id left join package_activity a on p.package_activity_id=a.id";

        return getPackageList(strPurchaseID, sql);
    }

    private ArrayList<Map<String, String>> getPackageList(String strPurchaseID, String sql) {
        List<PackageModel> packageList = PackageModel.dao.find(sql);
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<packageList.size(); i++) {
            Map<String, String> node= new HashMap<String, String>();
            PackageModel item = packageList.get(i);
            if (item.get("purchase_uuid").equals(strPurchaseID)) {
                node.put("id", item.get("package_uuid").toString());
                node.put("text", "第" + String.valueOf(lst.size() + 1) + "包");
                node.put("iconCls", "icon-cut");
                node.put("type", "package");
                lst.add(node);
            }
        }
        return lst;
    }

    public String addProducts(String strPackageID, JSONObject obj) {
        String url = "select id from package p where p.package_uuid=" + strPackageID;

        int nPackageID = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageID = packageModel.get("id");
        } else {
            packageModel = new PackageModel();
            packageModel.set("purchase_uuid", strPackageID).save();
            nPackageID = packageModel.get("id");
        }

        int nTotal = obj.getInt("total");
        JSONArray arr = obj.getJSONArray("rows");
        for (int i=0; i<nTotal; i++) {
            JSONObject item = (JSONObject)arr.get(i);
            ProductBean prjItem = new ProductBean();
            prjItem.strPrjName = item.getString("prj_name");
            prjItem.nPrjCount = item.getInt("prj_count");
            prjItem.fPrjPrice = item.getDouble("prj_price");
            prjItem.strProductID = item.getString("project_id");
            prjItem.strPrjSpec = item.getString("prj_spec");
            prjItem.fPrjPrePrice = item.getDouble("prj_pre_price");
            prjItem.strPrjParam = item.getString("prj_param");
            prjItem.strPrjType = item.getString("prj_type");
            prjItem.nPackagedCount = 0;
            PackageProductModel.dao.addProduct(nPackageID, prjItem);
        }
        return ErrorCode.SUCCESS;
    }

    public void addAttachFile(String strPackageID, PackageAttachFileBean item) {
        String url = "select id from package p where p.package_uuid=" + strPackageID ;

        int nPackageID = 0;
        PackageModel packageModel= PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageID = packageModel.get("id");
        } else {
            packageModel = new PackageModel();
            packageModel.set("package_uuid", strPackageID).save();
            nPackageID = packageModel.get("id");
        }
        PackageFileAttachModel.dao.addAttachFile(nPackageID, item);
    }
}
