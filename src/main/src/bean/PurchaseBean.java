package bean;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchaseBean {
    /** 基础信息 */
    private String strPurchaseID;                                                     // 采购函ID
    private String strPurCode;                                                          // 采购编号
    private String strFundsSrc;                                                         // 资金来源
    private String strContacts;                                                         // 联系人
    private String strPhoneNum;                                                         // 联系电话
    private String strFundsNature;                                                      // 资金性质
    /** 购买项目详细内容 */
    private String strCommodityPrePrice;                                                // 商品类预算总金额
    private String strServicePrePrice;                                                  // 服务类预算总金额
    private String strEngineeringPrePrice;                                              // 工程类预算总金额
    private ArrayList<ProductBean> lstProduct = new ArrayList<ProductBean>();           // 采购项目
    /** 采购函附件 */
    private ArrayList<PurchaseAttachFileBean> lstAttachFile = new ArrayList<PurchaseAttachFileBean>();  // 附件
    /** 审批流状态及内容 */
    private int nComplaintsStatus;                                                      // 投诉处理状态
    private ArrayList<PurchaseOpinionBean> lstOpinion = new ArrayList<PurchaseOpinionBean>();           // 审批意见
    private ArrayList<ComplaintsBean> lstComplaints = new ArrayList<ComplaintsBean>();  // 投诉处理

    /** 定义 Model 接口 */
    public String getPurchaseID() {
        return strPurchaseID;
    }

    public String getPurCode() {
        return strPurCode;
    }

    public String getFundsSrc() {
        return strFundsSrc;
    }

    public String getContacts() {
        return strContacts;
    }

    public String getPhoneNum() {
        return strPhoneNum;
    }

    public String getFundsNature() {
        return strFundsNature;
    }

    public String getCommodityPrePrice() {
        return strCommodityPrePrice;
    }

    public int getComplaintsStatus() {
        return nComplaintsStatus;
    }

    public void setComplaintsStatus(int nStatus) {
        this.nComplaintsStatus = nStatus;
    }

    public String getServicePrePrice() {
        return strServicePrePrice;
    }

    public String getEngineeringPrePrice() {
        return strEngineeringPrePrice;
    }

    public ArrayList<ProductBean> getProductList() {
        return lstProduct;
    }

    public ArrayList<PurchaseAttachFileBean> getAttachFileList() {
        return lstAttachFile;
    }

    public ArrayList<PurchaseOpinionBean> getOpinionList() {
        return lstOpinion;
    }

    public ArrayList<ComplaintsBean> getComplaintsList() {
        return lstComplaints;
    }

    /** 定义 Controller 接口 */
    public String parseBaseData(JSONObject obj) {
        lstProduct.clear();
        strPurchaseID = obj.getString("purchase_id");
        strPurCode = obj.getString("pur_code");
        strFundsSrc = obj.getString("funds_src");
        strContacts = obj.getString("contacts");
        strPhoneNum = obj.getString("phone_num");
        strFundsNature = obj.getString("funds_nature");
        strCommodityPrePrice = obj.getString("commodity_pre_price");
        strServicePrePrice = obj.getString("service_pre_price");
        strEngineeringPrePrice = obj.getString("engineering_pre_price");

        return ErrorCode.SUCCESS;
    }

    public String parseProductData(JSONObject obj, String strProductType) {
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
            lstProduct.add(prjItem);
        }
        return ErrorCode.SUCCESS;
    }

    public void addAttachFile(PurchaseAttachFileBean item) {
        lstAttachFile.add(item);
    }

    public void delAttachFile(String strFileID) {
        for (int i=0; i<lstAttachFile.size(); i++) {
            if (lstAttachFile.get(i).strFileID.equals(strFileID)) {
                lstAttachFile.remove(i);
                break;
            }
        }
    }

    public PurchaseAttachFileBean getAttachFileItem(String strFileID) {
        for (int i=0; i<lstAttachFile.size(); i++) {
            if (lstAttachFile.get(i).strFileID.equals(strFileID)) {
                return lstAttachFile.get(i);
            }
        }
        return null;
    }

    public void addOpinion(PurchaseOpinionBean purchaseOpinionBean) {
        lstOpinion.add(purchaseOpinionBean);
    }

    public void addComplaints(ComplaintsBean complaintsBean) {
        lstComplaints.add(complaintsBean);
    }

    /** 定义 JSON 接口 */
    public Map<String, String> getJSONBaseData() {
        Map<String, String> obj = new HashMap<String, String>();
        obj.put("purchase_id", strPurchaseID);
        obj.put("pur_code", strPurCode);
        obj.put("funds_src", strFundsSrc);
        obj.put("contacts", strContacts);
        obj.put("phone_num", strPhoneNum);
        obj.put("funds_nature", strFundsNature);
        obj.put("commodity_pre_price", strCommodityPrePrice);
        obj.put("service_pre_price", strServicePrePrice);
        obj.put("engineering_pre_price", strEngineeringPrePrice);

        return obj;
    }

    public ArrayList<Map<String, String>> getJSONProductItems(String strProductType) {
        ArrayList<Map<String, String>> productArray = new ArrayList<Map<String, String>>();
        for (int i = 0; i < lstProduct.size(); i++) {
            ProductBean item = lstProduct.get(i);
            if (item.strPrjType.equals(strProductType)) {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("project_id", item.strProductID);
                obj.put("product_type", item.strPrjType);
                obj.put("prj_name", item.strPrjName);
                obj.put("prj_count", String.valueOf(item.nPrjCount));
                obj.put("prj_price", String.valueOf(item.fPrjPrice));
                obj.put("prj_pre_price", String.valueOf(item.fPrjPrePrice));
                obj.put("prj_param", item.strPrjParam);
                obj.put("prj_spec", item.strPrjSpec);
                productArray.add(obj);
            }
        }
        return productArray;
    }

    public ArrayList<Map<String, String>> getJSONAttachFiles() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstAttachFile.size(); i++) {
            PurchaseAttachFileBean item = lstAttachFile.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", item.strFileID);
            m.put("name", item.strFileName);
            m.put("size", item.strFileSize);
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getJSONOpinionItems() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstOpinion.size(); i++) {
            PurchaseOpinionBean item = lstOpinion.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("op_department", item.strApproveDepartment);
            m.put("op_approve_person", item.strApprovePerson);
            m.put("op_approve_date", item.strApproveDate);
            m.put("op_content", item.strOpinion);
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getJSONComplaintsItems() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstComplaints.size(); i++) {
            ComplaintsBean item = lstComplaints.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("comp_deal_date", item.strDealwithDate);
            m.put("comp_content", item.strDealwithOpinion);
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getJSONToDivideItems() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i = 0; i < lstProduct.size(); i++) {
            ProductBean item = lstProduct.get(i);
            if (item.nPackagedCount < item.nPrjCount) {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("project_id", item.strProductID);
                obj.put("product_type", item.strPrjType);
                obj.put("prj_name", item.strPrjName);
                obj.put("prj_count", String.valueOf(item.nPrjCount));
                obj.put("prj_price", String.valueOf(item.fPrjPrice));
                obj.put("prj_pre_price", String.valueOf(item.fPrjPrePrice));
                obj.put("prj_param", item.strPrjParam);
                obj.put("prj_spec", item.strPrjSpec);
                obj.put("prj_select", "selected");
                lst.add(obj);
            }
        }
        return lst;
    }
}
