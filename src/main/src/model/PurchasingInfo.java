package model;

import bean.AttachFileItem;
import com.jfinal.plugin.activerecord.Model;
import bean.PurchasingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchasingInfo extends Model<PurchasingInfo> {
    public static final String SUCCESS = "success";
    public static final PurchasingInfo dao = new PurchasingInfo();

    private Map<String,ArrayList<AttachFileItem>> mapAttachFile = new HashMap<String, ArrayList<AttachFileItem>>();
    private ArrayList<PurchasingData> lstPurchasingData = new ArrayList<PurchasingData>();
    public String savePurchasing(PurchasingData data) {
        /*PurchasingInfo info = PurchasingInfo.dao.findFirst("select * from cg_xm_jbxx where CG_XM_JBXXcol_CGHBH=" +
                data.getPurCode());
        if (info == null) {
            String strID = java.util.UUID.randomUUID().toString();
            dao.set("CG_XM_JBXXcol_ID", strID).set("CG_XM_JBXXcol_CGHBH", data.getPurCode())
                    .set("CG_XM_JBXXcol_ZJLY", data.getFundsSrc()).set("CG_XM_JBXXcol_ZJLYWJ",
                    data.getFundsNature()).save();
        }*/

        for (int i=0; i<lstPurchasingData.size(); i++) {
            if (lstPurchasingData.get(i).getPurchasingID().equals(data.getPurchasingID())){
                lstPurchasingData.remove(i);
                break;
            }
        }
        ArrayList<AttachFileItem> lstFile = mapAttachFile.get(data.getPurchasingID());
        if (lstFile != null){
            for (int i=0; i<lstFile.size(); i++) {
                data.addAttachFile(lstFile.get(i));
            }
            lstFile.clear();
        }
        data.setStatus(PurchasingData.NEW);
        lstPurchasingData.add(data);

        return SUCCESS;
    }

    public String submitPurchasing(String strPurchasingID) {
        PurchasingData data = getPurchasing(strPurchasingID);
        if (data != null) {
            data.setStatus(PurchasingData.SUBMITTED);
        }
        return SUCCESS;
    }

    public ArrayList<PurchasingData> getPurchasingList() {
        return lstPurchasingData;
    }

    public PurchasingData getPurchasing(String strPurchasingID) {
        for (int i = 0; i< lstPurchasingData.size(); i++) {
            if (lstPurchasingData.get(i).getPurchasingID().equals(strPurchasingID)) {
                return lstPurchasingData.get(i);
            }
        }
        return null;
    }

    public void addAttachFile(String strPurchasingID, AttachFileItem item) {
        ArrayList<AttachFileItem> lst = mapAttachFile.get(strPurchasingID);
        if (lst == null) {
            lst = new ArrayList<AttachFileItem>();
            mapAttachFile.put(strPurchasingID, lst);
        }
        lst.add(item);
    }
}
