package bean;

import model.PurchasingModel;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchasingBean {
    /** 定义错误码 */
    public static final String SUCCESS                  = "success";
    /** 定义项目类别 */
    public static final String COMMODITY                = "commodity";
    public static final String SERVICE                  = "service";
    public static final String ENGINEERING              = "engineering";
    /** 定义采购工作流状态 */
    public static final int INITIALIZE                  = 0;          // 新创建项目
    public static final int ACC_APPROVE                 = 1;          // 单位会计审批
    public static final int DIR_APPROVE                 = 2;          // 分管股室局长审批
    public static final int FINANCIAL_APPROVE           = 3;          // 财政监管股室股审批
    public static final int FIN_BUREAU_APPROVE          = 4;          // 财政局审批
    public static final int SUBCONTRACTING              = 5;          // 未分包
    public static final int SUBCONTRACTED               = 6;          // 已分包
    public static final int COMPLETED                   = 7;          // 已完成
    public static final int ACC_APPROVE_FAILED          = 101;        // 单位会计审批失败
    public static final int DIR_APPROVE_FAILED          = 102;        // 分管股室局长审批失败
    public static final int FINANCIAL_APPROVE_FAILED    = 103;        // 财政监管股室股审批失败
    public static final int FIN_BUREAU_APPROVE_FAILED   = 104;        // 财政局审批失败

    /** 定义申请者目录结构 */
    public static final String CREATE                   = "create";                             // 新建采购函
    public static final String SUBMITTED                = "submitted";                          // 已提交
    public static final String EXECUTED                 = "executed";                           // 已执行
    public static final String IMPLEMENTED              = "implemented";                        // 已完成
    /** 定义审批者目录结构 */
    public static final String TO_APPROVE               = "to_approve";                         // 待审批
    public static final String APPROVED                 = "approved";                           // 已审批
    public static final String REJECTED                 = "rejected";                           // 审批未通过
    /** 定义采购执行者目录结构 */
    public static final String TO_DIVIDE                = "to_divide";                          // 未分包
    public static final String DIVIDED                  = "divided";                            // 已分包
    public static final String FINISHED                 = "finished";                           // 已结束
    public static final String INTERRUPT                = "interrupt";                          // 被投诉
    public static final String FAILED                   = "failed";                             // 流标
    /** 基础信息 */
    private String strPurchasingID;                                                     // 采购函ID
    private String strPurCode;                                                          // 采购编号
    private String strFundsSrc;                                                         // 资金来源
    private String strContacts;                                                         // 联系人
    private String strPhoneNum;                                                         // 联系电话
    private String strFundsNature;                                                      // 资金性质
    /** 购买项目详细内容 */
    private String strCommodityPrePrice;                                                // 商品类预算总金额
    private String strServicePrePrice;                                                  // 服务类预算总金额
    private String strEngineeringPrePrice;                                              // 工程类预算总金额
    private ArrayList<ProductBean> lstCommodity = new ArrayList<ProductBean>();                   // 商品类
    private ArrayList<ProductBean> lstService = new ArrayList<ProductBean>();                     // 服务类
    private ArrayList<ProductBean> lstEngineering = new ArrayList<ProductBean>();                 // 工程类
    /** 采购函附件 */
    private ArrayList<AttachFileBean> lstAttachFile = new ArrayList<AttachFileBean>();            // 附件
    /** 审批流状态及内容 */
    private int nApproveStatus;                                                         // 采购函状态
    private int nComplaintsStatus;                                                      // 投诉处理状态
    private ArrayList<OpinionBean> lstOpinion = new ArrayList<OpinionBean>();           // 审批意见
    private ArrayList<ComplaintsBean> lstComplaints = new ArrayList<ComplaintsBean>();  // 投诉处理
    /** 分包 */
    private ArrayList<PacketBean> lstPacket = new ArrayList<PacketBean>();              // 分包信息
    /** 定义静态函数 */
    static public JSONArray getApplicantTree() {
        ArrayList<PurchasingBean> lstPurchasing = PurchasingModel.dao.getPurchasingList();

        JSONArray newPrjChildren = new JSONArray();
        JSONArray committedPrjChildren = new JSONArray();
        JSONArray executedPrjChildren = new JSONArray();
        JSONArray implementedPrjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchasingID());
            childrenNode.put("text", lstPurchasing.get(i).getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            switch (lstPurchasing.get(i).getApproveStatus()) {
                case PurchasingBean.INITIALIZE:
                    childrenNode.put("type", PurchasingBean.CREATE);
                    newPrjChildren.put(childrenNode);
                    break;
                case PurchasingBean.ACC_APPROVE:
                case PurchasingBean.ACC_APPROVE_FAILED:
                case PurchasingBean.DIR_APPROVE:
                case PurchasingBean.FINANCIAL_APPROVE:
                case PurchasingBean.FIN_BUREAU_APPROVE:
                case PurchasingBean.SUBCONTRACTING:
                    childrenNode.put("type", PurchasingBean.SUBMITTED);
                    committedPrjChildren.put(childrenNode);
                    break;
            }
        }

        JSONObject newPrj = new JSONObject();
        newPrj.put("id", "new_prj");
        newPrj.put("text", "新建采购过程");
        newPrj.put("iconCls", "icon-cut");
        newPrj.put("children", newPrjChildren);

        JSONObject committedPrj = new JSONObject();
        committedPrj.put("id", "committed_prj");
        committedPrj.put("text", "已提交采购过程");
        committedPrj.put("iconCls", "icon-cut");
        committedPrj.put("children", committedPrjChildren);

        JSONObject executedPrj = new JSONObject();
        executedPrj.put("id", "executed_prj");
        executedPrj.put("text", "已执行采购过程");
        executedPrj.put("iconCls", "icon-cut");
        executedPrj.put("children", executedPrjChildren);

        JSONObject implementedPrj = new JSONObject();
        implementedPrj.put("id", "implemented_prj");
        implementedPrj.put("text", "已完成采购过程");
        implementedPrj.put("iconCls", "icon-cut");
        implementedPrj.put("children", implementedPrjChildren);

        JSONArray lstChildren = new JSONArray();
        lstChildren.put(newPrj);
        lstChildren.put(committedPrj);
        lstChildren.put(executedPrj);
        lstChildren.put(implementedPrj);

        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "root");
        rootNode.put("text", "采购信息管理中心");
        rootNode.put("iconCls", "icon-cut");
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }

    static private String getNodeType(String strUserRole, int nStatus) {
        String strNodeType = "";
        if (strUserRole.equals(RoleBean.ACCOUNTING)){
            switch (nStatus) {
                case PurchasingBean.ACC_APPROVE:
                    strNodeType = PurchasingBean.TO_APPROVE;
                    break;
                case PurchasingBean.DIR_APPROVE:
                case PurchasingBean.FINANCIAL_APPROVE:
                case PurchasingBean.FIN_BUREAU_APPROVE:
                case PurchasingBean.SUBCONTRACTING:
                    strNodeType = PurchasingBean.APPROVED;
                    break;
                case PurchasingBean.DIR_APPROVE_FAILED:
                    strNodeType = PurchasingBean.REJECTED;
                    break;
            }
        } else if (strUserRole.equals(RoleBean.DIRECTOR)) {
            switch (nStatus) {
                case PurchasingBean.DIR_APPROVE:
                    strNodeType = PurchasingBean.TO_APPROVE;
                    break;
                case PurchasingBean.FINANCIAL_APPROVE:
                case PurchasingBean.FIN_BUREAU_APPROVE:
                case PurchasingBean.SUBCONTRACTING:
                    strNodeType = PurchasingBean.APPROVED;
                    break;
                case PurchasingBean.FINANCIAL_APPROVE_FAILED:
                    strNodeType = PurchasingBean.REJECTED;
                    break;
            }
        } else if (strUserRole.equals(RoleBean.REGULATORY)) {
            switch (nStatus) {
                case PurchasingBean.FINANCIAL_APPROVE:
                    strNodeType = PurchasingBean.TO_APPROVE;
                    break;
                case PurchasingBean.FIN_BUREAU_APPROVE:
                case PurchasingBean.SUBCONTRACTING:
                    strNodeType = PurchasingBean.APPROVED;
                    break;
                case PurchasingBean.FIN_BUREAU_APPROVE_FAILED:
                    strNodeType = PurchasingBean.REJECTED;
                    break;
            }
        } else if (strUserRole.equals(RoleBean.BUREAU)) {
            switch (nStatus) {
                case PurchasingBean.FIN_BUREAU_APPROVE:
                    strNodeType = PurchasingBean.TO_APPROVE;
                    break;
                case PurchasingBean.SUBCONTRACTING:
                    strNodeType = PurchasingBean.APPROVED;
                    break;
            }
        }
        return strNodeType;
    }

    static public JSONArray getApprovalTree(String strUserRole) {
        ArrayList<PurchasingBean> lstPurchasing = PurchasingModel.dao.getPurchasingList();

        JSONArray toApprovePrjChildren = new JSONArray();
        JSONArray approvedPrjChildren = new JSONArray();
        JSONArray rejectedPrjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchasingID());
            childrenNode.put("text", lstPurchasing.get(i).getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            childrenNode.put("role", strUserRole);
            String strNodeType = getNodeType(strUserRole, lstPurchasing.get(i).getApproveStatus());
            childrenNode.put("type", strNodeType);
            if (strNodeType.equals(PurchasingBean.TO_APPROVE)) {
                toApprovePrjChildren.put(childrenNode);
            } else if (strNodeType.equals(PurchasingBean.APPROVED)) {
                approvedPrjChildren.put(childrenNode);
            } else if (strNodeType.equals(PurchasingBean.REJECTED)) {
                rejectedPrjChildren.put(childrenNode);
            }
        }

        JSONObject toApprovePrj = new JSONObject();
        toApprovePrj.put("id", "to_approve_prj");
        toApprovePrj.put("text", "待审批项目");
        toApprovePrj.put("iconCls", "icon-cut");
        toApprovePrj.put("children", toApprovePrjChildren);

        JSONObject approvedPrj = new JSONObject();
        approvedPrj.put("id", "approved_prj");
        approvedPrj.put("text", "已审批项目");
        approvedPrj.put("iconCls", "icon-cut");
        approvedPrj.put("children", approvedPrjChildren);

        JSONObject rejectPrj = new JSONObject();
        rejectPrj.put("id", "executed_prj");
        rejectPrj.put("text", "审批未通过项目");
        rejectPrj.put("iconCls", "icon-cut");
        rejectPrj.put("children", rejectedPrjChildren);

        JSONArray lstChildren = new JSONArray();
        lstChildren.put(toApprovePrj);
        lstChildren.put(approvedPrj);
        lstChildren.put(rejectPrj);

        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "root");
        rootNode.put("text", "项目审批管理中心");
        rootNode.put("iconCls", "icon-cut");
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }

    static public JSONArray getPurchaseTree() {
        ArrayList<PurchasingBean> lstPurchasing = PurchasingModel.dao.getPurchasingList();

        JSONArray subcontractingChildren = new JSONArray();
        JSONArray subcontractedChildren = new JSONArray();
        JSONArray completedChildren = new JSONArray();
        JSONArray complaintsChildren = new JSONArray();
        JSONArray failedChildren = new JSONArray();
        for (int i=0; i<lstPurchasing.size(); i++) {
            PurchasingBean purchasingBean = lstPurchasing.get(i);
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchasingID());
            childrenNode.put("text", lstPurchasing.get(i).getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            switch (purchasingBean.getComplaintsStatus()) {
                case 1:// 投诉
                    childrenNode.put("type", PurchasingBean.INTERRUPT);
                    complaintsChildren.put(childrenNode);
                    break;
                case 2:// 流标
                    childrenNode.put("type", PurchasingBean.FAILED);
                    failedChildren.put(childrenNode);
                    break;
                default:
                    switch (purchasingBean.getApproveStatus()) {
                        case PurchasingBean.SUBCONTRACTING:
                            childrenNode.put("type", PurchasingBean.TO_DIVIDE);
                            subcontractingChildren.put(childrenNode);
                            break;
                        case PurchasingBean.SUBCONTRACTED:
                            childrenNode.put("type", PurchasingBean.DIVIDED);
                            subcontractedChildren.put(childrenNode);
                            break;
                        case PurchasingBean.COMPLETED:
                            childrenNode.put("type", PurchasingBean.FINISHED);
                            completedChildren.put(childrenNode);
                            break;
                    }
                    break;
            }
        }

        JSONObject toDividePrj = new JSONObject();
        toDividePrj.put("id", "to_divide_prj");
        toDividePrj.put("text", "未分包项目");
        toDividePrj.put("iconCls", "icon-cut");
        toDividePrj.put("children", subcontractingChildren);

        JSONObject dividedPrj = new JSONObject();
        dividedPrj.put("id", "divided_prj");
        dividedPrj.put("text", "已分包项目");
        dividedPrj.put("iconCls", "icon-cut");
        dividedPrj.put("children", subcontractedChildren);

        JSONObject finishPrj = new JSONObject();
        finishPrj.put("id", "finish_prj");
        finishPrj.put("text", "已结束项目");
        finishPrj.put("iconCls", "icon-cut");
        finishPrj.put("children", completedChildren);

        JSONObject interruptPrj = new JSONObject();
        interruptPrj.put("id", "interrupt_prj");
        interruptPrj.put("text", "投诉项目");
        interruptPrj.put("iconCls", "icon-cut");
        interruptPrj.put("children", complaintsChildren);

        JSONObject failedPrj = new JSONObject();
        failedPrj.put("id", "failed_prj");
        failedPrj.put("text", "流标项目");
        failedPrj.put("iconCls", "icon-cut");
        failedPrj.put("children", failedChildren);

        JSONArray lstChildren = new JSONArray();
        lstChildren.put(toDividePrj);
        lstChildren.put(dividedPrj);
        lstChildren.put(finishPrj);
        lstChildren.put(interruptPrj);
        lstChildren.put(failedPrj);

        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "root");
        rootNode.put("text", "采购执行中心");
        rootNode.put("iconCls", "icon-cut");
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }

    /** 定义 Model 接口 */
    public String getPurchasingID() {
        return strPurchasingID;
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

    public int getApproveStatus() {
        return nApproveStatus;
    }

    public void setApproveStatus(int nStatus) {
        this.nApproveStatus = nStatus;
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

    public ArrayList<ProductBean> getCommodityList() {
        return lstCommodity;
    }

    public ArrayList<ProductBean> getServiceList() {
        return lstService;
    }

    public ArrayList<ProductBean> getEngineeringList() {
        return lstEngineering;
    }

    public ArrayList<AttachFileBean> getAttachFileList() {
        return lstAttachFile;
    }

    public ArrayList<OpinionBean> getOpinionList() {
        return lstOpinion;
    }

    public ArrayList<ComplaintsBean> getComplaintsList() {
        return lstComplaints;
    }

    /** 定义 Controller 接口 */
    public String parseBaseData(JSONObject obj) {
        lstCommodity.clear();
        lstService.clear();
        lstEngineering.clear();

        strPurchasingID = obj.getString("purchasing_id");
        strPurCode = obj.getString("pur_code");
        strFundsSrc = obj.getString("funds_src");
        strContacts = obj.getString("contacts");
        strPhoneNum = obj.getString("phone_num");
        strFundsNature = obj.getString("funds_nature");
        strCommodityPrePrice = obj.getString("commodity_pre_price");
        strServicePrePrice = obj.getString("service_pre_price");
        strEngineeringPrePrice = obj.getString("engineering_pre_price");

        return SUCCESS;
    }

    public String parseProductData(JSONObject obj, String strProductType) {
        int nTotal = obj.getInt("total");
        JSONArray arr = obj.getJSONArray("rows");
        for (int i=0; i<nTotal; i++) {
            JSONObject item = (JSONObject)arr.get(i);
            ProductBean prjItem = new ProductBean();
            prjItem.strPrjName = item.getString("prj_name");
            prjItem.nPrjCount = item.getInt("prj_count");
            prjItem.fPrjPrice = item.getDouble("prj_price");
            prjItem.strPrjSpec = item.getString("prj_spec");
            prjItem.fPrjPrePrice = item.getDouble("prj_pre_price");
            prjItem.strPrjParam = item.getString("prj_param");

            if (strProductType == COMMODITY) {
                lstCommodity.add(prjItem);
            } else if (strProductType == SERVICE) {
                lstService.add(prjItem);
            } else {
                lstEngineering.add(prjItem);
            }
        }
        return SUCCESS;
    }

    public void addAttachFile(AttachFileBean item) {
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

    public AttachFileBean getAttachFileItem(String strFileID) {
        for (int i=0; i<lstAttachFile.size(); i++) {
            if (lstAttachFile.get(i).strFileID.equals(strFileID)) {
                return lstAttachFile.get(i);
            }
        }
        return null;
    }

    public void addOpinion(OpinionBean opinionBean) {
        lstOpinion.add(opinionBean);
    }

    public void addComplaints(ComplaintsBean complaintsBean) {
        lstComplaints.add(complaintsBean);
    }

    /** 定义 JSON 接口 */
    public Map<String, String> getJSONBaseData() {
        Map<String, String> obj = new HashMap<String, String>();
        obj.put("purchasing_id", strPurchasingID);
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

    public ArrayList<Map<String, String>> getJSONProductData(String strProductType) {
        ArrayList<ProductBean> lstProduct = null;
        if (strProductType == COMMODITY) {
            lstProduct = lstCommodity;
        } else if (strProductType == SERVICE) {
            lstProduct = lstService;
        } else {
            lstProduct = lstEngineering;
        }

        ArrayList<Map<String, String>> productArray = new ArrayList<Map<String, String>>();
        for (int i = 0; i < lstProduct.size(); i++) {
            ProductBean item = lstProduct.get(i);
            Map<String, String> obj = new HashMap<String, String>();
            obj.put("prj_name", item.strPrjName);
            obj.put("prj_count", String.valueOf(item.nPrjCount));
            obj.put("prj_price",  String.valueOf(item.fPrjPrice));
            obj.put("prj_pre_price",  String.valueOf(item.fPrjPrePrice));
            obj.put("prj_param", item.strPrjParam);
            obj.put("prj_spec", item.strPrjSpec);
            productArray.add(obj);
        }
        return productArray;
    }

    public ArrayList<Map<String, String>> getJSONAttachFile() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstAttachFile.size(); i++) {
            AttachFileBean item = lstAttachFile.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", item.strFileID);
            m.put("name", item.strFileName);
            m.put("size", item.strFileSize);
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getJSONOpinionData() {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i=0; i<lstOpinion.size(); i++) {
            OpinionBean item = lstOpinion.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("op_department", item.strApproveDepartment);
            m.put("op_approve_person", item.strApprovePerson);
            m.put("op_approve_date", item.strApproveDate);
            m.put("op_content", item.strOpinion);
            lst.add(m);
        }
        return lst;
    }

    public ArrayList<Map<String, String>> getJSONComplaintsData() {
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
