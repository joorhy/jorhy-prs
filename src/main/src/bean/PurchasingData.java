package bean;

import model.PurchasingInfo;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchasingData {
    /** 定义错误码 */
    public static final String SUCCESS          = "success";
    /** 定义项目类别 */
    public static final String COMMODITY        = "commodity";
    public static final String SERVICE          = "service";
    public static final String ENGINEERING      = "engineering";
    /** 定义采购状态 */
    public static final String NEW              = "new";
    public static final String COMMITTED        = "committed";
    public static final String EXECUTED         = "executed";
    public static final String IMPLEMENTED      = "implemented";

    private String strPurchasingID;                                                     // 采购函ID
    private String strPurCode;                                                          // 采购编号
    private String strFundsSrc;                                                         // 资金来源
    private String strContacts;                                                         // 联系人
    private String strPhoneNum;                                                         // 联系电话
    private String strFundsNature;                                                      // 资金性质
    private String strStatus;                                                           // 采购函状态
    private String strCommodityPrePrice;                                                // 商品类预算总金额
    private String strServicePrePrice;                                                  // 服务类预算总金额
    private String strEngineeringPrePrice;                                              // 工程类预算总金额
    private ArrayList<ProductItem> lstCommodity = new ArrayList<ProductItem>();         // 商品类
    private ArrayList<ProductItem> lstService = new ArrayList<ProductItem>();           // 服务类
    private ArrayList<ProductItem> lstEngineering = new ArrayList<ProductItem>();       // 工程类
    private ArrayList<AttachFileItem> lstAttachFile = new ArrayList<AttachFileItem>();  // 附件

    /** 定义静态函数 */
    static public JSONArray getTree() {
        ArrayList<PurchasingData> lstPurchasing = PurchasingInfo.dao.getPurchasingList();

        JSONArray newPrjChildren = new JSONArray();
        JSONArray committedPrjChildren = new JSONArray();
        JSONArray executedPrjChildren = new JSONArray();
        JSONArray implementedPrjChildren = new JSONArray();
        for (int i = 0; i< lstPurchasing.size(); i++) {
            JSONObject childrenNode = new JSONObject();
            childrenNode.put("id", lstPurchasing.get(i).getPurchasingID());
            childrenNode.put("text", lstPurchasing.get(i).getPurCode());
            childrenNode.put("iconCls", "icon-cut");
            if (lstPurchasing.get(i).getStatus().equals(NEW)) {
                newPrjChildren.put(childrenNode);
            } else if (lstPurchasing.get(i).getStatus().equals(COMMITTED)) {
                committedPrjChildren.put(childrenNode);
            } else if (lstPurchasing.get(i).getStatus().equals(EXECUTED)) {
                executedPrjChildren.put(childrenNode);
            } else {
                implementedPrjChildren.put(childrenNode);
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

    public String getStatus() {
        return strStatus;
    }

    public void setStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getServicePrePrice() {
        return strServicePrePrice;
    }

    public String getEngineeringPrePrice() {
        return strEngineeringPrePrice;
    }

    public ArrayList<ProductItem> getCommodityList() {
        return lstCommodity;
    }

    public ArrayList<ProductItem> getServiceList() {
        return lstService;
    }

    public ArrayList<ProductItem> getEngineeringList() {
        return lstEngineering;
    }

    public ArrayList<AttachFileItem> getAttachFileList() {
        return lstAttachFile;
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
            ProductItem prjItem = new ProductItem();
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

    public void addAttachFile(AttachFileItem item) {
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

    public AttachFileItem getAttachFileItem(String strFileID) {
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
        ArrayList<ProductItem> lstProduct = null;
        if (strProductType == COMMODITY) {
            lstProduct = lstCommodity;
        } else if (strProductType == SERVICE) {
            lstProduct = lstService;
        } else {
            lstProduct = lstEngineering;
        }

        ArrayList<Map<String, String>> productArray = new ArrayList<Map<String, String>>();
        for (int i = 0; i < lstProduct.size(); i++) {
            ProductItem item = lstProduct.get(i);
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
            AttachFileItem item = lstAttachFile.get(i);
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", item.strFileID);
            m.put("name", item.strFileName);
            m.put("size", item.strFileSize);
            lst.add(m);
        }
        return lst;
    }
}
