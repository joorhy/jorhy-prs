package model;

import bean.*;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchasingModel extends Model<PurchasingModel> {
    public static final PurchasingModel dao = new PurchasingModel();

    private Map<String,ArrayList<AttachFileBean>> mapAttachFile = new HashMap<String, ArrayList<AttachFileBean>>();
    private ArrayList<PurchaseBean> lstPurchasingData = new ArrayList<PurchaseBean>();
    public String savePurchasing(PurchaseBean data) {
        /*PurchasingModel model = PurchasingModel.dao.findFirst("select * from cg_xm_jbxx where CG_XM_JBXXcol_CGHBH=" +
                data.getPurCode());
        if (info == null) {
            String strID = java.util.UUID.randomUUID().toString();
            dao.set("CG_XM_JBXXcol_ID", strID).set("CG_XM_JBXXcol_CGHBH", data.getPurCode())
                    .set("CG_XM_JBXXcol_ZJLY", data.getFundsSrc()).set("CG_XM_JBXXcol_ZJLYWJ",
                    data.getFundsNature()).save();
        }*/

        for (int i=0; i<lstPurchasingData.size(); i++) {
            if (lstPurchasingData.get(i).getPurchaseID().equals(data.getPurchaseID())){
                lstPurchasingData.remove(i);
                break;
            }
        }
        ArrayList<AttachFileBean> lstFile = mapAttachFile.get(data.getPurchaseID());
        if (lstFile != null){
            for (int i=0; i<lstFile.size(); i++) {
                data.addAttachFile(lstFile.get(i));
            }
            lstFile.clear();
        }
        ActivityModel.dao.addPurchasing(data.getPurchaseID());
        lstPurchasingData.add(data);

        return ErrorCode.SUCCESS;
    }

    public String submitPurchasing(String strPurchasingID) {
        PurchaseBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            ActivityModel.dao.nextActivity(strPurchasingID);
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPurchasing(String strPurchasingID) {
        PurchaseBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            ArrayList<AttachFileBean> attachFileBeen = data.getAttachFileList();
            for (int i = 0; i< attachFileBeen.size(); i++) {
                mapAttachFile.remove(attachFileBeen.get(i));
            }
            ActivityModel.dao.removePurchasing(strPurchasingID);
            lstPurchasingData.remove(data);
        }
        return ErrorCode.SUCCESS;
    }

    public String agreePurchasing(String strPurchasingID, OpinionBean opinionBean) {
        PurchaseBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            ActivityModel.dao.nextActivity(strPurchasingID);
            data.addOpinion(opinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePurchasing(String strPurchasingID, OpinionBean opinionBean) {
        PurchaseBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            ActivityModel.dao.prevActivity(strPurchasingID);
            data.addOpinion(opinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public ArrayList<PurchaseBean> getPurchasingList() {
        return lstPurchasingData;
    }

    public PurchaseBean getPurchasing(String strPurchasingID) {
        for (int i = 0; i< lstPurchasingData.size(); i++) {
            if (lstPurchasingData.get(i).getPurchaseID().equals(strPurchasingID)) {
                return lstPurchasingData.get(i);
            }
        }
        return null;
    }

    public void addAttachFile(String strPurchasingID, AttachFileBean item) {
        ArrayList<AttachFileBean> lst = mapAttachFile.get(strPurchasingID);
        if (lst == null) {
            lst = new ArrayList<AttachFileBean>();
            mapAttachFile.put(strPurchasingID, lst);
        }
        lst.add(item);
    }
}
