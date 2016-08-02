package bean;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Joo on 2016/7/30.
 */
public class PrjData {
    public static final String COMMODITY = "commodity";
    public static final String SERVIDE = "service";
    public static final String ENGINEERING = "engineering";

    private String strPurcCode;
    private String strFundsSrc;
    private String strContacts;
    private String strPhoneNum;
    private String strFundsNature;
    private double fCommodityTotlePrice;
    private double fServiceTotlePrice;
    private double fEngineeringTotlePrice;
    private ArrayList<ProductItem> lstCommodity = new ArrayList<ProductItem>();
    private ArrayList<ProductItem> lstService = new ArrayList<ProductItem>();
    private ArrayList<ProductItem> lstEngineering = new ArrayList<ProductItem>();

    public String ReadBaseData(JSONObject obj) {
        lstCommodity.clear();
        lstService.clear();
        lstEngineering.clear();

        strPurcCode = obj.getString("purc_code");
        strFundsSrc = obj.getString("funds_src");
        strContacts = obj.getString("contacts");
        strPhoneNum = obj.getString("phone_num");
        strFundsNature = obj.getString("funds_nature");
        fCommodityTotlePrice = obj.getDouble("commodity_total_price");
        fServiceTotlePrice = obj.getDouble("service_total_price");
        fEngineeringTotlePrice = obj.getDouble("engineering_total_price");

        return null;
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
            prjItem.fPrjTotalPrice = item.getDouble("prj_total_price");
            prjItem.strPrjParam = item.getString("prj_param");
            prjItem.strPrjAttachFile = item.getString("prj_attach");

            if (strProductType == COMMODITY) {
                lstCommodity.add(prjItem);
            } else if (strProductType == SERVIDE) {
                lstService.add(prjItem);
            } else {
                lstEngineering.add(prjItem);
            }
        }
        return null;
    }
}
