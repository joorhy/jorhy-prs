package bean;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PrjData {
    public static final String SUCCESS = "success";

    public static final String COMMODITY = "commodity";
    public static final String SERVICE = "service";
    public static final String ENGINEERING = "engineering";

    private String strPurCode;
    private String strFundsSrc;
    private String strContacts;
    private String strPhoneNum;
    private String strFundsNature;
    private double fCommodityPrePrice;
    private double fServicePrePrice;
    private double fEngineeringPrePrice;
    private ArrayList<ProductItem> lstCommodity = new ArrayList<ProductItem>();
    private ArrayList<ProductItem> lstService = new ArrayList<ProductItem>();
    private ArrayList<ProductItem> lstEngineering = new ArrayList<ProductItem>();

    public String getPurCode() {
        return strPurCode;
    }

    public String getStrFundsSrc() {
        return strFundsSrc;
    }

    public String getStrContacts() {
        return strContacts;
    }

    public String getStrPhoneNum() {
        return strPhoneNum;
    }

    public String getStrFundsNature() {
        return strFundsNature;
    }

    public double getCommodityPrePrice() {
        return fCommodityPrePrice;
    }

    public double getServicePrePrice() {
        return fServicePrePrice;
    }

    public double getEngineeringPrePrice() {
        return fEngineeringPrePrice;
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

    public String ReadBaseData(JSONObject obj) {
        lstCommodity.clear();
        lstService.clear();
        lstEngineering.clear();

        strPurCode = obj.getString("pur_code");
        strFundsSrc = obj.getString("funds_src");
        strContacts = obj.getString("contacts");
        strPhoneNum = obj.getString("phone_num");
        strFundsNature = obj.getString("funds_nature");
        fCommodityPrePrice = obj.getDouble("commodity_pre_price");
        fServicePrePrice = obj.getDouble("service_pre_price");
        fEngineeringPrePrice = obj.getDouble("engineering_pre_price");

        return SUCCESS;
    }

    public JSONObject GetBaseDataObj() {
        JSONObject obj = new JSONObject();
        obj.put("pur_code", strPurCode);
        obj.put("funds_src", strFundsSrc);
        obj.put("contacts", strContacts);
        obj.put("phone_num", strPhoneNum);
        obj.put("funds_nature", strFundsNature);
        obj.put("commodity_pre_price", fCommodityPrePrice);
        obj.put("service_pre_price", fServicePrePrice);
        obj.put("engineering_pre_price", fEngineeringPrePrice);

        return obj;
    }

    public String ReadProductData(JSONObject obj, String strProductType) {
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

    public ArrayList<Map<String, String>> getProductDataObj(String strProductType) {
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
}
