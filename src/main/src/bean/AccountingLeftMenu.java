package bean;

import model.PackageModel;
import model.PurchaseModel;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/10/21.
 */
public class AccountingLeftMenu {
    /** 定义审批者目录结构 */
    public static final String TO_APPROVE               = "to_approve";                         // 待审批
    public static final String APPROVED                 = "approved";                           // 已审批
    public static final String REJECTED                 = "rejected";                           // 审批未通过
    public static final String TO_PAY                 = "to_pay";                         // 未支付
    public static final String PAID                   = "paid";                           // 已支付

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
        JSONArray unpaidChildren = new JSONArray();
        JSONArray paidChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            PurchaseBean purchaseBean = lstPurchasing.get(i);
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchaseID());
            childrenNode.put("text", lstPurchasing.get(i).getPurName());
            childrenNode.put("iconCls", "icon-cut");
            String strNodeType = "";
            switch (PurchaseModel.dao.getActivityStatus(purchaseBean.getPurchaseID())) {
                case PurchaseActivityBean.ACC_APPROVE:
                    strNodeType = TO_APPROVE;
                    break;
                case PurchaseActivityBean.LEAD_APPROVE:
                case PurchaseActivityBean.DIR_APPROVE:
                case PurchaseActivityBean.SECTOR_APPROVE:
                case PurchaseActivityBean.FINANCIAL_APPROVE:
                case PurchaseActivityBean.FIN_BUREAU_APPROVE:
                case PurchaseActivityBean.PURCHASE:
                    strNodeType = APPROVED;
                    break;
            }
            childrenNode.put("type", strNodeType);
            childrenNode.put("level", lstPurchasing.get(i).getPurchaseType());
            if (strNodeType.equals(ApprovalLeftMenu.TO_APPROVE)) {
                toApprovePrjChildren.put(childrenNode);
            } else if (strNodeType.equals(ApprovalLeftMenu.APPROVED)) {
                JSONArray packageRejectChildren =
                        new JSONArray(PackageModel.dao.getPackageList(purchaseBean.getPurchaseID(),
                                PackageActivityBean.APPLIED));
                JSONArray packageUnpaidChildren =
                        new JSONArray(PackageModel.dao.getPackageList(purchaseBean.getPurchaseID(),
                                PackageActivityBean.TO_PAY, PackageActivityBean.TO_REPAY));
                JSONArray packagePaidChildren =
                        new JSONArray(PackageModel.dao.getPackageList(purchaseBean.getPurchaseID(),
                                PackageActivityBean.PAID));
                if (packageRejectChildren.length() > 0 || packageUnpaidChildren.length() > 0
                        || packagePaidChildren.length() > 0) {
                    if (packageRejectChildren.length() > 0) {
                        JSONObject packageRejectChildrenNode = new JSONObject();
                        packageRejectChildrenNode.put("id", purchaseBean.getPurchaseID());
                        packageRejectChildrenNode.put("text", purchaseBean.getPurName());
                        packageRejectChildrenNode.put("iconCls", "icon-cut");
                        packageRejectChildrenNode.put("level", purchaseBean.getPurchaseType());
                        packageRejectChildrenNode.put("children", packageRejectChildren);
                        packageRejectChildrenNode.put("type", REJECTED);
                        rejectedPrjChildren.put(packageRejectChildrenNode);
                    }
                    if (packageUnpaidChildren.length() > 0) {
                        JSONObject packageUnpaidChildrenNode = new JSONObject();
                        packageUnpaidChildrenNode.put("level", purchaseBean.getPurchaseType());
                        packageUnpaidChildrenNode.put("id", purchaseBean.getPurchaseID());
                        packageUnpaidChildrenNode.put("text", purchaseBean.getPurName());
                        packageUnpaidChildrenNode.put("iconCls", "icon-cut");
                        packageUnpaidChildrenNode.put("children", packageUnpaidChildren);
                        packageUnpaidChildrenNode.put("type", TO_PAY);
                        unpaidChildren.put(packageUnpaidChildrenNode);
                    }
                    if (packagePaidChildren.length() > 0) {
                        JSONObject packagePaidChildrenNode = new JSONObject();
                        packagePaidChildrenNode.put("id", purchaseBean.getPurchaseID());
                        packagePaidChildrenNode.put("text", purchaseBean.getPurName());
                        packagePaidChildrenNode.put("iconCls", "icon-cut");
                        packagePaidChildrenNode.put("children", packagePaidChildren);
                        packagePaidChildrenNode.put("level", purchaseBean.getPurchaseType());
                        packagePaidChildrenNode.put("type", PAID);
                        paidChildren.put(packagePaidChildrenNode);
                    }
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
        rejectPrj.put("text", "未申请支付项目");
        rejectPrj.put("iconCls", "icon-cut");
        rejectPrj.put("children", rejectedPrjChildren);

        JSONObject toPayPrj = new JSONObject();
        toPayPrj.put("id", "to_pay_prj");
        toPayPrj.put("text", "未支付项目");
        toPayPrj.put("iconCls", "icon-cut");
        toPayPrj.put("children", unpaidChildren);

        JSONObject paidPrj = new JSONObject();
        paidPrj.put("id", "paid_prj");
        paidPrj.put("text", "已支付项目");
        paidPrj.put("iconCls", "icon-cut");
        paidPrj.put("children", paidChildren);

        JSONArray lstChildren = new JSONArray();
        lstChildren.put(toApprovePrj);
        lstChildren.put(approvedPrj);
        lstChildren.put(rejectPrj);
        lstChildren.put(toPayPrj);
        lstChildren.put(paidPrj);

        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "root");
        rootNode.put("text", "项目审批管理中心");
        rootNode.put("iconCls", "icon-cut");
        rootNode.put("type", RoleBean.ACCOUNTING);
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }
}
