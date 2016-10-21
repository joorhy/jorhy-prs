package bean;

import model.PackageModel;
import model.PurchaseModel;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/10/21.
 */
public class RegulatoryLeftMenu {
    /** 定义审批者目录结构 */
    public static final String TO_APPROVE               = "to_approve";                         // 待审批
    public static final String APPROVED                 = "approved";                           // 已审批
    public static final String REJECTED                 = "rejected";                           // 审批未通过

    /** 定义静态函数 */
    static public JSONArray getTree(String strUsername) {
        String sql = "select p.* from purchase p left join role r on " +
                "((p.purchase_activity_id>r.permission_id) or " +
                "(p.purchase_activity_id=r.permission_id and p.role_id=r.id)) " +
                "left join user u on u.id=r.user_id where u.username='" + strUsername + "'";
        ArrayList<PurchaseBean> lstPurchasing = PurchaseModel.dao.getPurchaseList(sql);

        JSONArray toApprovePrjChildren = new JSONArray();
        JSONArray approvedPrjChildren = new JSONArray();
        JSONArray rejectedPrjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            PurchaseBean purchaseBean = lstPurchasing.get(i);
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchaseID());
            childrenNode.put("text", lstPurchasing.get(i).getPurName());
            childrenNode.put("iconCls", "icon-cut");
            String strNodeType = "";
            switch (PurchaseModel.dao.getActivityStatus(purchaseBean.getPurchaseID())) {
                case PurchaseActivityBean.FINANCIAL_APPROVE:
                    strNodeType = ApprovalLeftMenu.TO_APPROVE;
                    break;
                case PurchaseActivityBean.FIN_BUREAU_APPROVE:
                case PurchaseActivityBean.PURCHASE:
                    strNodeType = ApprovalLeftMenu.APPROVED;
                    break;
            }
            childrenNode.put("type", strNodeType);
            childrenNode.put("level", lstPurchasing.get(i).getPurchaseType());
            if (strNodeType.equals(ApprovalLeftMenu.TO_APPROVE)) {
                toApprovePrjChildren.put(childrenNode);
            } else if (strNodeType.equals(ApprovalLeftMenu.APPROVED)) {
                JSONArray packageToPayChildren =
                        new JSONArray(PackageModel.dao.getPackageList(purchaseBean.getPurchaseID(),
                                PackageActivityBean.TO_PAY));
                if (packageToPayChildren.length() > 0) {
                    JSONObject childrenToPayhNode = new JSONObject();
                    childrenToPayhNode.put("id", purchaseBean.getPurchaseID());
                    childrenToPayhNode.put("text", purchaseBean.getPurName());
                    childrenToPayhNode.put("iconCls", "icon-cut");
                    childrenToPayhNode.put("children", packageToPayChildren);
                    childrenToPayhNode.put("type", strNodeType);
                    childrenNode.put("level", purchaseBean.getPurchaseType());
                    approvedPrjChildren.put(childrenToPayhNode);
                } else {
                    approvedPrjChildren.put(childrenNode);
                }
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
        rootNode.put("type", RoleBean.REGULATORY);
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }
}
