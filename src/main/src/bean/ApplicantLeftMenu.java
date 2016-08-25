package bean;

import model.ActivityModel;
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
            switch (ActivityModel.dao.getActivityStatus(lstPurchasing.get(i).getPurchasingID())) {
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
                case ActivityBean.SUBCONTRACTED:
                    childrenNode.put("type", ApplicantLeftMenu.EXECUTED);
                    executedPrjChildren.put(childrenNode);
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
}
