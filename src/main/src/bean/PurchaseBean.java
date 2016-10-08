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
    private String strPurchaseID;                                                      // 采购函ID
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
    public void setPurchaseID(String strPurchaseID) {
        this.strPurchaseID = strPurchaseID;
    }

    public String getPurchaseID() {
        return strPurchaseID;
    }

    public void setPurCode(String strPurCode) {
        this.strPurCode = strPurCode;
    }

    public String getPurCode() {
        return strPurCode;
    }

    public void setFundsSrc(String strFundsSrc) {
        this.strFundsSrc = strFundsSrc;
    }

    public String getFundsSrc() {
        return strFundsSrc;
    }

    public void setContacts(String strContacts) {
        this.strContacts = strContacts;
    }

    public String getContacts() {
        return strContacts;
    }

    public void setPhoneNum(String strPhoneNum) {
        this.strPhoneNum = strPhoneNum;
    }

    public String getPhoneNum() {
        return strPhoneNum;
    }

    public void setFundsNature(String strFundsNature) {
        this.strFundsNature = strFundsNature;
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

    public ArrayList<Map<String, String>> getJSONAttachFiles() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstAttachFile.size(); i++) {
            PurchaseAttachFileBean item = lstAttachFile.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", item.strFileID);
            m.put("name", item.strFileName);
            m.put("size", String.valueOf(item.fileSize));
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getJSONOpinionItems() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstOpinion.size(); i++) {
            PurchaseOpinionBean item = lstOpinion.get(i);
            Map<String, String> m = new HashMap<String, String>();
            //m.put("op_department", item.strApproveDepartment);
            //m.put("op_approve_person", item.strApprovePerson);
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
}
