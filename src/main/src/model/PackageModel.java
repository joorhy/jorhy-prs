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
        String strPackageID = obj.getString("package_id");
        String strPackCode = obj.getString("pack_code");
        String strPurAddress = obj.getString("pur_address");
        int nExpertCount = obj.getInt("expert_count");
        String strPurDate = obj.getString("pur_date");
        String strPurMethod = obj.getString("pur_method");
        Boolean isPublicity = obj.getBoolean("pur_publicity");
        String strSupplier = obj.getString("pur_supplier");
        double fAmount = obj.getDouble("pur_amount");
        String strPurchaseID = obj.getString("purchase_id");

        String url = "select id from package p where p.package_uuid='" + strPackageID + "'";
        int nPackageId = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageId = packageModel.get("id");
            packageModel.set("package_uuid", strPackageID).set("package_code", strPackCode)
                    .set("pur_address", strPurAddress).set("expert_count", nExpertCount)
                    .set("pur_date", strPurDate).set("pur_method", strPurMethod)
                    .set("publicity", isPublicity).set("vendor", strSupplier)
                    .set("amount", fAmount).set("package_activity_id", PackageActivityBean.INITIALIZE).update();
        } else {
            packageModel = new PackageModel();
            packageModel.set("package_uuid", strPackageID).set("package_code", strPackCode)
                    .set("pur_address", strPurAddress).set("expert_count", nExpertCount)
                    .set("pur_date", strPurDate).set("pur_method", strPurMethod)
                    .set("publicity", isPublicity).set("vendor", strSupplier)
                    .set("amount", fAmount).set("package_activity_id", PackageActivityBean.INITIALIZE).save();
            nPackageId = packageModel.get("id");
        }
    }

    public void removePackage(String strPacketID) {
        String url = "select p.id,pur.purchase_uuid from package p left join purchase pur on " +
                "p.purchase_id=pur.id where p.package_uuid='" + strPacketID + "'";
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
        String url = "select id, package_activity_id from package p where p.package_uuid='"
                + strPackageID + "'";

        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            int nPackageActivityID =  packageModel.get("package_activity_id");
            packageModel.set("package_activity_id", nPackageActivityID + 1).update();
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPackage(String strPackageID) {
        return ErrorCode.SUCCESS;
    }

    public String agreePackage(String strPackageID, PackageOpinionBean packageOpinionBean) {
        String url = "select id, package_activity_id from package p where p.package_uuid='"
                + strPackageID + "'";

        int nPackageID = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPackageID = packageModel.get("id");
            int nPackageActivityID =  packageModel.get("package_activity_id");
            if (PackageRecordModel.dao.addApproveRecord(nPackageID, nPackageActivityID + 1, packageOpinionBean)
                    == ErrorCode.SUCCESS) {
                packageModel.set("package_activity_id", nPackageActivityID + 1).update();
            }
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePackage(String strPackageID, PackageOpinionBean packageOpinionBean) {
        String url = "select id from package p where package_uuid='" + strPackageID + "'";

        int nPurchaseID = 0;
        PackageModel packageModel = PackageModel.dao.findFirst(url);
        if (packageModel != null) {
            nPurchaseID = packageModel.get("id");
            packageModel.set("paclage_activity_id", 1);
        }
        return ErrorCode.SUCCESS;
    }

    public Map<String, String> getBaseDataByPackageID(String strPacketID) {
        String sql = "select p.*, pur.code as pur_code, pur.name as pur_name from package p left join " +
                "package_activity a on p.package_activity_id=a.id left join purchase pur " +
                "on p.purchase_id=pur.id where p.package_uuid='" + strPacketID + "'";
        PackageModel packageModel = PackageModel.dao.findFirst(sql);

        Map<String, String> obj = new HashMap<String, String>();
        if (packageModel != null) {
            obj.put("pack_type", "packaged");
            obj.put("pur_code", packageModel.getStr("pur_code"));
            obj.put("pur_name", packageModel.getStr("pur_name"));
            obj.put("package_id", packageModel.getStr("package_uuid"));
            obj.put("pack_code", packageModel.getStr("package_code"));
            obj.put("pur_address", packageModel.getStr("pur_address"));
            obj.put("expert_count",packageModel.getInt("expert_count").toString());
            obj.put("pur_date", packageModel.getStr("pur_date"));
            obj.put("pur_method", packageModel.getStr("pur_method"));
            obj.put("pur_publicity", packageModel.getBoolean("publicity").toString());
            obj.put("pur_supplier", packageModel.getStr("vendor"));
            obj.put("pur_amount", packageModel.getDouble("amount").toString());
            obj.put("purchase_id", packageModel.getStr("purchase_uuid"));
        }

        return obj;
    }

    public Map<String, String> getBaseDataByPurchaseID(String strPurchaseID) {
        String sql = "select pur.code as pur_code, pur.name as pur_name from purchase pur " +
                "where pur.purchase_uuid='" + strPurchaseID + "'";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);

        Map<String, String> obj = new HashMap<String, String>();
        if (purchaseModel != null) {
            obj.put("pack_type", "new_package");
            obj.put("pur_code", purchaseModel.getStr("pur_code"));
            obj.put("pur_name", purchaseModel.getStr("pur_name"));
        }

        return obj;
    }

    public  ArrayList<Map<String, String>> getPackageList(String strPurchaseID, int fromType, int toType) {
        String sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                "on p.purchase_id=pur.id where p.package_activity_id>=" + fromType +
                " and p.package_activity_id<=" + toType;
        return getPackageList(strPurchaseID, sql);
    }

    public ArrayList<Map<String, String>> getPackageList(String strPurchaseID, int nPackageType) {
        String sql;
        switch (nPackageType) {
            case PackageActivityBean.INITIALIZE:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                    "on p.purchase_id=pur.id where p.package_activity_id=" + PackageActivityBean.INITIALIZE;
                break;
            case PackageActivityBean.ACCEPTANCE:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                        "on p.purchase_id=pur.id where p.package_activity_id=" + PackageActivityBean.ACCEPTANCE;
                break;
            case PackageActivityBean.APPLIED:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                        "on p.purchase_id=pur.id where p.package_activity_id=" + PackageActivityBean.APPLIED;
                break;
            case PackageActivityBean.TO_PAY:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                    "on p.purchase_id=pur.id where p.package_activity_id=" + PackageActivityBean.TO_PAY;
                break;
            case PackageActivityBean.TO_REPAY:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                        "on p.purchase_id=pur.id where p.package_activity_id=" + PackageActivityBean.TO_REPAY;
                break;
            case PackageActivityBean.PAID:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                        "on p.purchase_id=pur.id where p.package_activity_id=" + PackageActivityBean.PAID;
                break;
            default:
                sql = "select p.package_uuid, pur.purchase_uuid from package p left join purchase pur " +
                        "on p.purchase_id=pur.id";
                break;
        }

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

    public ArrayList<Map<String, String>> getProductItems(String strPackageID) {
        String sql = "select pp.* from package_product pp left join package p on pp.package_id=p.id " +
                "where p.package_uuid='" + strPackageID + "'";

        List<PackageProductModel> lstModel = PackageProductModel.dao.find(sql);
        ArrayList<Map<String, String>> productArray = new ArrayList<Map<String, String>>();
        for (int i = 0; i < lstModel.size(); i++) {
            PackageProductModel item = lstModel.get(i);
            Map<String, String> obj = new HashMap<String, String>();
            obj.put("project_id", item.getStr("uuid"));
            obj.put("product_type", item.getStr("type"));
            obj.put("prj_name", item.getStr("name"));
            obj.put("prj_count", item.getInt("count").toString());
            obj.put("prj_price", item.getDouble("price").toString());
            obj.put("prj_pre_price", item.getDouble("pre_price").toString());
            obj.put("prj_param", item.getStr("param"));
            obj.put("prj_spec", item.getStr("spec"));
            productArray.add(obj);
        }
        return productArray;
    }

    public ArrayList<Map<String, String>> getAttachFiles(String strPackageID, String strFileType) {
        String sql = "select * from package_attach_file paf left join package p on paf.package_id=p.id " +
                "where p.package_uuid='" + strPackageID + "' and paf.type='" + strFileType + "'";

        List<PackageFileAttachModel> lstModel = PackageFileAttachModel.dao.find(sql);
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstModel.size(); i++) {
            PackageFileAttachModel item = lstModel.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", item.getStr("uuid"));
            m.put("name", item.getStr("name"));
            m.put("size", item.getInt("size").toString());
            lst.add(m);
        }
        return lst;
    }

    public String addProducts(String strPackageID, String strPurchaseID, JSONObject obj) {
        String sql = "select id from purchase p where p.purchase_uuid='" + strPurchaseID + "'";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
        if (purchaseModel != null) {
            sql = "select id from package p where p.package_uuid='" + strPackageID + "'";

            int nPackageID = 0;
            PackageModel packageModel = PackageModel.dao.findFirst(sql);
            if (packageModel == null) {
                packageModel = new PackageModel();
                packageModel.set("package_uuid", strPackageID).set("purchase_id", purchaseModel.getInt("id"))
                        .set("package_activity_id", PackageActivityBean.INITIALIZE).save();
            }
            nPackageID = packageModel.get("id");

            int nTotal = obj.getInt("total");
            JSONArray arr = obj.getJSONArray("rows");
            for (int i = 0; i < nTotal; i++) {
                JSONObject item = (JSONObject) arr.get(i);
                ProductBean prjItem = new ProductBean();
                prjItem.strPrjName = item.getString("prj_name");
                prjItem.nPrjCount = item.getInt("prj_count");
                prjItem.fPrjPrice = item.getDouble("prj_price");
                prjItem.strProductID = item.getString("project_id");
                prjItem.strPrjSpec = item.getString("prj_spec");
                prjItem.fPrjPrePrice = item.getDouble("prj_pre_price");
                prjItem.strPrjParam = item.getString("prj_param");
                prjItem.strPrjType = item.getString("product_type");
                prjItem.nPackagedCount = item.getInt("prj_package_count");
                PackageProductModel.dao.addProduct(nPackageID, prjItem);
                ProductModel.dao.updatePackagedCount(prjItem.strProductID, prjItem.nPackagedCount);
            }
        }
        return ErrorCode.SUCCESS;
    }

    public void addAttachFile(String strPurchaseID, String strPackageID, String strFileType,
                              PackageAttachFileBean item) {
        String sql = "select id from purchase p where p.purchase_uuid='" + strPurchaseID + "'";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
        if (purchaseModel != null || strPurchaseID == "") {
            sql = "select id from package p where p.package_uuid='" + strPackageID + "'";

            int nPackageID = 0;
            PackageModel packageModel = PackageModel.dao.findFirst(sql);
            if (packageModel != null) {
                nPackageID = packageModel.get("id");
            } else {
                packageModel = new PackageModel();
                packageModel.set("purchase_id", purchaseModel.getInt("id")).set("package_uuid", strPackageID)
                        .set("package_activity_id", PackageActivityBean.INITIALIZE).save();
                nPackageID = packageModel.get("id");
            }
            PackageFileAttachModel.dao.addAttachFile(nPackageID, strFileType, item);
        }
    }
}
