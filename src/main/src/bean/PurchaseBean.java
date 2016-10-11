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
    private String strPurName;                                                          // 采购名称
    private String strPurCode;                                                          // 采购编号
    private String strFundsSrc;                                                         // 资金来源
    private String strContacts;                                                         // 联系人
    private String strPhoneNum;                                                         // 联系电话
    private String strFundsNature;                                                      // 资金性质
    private String strPurchaseType;                                                     // 采购类型
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

    public void setPurName(String strPurName) {
        this.strPurName = strPurName;
    }

    public String getPurName() {
        return strPurName;
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

    public String getPurchaseType() {
        return strPurchaseType;
    }

    public void setPurchaseType(String strPurchaseType) {
        this.strPurchaseType = strPurchaseType;
    }

}
