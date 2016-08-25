package bean;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PacketBean {
    public String strPackID;                        // 包ID
    public String strPackName;                      // 包名
    public String strPackCode;                      // 采购文号
    public String strPurAddress;                    // 采购地点
    public String strPurDate;                       // 采购日期
    public String strExpertCount;                   // 抽取专家人数
    public String strPurMethod;                     // 采购方式
    public String strPublicity;                     // 采购需求公告
    public String strSupplier;                      // 中标供应商
    public String strAmount;                        // 中标金额
    public String strPurchasingID;                    // 采购函ID

    public ArrayList<ProductBean> lstProduct = new ArrayList<ProductBean>();           // 采购项目
    /**
     * 合同附件
     */
    private ArrayList<AttachFileBean> lstAttachFile = new ArrayList<AttachFileBean>();  // 附件

    /**
     * 定义 Controller 接口
     */
    public String parseBaseData(JSONObject obj) {
        lstProduct.clear();
        strPackID = obj.getString("packet_id");
        strPackCode = obj.getString("pack_code");
        strPurAddress = obj.getString("pur_address");
        strExpertCount = obj.getString("expert_count");
        strPurDate = obj.getString("pur_date");
        strPurMethod = obj.getString("pur_method");
        strPublicity = obj.getString("pur_publicity");
        strSupplier = obj.getString("pur_supplier");
        strAmount = obj.getString("pur_amount");
        strPurchasingID = obj.getString("purchasing_id");

        return ErrorCode.SUCCESS;
    }

    public String parseProductData(JSONObject obj) {
        int nTotal = obj.getInt("total");
        JSONArray arr = obj.getJSONArray("rows");
        for (int i = 0; i < nTotal; i++) {
            JSONObject item = (JSONObject) arr.get(i);
            ProductBean prjItem = new ProductBean();
            prjItem.strProductID = item.getString("project_id");
            prjItem.strPrjType = item.getString("product_type");
            prjItem.strPrjName = item.getString("prj_name");
            prjItem.nPrjCount = item.getInt("prj_count");
            prjItem.fPrjPrice = item.getDouble("prj_price");
            prjItem.strPrjSpec = item.getString("prj_spec");
            prjItem.fPrjPrePrice = item.getDouble("prj_pre_price");
            prjItem.strPrjParam = item.getString("prj_param");
            lstProduct.add(prjItem);
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * 定义 JSON 接口
     */
    public Map<String, String> getJSONBaseData() {
        Map<String, String> obj = new HashMap<String, String>();
        obj.put("packet_id", strPackID);
        obj.put("pack_code", strPackCode);
        obj.put("pur_address", strPurAddress);
        obj.put("expert_count", strExpertCount);
        obj.put("pur_date", strPurDate);
        obj.put("pur_method", strPurMethod);
        obj.put("pur_publicity", strPublicity);
        obj.put("pur_supplier", strSupplier);
        obj.put("pur_amount", strAmount);
        obj.put("purchasing_id", strPurchasingID);

        return obj;
    }

    public ArrayList<Map<String, String>> getJSONPacketItems() {
        ArrayList<Map<String, String>> productArray = new ArrayList<Map<String, String>>();
        for (int i = 0; i < lstProduct.size(); i++) {
            ProductBean item = lstProduct.get(i);
            Map<String, String> obj = new HashMap<String, String>();
            obj.put("project_id", item.strProductID);
            obj.put("product_type", item.strPrjType);
            obj.put("prj_name", item.strPrjName);
            obj.put("prj_count", String.valueOf(item.nPrjCount));
            obj.put("prj_price", String.valueOf(item.fPrjPrice));
            obj.put("prj_pre_price", String.valueOf(item.fPrjPrePrice));
            obj.put("prj_param", item.strPrjParam);
            obj.put("prj_spec", item.strPrjSpec);
            productArray.add(obj);
        }
        return productArray;
    }
}
