package bean;

import model.ActivityModel;
import model.PurchasingModel;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class ApprovalLeftMenu {
    /** 定义审批者目录结构 */
    public static final String TO_APPROVE               = "to_approve";                         // 待审批
    public static final String APPROVED                 = "approved";                           // 已审批
    public static final String REJECTED                 = "rejected";                           // 审批未通过

    static private String getNodeType(String strUserRole, int nStatus) {
        String strNodeType = "";
        if (strUserRole.equals(RoleBean.ACCOUNTING)){
            switch (nStatus) {
                case ActivityBean.ACC_APPROVE:
                    strNodeType = ApprovalLeftMenu.TO_APPROVE;
                    break;
                case ActivityBean.DIR_APPROVE:
                case ActivityBean.FINANCIAL_APPROVE:
                case ActivityBean.FIN_BUREAU_APPROVE:
                case ActivityBean.SUBCONTRACTING:
                    strNodeType = ApprovalLeftMenu.APPROVED;
                    break;
                case ActivityBean.DIR_APPROVE_FAILED:
                    strNodeType = ApprovalLeftMenu.REJECTED;
                    break;
            }
        } else if (strUserRole.equals(RoleBean.DIRECTOR)) {
            switch (nStatus) {
                case ActivityBean.DIR_APPROVE:
                    strNodeType = ApprovalLeftMenu.TO_APPROVE;
                    break;
                case ActivityBean.FINANCIAL_APPROVE:
                case ActivityBean.FIN_BUREAU_APPROVE:
                case ActivityBean.SUBCONTRACTING:
                    strNodeType = ApprovalLeftMenu.APPROVED;
                    break;
                case ActivityBean.FINANCIAL_APPROVE_FAILED:
                    strNodeType = ApprovalLeftMenu.REJECTED;
                    break;
            }
        } else if (strUserRole.equals(RoleBean.REGULATORY)) {
            switch (nStatus) {
                case ActivityBean.FINANCIAL_APPROVE:
                    strNodeType = ApprovalLeftMenu.TO_APPROVE;
                    break;
                case ActivityBean.FIN_BUREAU_APPROVE:
                case ActivityBean.SUBCONTRACTING:
                    strNodeType = ApprovalLeftMenu.APPROVED;
                    break;
                case ActivityBean.FIN_BUREAU_APPROVE_FAILED:
                    strNodeType = ApprovalLeftMenu.REJECTED;
                    break;
            }
        } else if (strUserRole.equals(RoleBean.BUREAU)) {
            switch (nStatus) {
                case ActivityBean.FIN_BUREAU_APPROVE:
                    strNodeType = ApprovalLeftMenu.TO_APPROVE;
                    break;
                case ActivityBean.SUBCONTRACTING:
                    strNodeType = ApprovalLeftMenu.APPROVED;
                    break;
            }
        }
        return strNodeType;
    }

    /** 定义静态函数 */
    static public JSONArray getTree(String strUserRole) {
        ArrayList<PurchaseBean> lstPurchasing = PurchasingModel.dao.getPurchasingList();

        JSONArray toApprovePrjChildren = new JSONArray();
        JSONArray approvedPrjChildren = new JSONArray();
        JSONArray rejectedPrjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchaseID());
            childrenNode.put("text", lstPurchasing.get(i).getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            String strNodeType = getNodeType(strUserRole,
                    ActivityModel.dao.getActivityStatus(lstPurchasing.get(i).getPurchaseID()));
            childrenNode.put("type", strNodeType);
            if (strNodeType.equals(ApprovalLeftMenu.TO_APPROVE)) {
                toApprovePrjChildren.put(childrenNode);
            } else if (strNodeType.equals(ApprovalLeftMenu.APPROVED)) {
                approvedPrjChildren.put(childrenNode);
            } else if (strNodeType.equals(ApprovalLeftMenu.REJECTED)) {
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
        rootNode.put("type", strUserRole);
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }
}
