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
 * Created by Joo on 2016/7/30.
 */
public class PurchaseModel extends Model<PurchaseModel> {
    public static final PurchaseModel dao = new PurchaseModel();

    private Map<String,ArrayList<PurchaseAttachFileBean>> mapAttachFile = new HashMap<String, ArrayList<PurchaseAttachFileBean>>();
    private ArrayList<PurchaseBean> lstPurchaseData = new ArrayList<PurchaseBean>();
    public String savePurchase(JSONObject obj) {
        String strPurchaseID = obj.getString("purchase_id");
        String strPurCode = obj.getString("pur_code");
        String strFundsSrc = obj.getString("funds_src");
        String strContacts = obj.getString("contacts");
        String strPhoneNum = obj.getString("phone_num");
        String strFundsNature = obj.getString("funds_nature");
        String strCommodityPrePrice = obj.getString("commodity_pre_price");
        String strServicePrePrice = obj.getString("service_pre_price");
        String strEngineeringPrePrice = obj.getString("engineering_pre_price");

        String url = "select id from purchase p where p.purchase_uuid=" + strPurchaseID;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        int nPurchaseID = 0;
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
            purchaseModel.set("purchase_uuid", strPurchaseID).set("code", strPurCode)
                    .set("funds_src", strFundsSrc).set("contacts", strContacts)
                    .set("phone_num", strPhoneNum).set("activity_id", 1)
                    .set("funds_nature_id", strFundsNature).update();
        } else {
            dao.set("purchase_uuid", strPurchaseID).set("code", strPurCode)
                    .set("funds_src", strFundsSrc).set("contacts", strContacts)
                    .set("phone_num", strPhoneNum).set("activity_id", 1)
                    .set("funds_nature_id", strFundsNature).save();
            nPurchaseID = dao.get("id");
        }

        return ErrorCode.SUCCESS;
    }

    public String submitPurchase(String strPurchaseID) {
        String url = "select id, purchase_activity_id from purchase p where p.purchase_uuid="
                + strPurchaseID;

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
            int nPurchaseActivityID =  purchaseModel.get("purchase_activity_id");
            purchaseModel.set("purchase_activity_id", nPurchaseActivityID + 1);
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPurchase(String strPurchaseID) {
        String url = "select id, purchase_activity_id from purchase p where p.purchase_uuid="
                + strPurchaseID;

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
            PurchaseFileAttachModel.dao.removePurchaseAttachFiles(nPurchaseID);
            ProductModel.dao.removePurchaseProducts(nPurchaseID);
            PurchaseModel.dao.deleteById(purchaseModel);
        }
        return ErrorCode.SUCCESS;
    }

    public String agreePurchase(String strPurchaseID, PurchaseOpinionBean purchaseOpinionBean) {
        String url = "select id, purchase_activity_id from purchase p where p.purchase_uuid="
                + strPurchaseID;

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
            int nPurchaseActivityID =  purchaseModel.get("purchase_activity_id");
            if (ApproveRecordModel.dao.addApproveRecord(nPurchaseID, nPurchaseActivityID + 1, purchaseOpinionBean)
                    == ErrorCode.SUCCESS) {
                purchaseModel.set("purchase_activity_id", nPurchaseActivityID + 1);
            }
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePurchase(String strPurchaseID, PurchaseOpinionBean purchaseOpinionBean) {
        String url = "select id from purchase p where p.purchase_uuid=" + strPurchaseID;

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
            purchaseModel.set("purchase_activity_id", 1);
        }
        return ErrorCode.SUCCESS;
    }

    public int getActivityStatus(String strPurchaseID) {
        String url = "select purchase_activity_id from purchase p where p.purchase_uuid=" + strPurchaseID;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            return purchaseModel.get("purchase_activity_id");
        }
        return PurchaseActivityBean.INITIALIZE;
    }

    public ArrayList<PurchaseBean> getPurchaseList() {
        ArrayList<PurchaseBean> lstPurchaseBean = new ArrayList<PurchaseBean>();
        String sql = "select p.purchase_uuid, p.code, p.name, p.funds_src, p.funds_nature_id, p.contacts, " +
                "p.phone_num, a.status from purchase p left join activity a on p.activity_id=a.id";
        List<PurchaseModel> purchaseList = dao.find(sql);
        for (int i=0; i<purchaseList.size(); i++) {
            PurchaseModel item = purchaseList.get(i);
            PurchaseBean purchaseBean = new PurchaseBean();
            purchaseBean.setPurchaseID(item.getStr("purchase_uuid"));
            purchaseBean.setPurCode(item.getStr("code"));
            purchaseBean.setFundsSrc(item.getStr("funds_src"));
            purchaseBean.setFundsNature(String.valueOf(item.getInt("funds_nature_id")));
            purchaseBean.setContacts(item.getStr("contacts"));
            purchaseBean.setPhoneNum(item.getStr("phone_num"));
            //purchaseBean.setS
            lstPurchaseBean.add(purchaseBean);
        }
        return lstPurchaseBean;
    }

    public PurchaseBean getPurchase(String strPurchaseID) {
        String sql = "select p.code, p.name, p.funds_src, p.funds_nature_id, p.contacts, " +
                "p.phone_num, a.status from purchase p left join activity a on p.activity_id=a.id " +
                "where p.purchase_uuid=" + strPurchaseID;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
        PurchaseBean purchaseBean = null;
        if (purchaseModel != null) {
            purchaseBean = new PurchaseBean();
            purchaseBean.setPurchaseID(purchaseModel.getStr("purchase_uuid"));
            purchaseBean.setPurCode(purchaseModel.getStr("code"));
            purchaseBean.setFundsSrc(purchaseModel.getStr("funds_src"));
            purchaseBean.setFundsNature(String.valueOf(purchaseModel.getInt("funds_nature_id")));
            purchaseBean.setContacts(purchaseModel.getStr("contacts"));
            purchaseBean.setPhoneNum(purchaseModel.getStr("phone_num"));
        }

        return purchaseBean;
    }

    public String addProducts(String strPurchaseID, JSONObject obj, String strProductType) {
        String url = "select id from purchase p where p.purchase_uuid=" + strPurchaseID;

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
        } else {
            dao.set("purchase_uuid", strPurchaseID).save();
            nPurchaseID = dao.get("id");
        }

        int nTotal = obj.getInt("total");
        JSONArray arr = obj.getJSONArray("rows");
        for (int i=0; i<nTotal; i++) {
            JSONObject item = (JSONObject)arr.get(i);
            ProductBean prjItem = new ProductBean();
            prjItem.strProductID = item.getString("project_id");
            prjItem.strPrjName = item.getString("prj_name");
            prjItem.nPrjCount = item.getInt("prj_count");
            prjItem.fPrjPrice = item.getDouble("prj_price");
            prjItem.strPrjSpec = item.getString("prj_spec");
            prjItem.fPrjPrePrice = item.getDouble("prj_pre_price");
            prjItem.strPrjParam = item.getString("prj_param");
            prjItem.strPrjType = strProductType;
            prjItem.nPackagedCount = 0;
            ProductModel.dao.addProduct(nPurchaseID, prjItem);
        }
        return ErrorCode.SUCCESS;
    }

    public void addAttachFile(String strPurchaseID, PurchaseAttachFileBean item) {
        String url = "select id from purchase p where p.purchase_uuid=" + strPurchaseID;

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
        } else {
            dao.set("purchase_uuid", strPurchaseID).save();
            nPurchaseID = dao.get("id");
        }
        PurchaseFileAttachModel.dao.addAttachFile(nPurchaseID, item);
    }
}
