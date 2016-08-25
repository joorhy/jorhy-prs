package bean;

import model.ActivityModel;
import model.PacketModel;
import model.PurchasingModel;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PurchaseLeftMenu {
    /** 定义采购执行者目录结构 */
    public static final String TO_DIVIDE                = "to_divide";                          // 未分包
    public static final String DIVIDED                  = "divided";                            // 已分包
    public static final String FINISHED                 = "finished";                           // 已结束
    public static final String INTERRUPT                = "interrupt";                          // 被投诉
    public static final String FAILED                   = "failed";                             // 流标

    /** 定义静态函数 */
    static public JSONArray getTree() {
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
                    childrenNode.put("type", PurchaseLeftMenu.INTERRUPT);
                    complaintsChildren.put(childrenNode);
                    break;
                case 2:// 流标
                    childrenNode.put("type", PurchaseLeftMenu.FAILED);
                    failedChildren.put(childrenNode);
                    break;
                default: {
                    JSONArray packetChildren = new JSONArray();
                    for (int j = 0; j< PacketModel.dao.getPacketList().size(); j++) {
                        JSONObject packetNode = new JSONObject();
                        PacketBean item = PacketModel.dao.getPacketList().get(j);
                        if (item.strPurchasingID.equals(purchasingBean.getPurchasingID())) {
                            packetNode.put("id", item.strPackID);
                            packetNode.put("text", "第" + String.valueOf(packetChildren.length() + 1) + "包");
                            packetNode.put("iconCls", "icon-cut");
                            packetNode.put("type", "packet");
                            packetChildren.put(packetNode);
                        }
                    }
                    childrenNode.put("children", packetChildren);
                    switch (ActivityModel.dao.getActivityStatus(purchasingBean.getPurchasingID())) {
                        case ActivityBean.SUBCONTRACTING:
                            childrenNode.put("type", PurchaseLeftMenu.TO_DIVIDE);
                            subcontractingChildren.put(childrenNode);
                            break;
                        case ActivityBean.SUBCONTRACTED:
                            childrenNode.put("type", PurchaseLeftMenu.DIVIDED);
                            subcontractedChildren.put(childrenNode);
                            break;
                        case ActivityBean.PAID:
                            childrenNode.put("type", PurchaseLeftMenu.FINISHED);
                            completedChildren.put(childrenNode);
                            break;
                    }
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
}
