package model;

import com.jfinal.plugin.activerecord.Model;
import bean.PurchasingData;

import java.util.ArrayList;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchasingInfo extends Model<PurchasingInfo> {
    public static final String SUCCESS = "success";
    public static final PurchasingInfo dao = new PurchasingInfo();

    private ArrayList<PurchasingData> lstPurchasingData = new ArrayList<PurchasingData>();
    public String addPurchasing(PurchasingData data) {
        PurchasingInfo info = null;//PurchasingInfo.dao.findFirst("select * from cg_xm_jbxx where CG_XM_JBXXcol_CGHBH=" +
                //data.getPurCode());
        if (info == null) {
            /*String strID = java.util.UUID.randomUUID().toString();
            //dao.set("CG_XM_JBXXcol_ID", strID).set("CG_XM_JBXXcol_CGHBH", data.getPurCode())
                    .set("CG_XM_JBXXcol_ZJLY", data.getStrFundsSrc()).set("CG_XM_JBXXcol_ZJLYWJ",
                    data.getStrFundsNature()).save();*/
            lstPurchasingData.add(data);
        }

        return SUCCESS;
    }

    public String updatePurchasing(PurchasingData data) {
        return SUCCESS;
    }

    public ArrayList<PurchasingData> getPrjDataList() {
        return lstPurchasingData;
    }

    public PurchasingData getPrjData(String strPurchasingID) {
        for (int i = 0; i< lstPurchasingData.size(); i++) {
            if (lstPurchasingData.get(i).getPurchasingID().equals(strPurchasingID)) {
                return lstPurchasingData.get(i);
            }
        }
        return null;
    }
}
