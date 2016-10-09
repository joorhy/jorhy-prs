package bean;

import model.PackageModel;
import model.PurchaseModel;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PaymentLeftMenu {
    /** 定义采购执行者目录结构 */
    public static final String TO_PAY                 = "to_pay";                         // 未支付
    public static final String PAID                   = "paid";                           // 已支付

    /** 定义静态函数 */
    static public JSONArray getTree() {
        ArrayList<PurchaseBean> lstPurchasing = PurchaseModel.dao.getPurchaseList();

        JSONArray unpaidChildren = new JSONArray();
        JSONArray paidChildren = new JSONArray();
        for (int i=0; i<lstPurchasing.size(); i++) {
            PurchaseBean purchaseBean = lstPurchasing.get(i);
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", purchaseBean.getPurchaseID());
            childrenNode.put("text", purchaseBean.getPurName());
            childrenNode.put("iconCls", "icon-cut");

            JSONArray packetChildren =
                    new JSONArray(PackageModel.dao.getToPayPackageList(purchaseBean.getPurchaseID()));
            childrenNode.put("children", packetChildren);
            switch (PurchaseModel.dao.getActivityStatus(purchaseBean.getPurchaseID())) {
                case PurchaseActivityBean.SUBCONTRACTED:
                    childrenNode.put("type", PaymentLeftMenu.TO_PAY);
                    unpaidChildren.put(childrenNode);
                    break;
                case PurchaseActivityBean.PAID:
                    childrenNode.put("type", PaymentLeftMenu.PAID);
                    paidChildren.put(childrenNode);
                    break;
            }
        }

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
        lstChildren.put(toPayPrj);
        lstChildren.put(paidPrj);

        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "root");
        rootNode.put("text", "资金支付中心");
        rootNode.put("iconCls", "icon-cut");
        rootNode.put("type", RoleBean.PAYMENT);
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }
}
