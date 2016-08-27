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
public class ApplicantLeftMenu {
    /** 定义申请者目录结构 */
    public static final String CREATE                   = "create";                             // 新建采购函
    public static final String SUBMITTED                = "submitted";                          // 已提交
    public static final String EXECUTED                 = "executed";                           // 已执行
    public static final String IMPLEMENTED              = "implemented";                        // 已完成

    /** 定义静态函数 */
    static public JSONArray getTree() {
        ArrayList<PurchaseBean> lstPurchasing = PurchasingModel.dao.getPurchasingList();

        JSONArray newPrjChildren = new JSONArray();
        JSONArray committedPrjChildren = new JSONArray();
        JSONArray executedPrjChildren = new JSONArray();
        JSONArray implementedPrjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            PurchaseBean purchaseBean = lstPurchasing.get(i);
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", purchaseBean.getPurchaseID());
            childrenNode.put("text", purchaseBean.getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            switch (ActivityModel.dao.getActivityStatus(purchaseBean.getPurchaseID())) {
                case ActivityBean.INITIALIZE:
                    childrenNode.put("type", ApplicantLeftMenu.CREATE);
                    newPrjChildren.put(childrenNode);
                    break;
                case ActivityBean.ACC_APPROVE:
                case ActivityBean.ACC_APPROVE_FAILED:
                case ActivityBean.DIR_APPROVE:
                case ActivityBean.FINANCIAL_APPROVE:
                case ActivityBean.FIN_BUREAU_APPROVE:
                case ActivityBean.SUBCONTRACTING:
                    childrenNode.put("type", ApplicantLeftMenu.SUBMITTED);
                    committedPrjChildren.put(childrenNode);
                    break;
                case ActivityBean.SUBCONTRACTED: {
                    JSONArray packetChildren =
                            new JSONArray(PacketModel.dao.getPackageList(purchaseBean.getPurchaseID()));
                    childrenNode.put("type", ApplicantLeftMenu.EXECUTED);
                    childrenNode.put("children", packetChildren);
                    executedPrjChildren.put(childrenNode);
                    break;
                }
            }
        }

        JSONObject newPrj = new JSONObject();
        newPrj.put("id", "new_prj");
        newPrj.put("text", "新建采购过程");
        newPrj.put("iconCls", "icon-cut");
        newPrj.put("type", ApplicantLeftMenu.CREATE);
        newPrj.put("children", newPrjChildren);

        JSONObject committedPrj = new JSONObject();
        committedPrj.put("id", "committed_prj");
        committedPrj.put("text", "已提交采购过程");
        committedPrj.put("iconCls", "icon-cut");
        committedPrj.put("type", ApplicantLeftMenu.SUBMITTED);
        committedPrj.put("children", committedPrjChildren);

        JSONObject executedPrj = new JSONObject();
        executedPrj.put("id", "executed_prj");
        executedPrj.put("text", "已执行采购过程");
        executedPrj.put("iconCls", "icon-cut");
        executedPrj.put("type", ApplicantLeftMenu.EXECUTED);
        executedPrj.put("children", executedPrjChildren);

        JSONObject implementedPrj = new JSONObject();
        implementedPrj.put("id", "implemented_prj");
        implementedPrj.put("text", "已完成采购过程");
        implementedPrj.put("iconCls", "icon-cut");
        implementedPrj.put("type", ApplicantLeftMenu.IMPLEMENTED);
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
        rootNode.put("type", RoleBean.APPLICANT);
        rootNode.put("children", lstChildren);

        JSONArray lstRoot = new JSONArray();
        lstRoot.put(rootNode);

        return lstRoot;
    }
}
