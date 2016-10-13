package model;

import bean.*;
import com.jfinal.plugin.activerecord.Model;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import javax.jws.soap.SOAPBinding;
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
    public String savePurchase(JSONObject obj, String strUsername) {
        String strPurchaseID = obj.getString("purchase_id");
        String strPurName = obj.getString("pur_name");
        String strPurCode = obj.getString("pur_code");
        String strFundsSrc = obj.getString("funds_src");
        String strContacts = obj.getString("contacts");
        String strPhoneNum = obj.getString("phone_num");
        String strFundsNature = obj.getString("funds_nature");
        int nPurchaseType = obj.getInt("purchase_type");
        Double fCommodityPrePrice = obj.getDouble("commodity_pre_price");
        Double fServicePrePrice = obj.getDouble("service_pre_price");
        Double fEngineeringPrePrice = obj.getDouble("engineering_pre_price");
        String sql = "select u.dept_id,r.id from user u left join role r on r.user_id=u.id where " +
                "u.username='" + strUsername + "'";
        UserModel userModel = UserModel.dao.findFirst(sql);
        if (userModel != null) {
            sql = "select id from purchase p where p.purchase_uuid='" + strPurchaseID + "'";
            PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
            if (purchaseModel != null) {
                purchaseModel.set("purchase_uuid", strPurchaseID).set("code", strPurCode).set("name", strPurName)
                        .set("funds_src", strFundsSrc).set("contacts", strContacts)
                        .set("phone_num", strPhoneNum).set("engineering_pre_price", fEngineeringPrePrice)
                        .set("purchase_activity_id", PurchaseActivityBean.INITIALIZE)
                        .set("funds_nature_id", strFundsNature).set("commodity_pre_price", fCommodityPrePrice)
                        .set("service_pre_price", fServicePrePrice).set("dept_id", userModel.getInt("dept_id"))
                        .set("role_id", userModel.getInt("id")).set("purchase_type_id", nPurchaseType).update();
            } else {
                purchaseModel = new PurchaseModel();
                purchaseModel.set("purchase_uuid", strPurchaseID).set("code", strPurCode)
                        .set("name", strPurName).set("funds_src", strFundsSrc).set("contacts", strContacts)
                        .set("phone_num", strPhoneNum).set("engineering_pre_price", fEngineeringPrePrice)
                        .set("purchase_activity_id", PurchaseActivityBean.INITIALIZE)
                        .set("funds_nature_id", strFundsNature).set("commodity_pre_price", fCommodityPrePrice)
                        .set("service_pre_price", fServicePrePrice).set("dept_id", userModel.getInt("dept_id"))
                        .set("role_id", userModel.getInt("id")).set("purchase_type_id", nPurchaseType).save();
            }
        }
        return ErrorCode.SUCCESS;
    }

    public String submitPurchase(String strPurchaseID, int nPurchaseType) {
        String url = "select p.id,p.purchase_activity_id,p.role_id from purchase p " +
                "where p.purchase_uuid='" + strPurchaseID + "'";

        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            int nPurchaseActivityID =  purchaseModel.get("purchase_activity_id");
            int nRoleId = purchaseModel.get("role_id");
            purchaseModel.set("purchase_activity_id", nPurchaseActivityID + 1)
                    .set("role_id", nRoleId + 1).set("purchase_type_id", nPurchaseType).update();
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPurchase(String strPurchaseID) {
        String url = "select id, purchase_activity_id from purchase p where p.purchase_uuid='"
                + strPurchaseID + "'";

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
        String sql = "select u.dept_id,r.id,r.permission_id from user u left join role r on " +
                "r.user_id=u.id where u.id=" + purchaseOpinionBean.userID;
        UserModel userModel = UserModel.dao.findFirst(sql);
        if (userModel != null) {
            sql = "select p.* from purchase p where p.purchase_uuid='" + strPurchaseID + "'";
            PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
            if (purchaseModel != null) {
                int nPurchaseID = purchaseModel.get("id");
                int nPurchaseActivityID =  purchaseModel.get("purchase_activity_id");
                int nPurchaseTypeID = purchaseModel.get("purchase_type_id");
                int lastApproveRoleId = purchaseModel.get("role_id");
                if (purchaseOpinionBean.nextApproveRoleId == 0) {
                    if (lastApproveRoleId >= 3 && lastApproveRoleId <= 6) {
                        purchaseOpinionBean.nextApproveRoleId = 7;
                    } else if (lastApproveRoleId >= 8 && lastApproveRoleId <= 12) {
                        purchaseOpinionBean.nextApproveRoleId = 13;
                    } else {
                        purchaseOpinionBean.nextApproveRoleId = lastApproveRoleId + 1;
                    }
                }

                if (lastApproveRoleId >= 3 && lastApproveRoleId <= 6 && nPurchaseTypeID == 1) {
                    nPurchaseActivityID += 2;
                } else {
                    nPurchaseActivityID += 1;
                }

                if (userModel.getInt("permission_id") == purchaseModel.get("purchase_activity_id")) {
                    if (ApproveRecordModel.dao.addApproveRecord(nPurchaseID, nPurchaseActivityID,
                            purchaseOpinionBean) == ErrorCode.SUCCESS) {
                        if (purchaseOpinionBean.nextApproveRoleId == 0) {
                            purchaseModel.set("purchase_activity_id", nPurchaseActivityID)
                                    .set("role_id", purchaseOpinionBean.nextApproveRoleId).update();
                        } else {
                            purchaseModel.set("purchase_activity_id", nPurchaseActivityID)
                                    .set("role_id", purchaseOpinionBean.nextApproveRoleId)
                                    .set("purchase_nature_id", purchaseOpinionBean.purchaseNatureId).update();
                        }
                    }
                }
            }
        }

        return ErrorCode.SUCCESS;
    }

    public String disagreePurchase(String strPurchaseID, PurchaseOpinionBean purchaseOpinionBean) {
        String url = "select id from purchase p where p.purchase_uuid='" + strPurchaseID + "'";

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            nPurchaseID = purchaseModel.get("id");
            purchaseModel.set("purchase_activity_id", PurchaseActivityBean.INITIALIZE)
                    .set("role_id", RoleBean.APPLICANT).update();
        }
        return ErrorCode.SUCCESS;
    }

    public int getActivityStatus(String strPurchaseID) {
        String url = "select purchase_activity_id from purchase p where p.purchase_uuid='"
                + strPurchaseID + "'";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            return purchaseModel.get("purchase_activity_id");
        }
        return PurchaseActivityBean.INITIALIZE;
    }

    public ArrayList<PurchaseBean> getPurchaseList(String sql) {
        ArrayList<PurchaseBean> lstPurchaseBean = new ArrayList<PurchaseBean>();
        List<PurchaseModel> purchaseList = dao.find(sql);
        for (int i=0; i<purchaseList.size(); i++) {
            PurchaseModel item = purchaseList.get(i);
            PurchaseBean purchaseBean = new PurchaseBean();
            purchaseBean.setPurchaseID(item.getStr("purchase_uuid"));
            purchaseBean.setPurName(item.getStr("name"));
            purchaseBean.setPurCode(item.getStr("code"));
            purchaseBean.setFundsSrc(item.getStr("funds_src"));
            purchaseBean.setFundsNature(String.valueOf(item.getInt("funds_nature_id")));
            purchaseBean.setContacts(item.getStr("contacts"));
            purchaseBean.setPhoneNum(item.getStr("phone_num"));
            purchaseBean.setPurchaseType(String.valueOf(item.getInt("purchase_type_id")));
            lstPurchaseBean.add(purchaseBean);
        }
        return lstPurchaseBean;
    }

    public Map<String, String> getBaseData(String strPurchaseID) {
        String sql = "select p.*, a.status from purchase p left join purchase_activity a on " +
                "p.purchase_activity_id=a.id where p.purchase_uuid='" + strPurchaseID + "'";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
        Map<String, String> obj = new HashMap<String, String>();
        if (purchaseModel != null) {
            obj.put("purchase_id", purchaseModel.getStr("purchase_uuid"));
            obj.put("pur_name", purchaseModel.getStr("name"));
            obj.put("pur_code", purchaseModel.getStr("code"));
            obj.put("funds_src", purchaseModel.getStr("funds_src"));
            obj.put("contacts", purchaseModel.getStr("contacts"));
            obj.put("phone_num", purchaseModel.getStr("phone_num"));
            obj.put("funds_nature", purchaseModel.getInt("funds_nature_id").toString());
            obj.put("commodity_pre_price", purchaseModel.getDouble("commodity_pre_price").toString());
            obj.put("service_pre_price", purchaseModel.getDouble("service_pre_price").toString());
            obj.put("engineering_pre_price", purchaseModel.getDouble("engineering_pre_price").toString());
        }

        return obj;
    }

    public ArrayList<Map<String, String>> getPreviewBaseData(String strPurchaseID) {
        String sql = "select p.*, pn.name as nature_name from purchase p left join purchase_nature pn on " +
                "p.purchase_nature_id=pn.id where p.purchase_uuid='" + strPurchaseID + "'";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(sql);
        ArrayList<Map<String, String>> previewArray = new ArrayList<Map<String, String>>();
        if (purchaseModel != null) {
            Map<String, String> objName = new HashMap<String, String>();
            objName.put("name", "采购项目名称");
            objName.put("value", purchaseModel.getStr("name"));
            objName.put("group",  "采购信息");
            previewArray.add(objName);

            Map<String, String> objCode = new HashMap<String, String>();
            objCode.put("name", "采购函编号");
            objCode.put("value", purchaseModel.getStr("code"));
            objCode.put("group",  "采购信息");
            previewArray.add(objCode);

            Map<String, String> objFundsSrc = new HashMap<String, String>();
            objFundsSrc.put("name", "资金来源");
            objFundsSrc.put("value",  purchaseModel.getStr("funds_src"));
            objFundsSrc.put("group",  "采购信息");
            previewArray.add(objFundsSrc);

            Map<String, String> objFundsNature = new HashMap<String, String>();
            objFundsNature.put("name", "资金性质");
            objFundsNature.put("value",  purchaseModel.getStr("nature_name"));
            objFundsNature.put("group",  "采购信息");
            previewArray.add(objFundsNature);

            Map<String, String> objContacts = new HashMap<String, String>();
            objContacts.put("name", "联系人");
            objContacts.put("value",  purchaseModel.getStr("contacts"));
            objContacts.put("group",  "联系方式");
            previewArray.add(objContacts);

            Map<String, String> objPhoneNum = new HashMap<String, String>();
            objPhoneNum.put("name", "联系电话");
            objPhoneNum.put("value",  purchaseModel.getStr("phone_num"));
            objPhoneNum.put("group",  "联系方式");
            previewArray.add(objPhoneNum);

            Map<String, String> objCommodityPrePrice = new HashMap<String, String>();
            objCommodityPrePrice.put("name", "产品类预算总价");
            objCommodityPrePrice.put("value",  purchaseModel.getDouble("commodity_pre_price").toString());
            objCommodityPrePrice.put("group",  "预算");
            previewArray.add(objCommodityPrePrice);

            Map<String, String> objServicePrePrice = new HashMap<String, String>();
            objServicePrePrice.put("name", "服务类预算总价");
            objServicePrePrice.put("value",  purchaseModel.getDouble("service_pre_price").toString());
            objServicePrePrice.put("group",  "预算");
            previewArray.add(objServicePrePrice);

            Map<String, String> objEngineeringPrePrice = new HashMap<String, String>();
            objEngineeringPrePrice.put("name", "工程类预算总价");
            objEngineeringPrePrice.put("value",  purchaseModel.getDouble("engineering_pre_price").toString());
            objEngineeringPrePrice.put("group",  "预算");
            previewArray.add(objEngineeringPrePrice);
        }

        return previewArray;
    }

    public ArrayList<Map<String, String>> getAttachFiles(String strPurchaseID) {
        String sql = "select paf.* from purchase_attach_file paf left join purchase p on " +
                "paf.purchase_id=p.id where p.purchase_uuid='" + strPurchaseID + "'";

        List<PurchaseFileAttachModel> lstModel = PurchaseFileAttachModel.dao.find(sql);
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstModel.size(); i++) {
            PurchaseFileAttachModel item = lstModel.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", item.getStr("uuid"));
            m.put("name", item.getStr("name"));
            m.put("size", item.getInt("size").toString());
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getOpinionItems(String strPurchaseID) {
        String sql = "select ar.* from approve_record ar left join purchase p on " +
                "ar.purchase_id=p.id where p.purchase_uuid='" + strPurchaseID + "'";
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();

        List<ApproveRecordModel> lstModel = ApproveRecordModel.dao.find(sql);
        for (int i = 0; i < lstModel.size(); i++) {
            ApproveRecordModel item = lstModel.get(i);
            sql = "select u.real_name,d.dept_name from user u left join dept d on u.dept_id=d.id " +
                    "where u.id=" + item.getInt("user_id");
            UserModel userModel = UserModel.dao.findFirst(sql);
            if (userModel != null) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("op_department", userModel.getStr("dept_name"));
                m.put("op_approve_person", userModel.getStr("real_name"));
                m.put("op_approve_date", item.getStr("approve_date"));
                m.put("op_content", item.getStr("opinion"));
                lst.add(m);
            }
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getComplaintsItems(String strPurchaseID) {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        /*for (int i=0; i<lstComplaints.size(); i++) {
            ComplaintsBean item = lstComplaints.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("comp_deal_date", item.strDealwithDate);
            m.put("comp_content", item.strDealwithOpinion);
            lst.add(m);
        }*/
        return lst;
    }

    public String addProducts(String strPurchaseID, JSONObject obj, String strProductType) {
        String url = "select id from purchase p where p.purchase_uuid='" + strPurchaseID + "'";

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel == null) {
            purchaseModel = new PurchaseModel();
            purchaseModel.set("purchase_uuid", strPurchaseID).save();
        }
        nPurchaseID = purchaseModel.get("id");

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
        String url = "select id from purchase p where p.purchase_uuid='" + strPurchaseID + "'";

        int nPurchaseID = 0;
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel == null) {
            purchaseModel = new PurchaseModel();
            purchaseModel.set("purchase_uuid", strPurchaseID).save();
            nPurchaseID = purchaseModel.get("id");
        }
        nPurchaseID = purchaseModel.get("id");
        PurchaseFileAttachModel.dao.addAttachFile(nPurchaseID, item);
    }
}
