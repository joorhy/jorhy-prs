package model;

import bean.*;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchaseModel extends Model<PurchaseModel> {
    public static final PurchaseModel dao = new PurchaseModel();

    private Map<String,ArrayList<PurchaseAttachFileBean>> mapAttachFile = new HashMap<String, ArrayList<PurchaseAttachFileBean>>();
    private ArrayList<PurchaseBean> lstPurchaseData = new ArrayList<PurchaseBean>();
    public String savePurchase(PurchaseBean purchaseBean) {
        /*PurchaseModel model = PurchaseModel.dao.findFirst("select * from cg_xm_jbxx where CG_XM_JBXXcol_CGHBH=" +
                data.getPurCode());
        if (info == null) {
            String strID = java.util.UUID.randomUUID().toString();
            dao.set("CG_XM_JBXXcol_ID", strID).set("CG_XM_JBXXcol_CGHBH", data.getPurCode())
                    .set("CG_XM_JBXXcol_ZJLY", data.getFundsSrc()).set("CG_XM_JBXXcol_ZJLYWJ",
                    data.getFundsNature()).save();
        }*/

        for (int i=0; i<lstPurchaseData.size(); i++) {
            if (lstPurchaseData.get(i).getPurchaseID().equals(purchaseBean.getPurchaseID())){
                lstPurchaseData.remove(i);
                break;
            }
        }
        ArrayList<PurchaseAttachFileBean> lstFile = mapAttachFile.get(purchaseBean.getPurchaseID());
        if (lstFile != null){
            for (int i=0; i<lstFile.size(); i++) {
                purchaseBean.addAttachFile(lstFile.get(i));
            }
            lstFile.clear();
        }
        PurchaseActivityModel.dao.addPurchase(purchaseBean.getPurchaseID());
        lstPurchaseData.add(purchaseBean);

        return ErrorCode.SUCCESS;
    }

    public String submitPurchase(String strPurchaseID) {
        PurchaseBean data = getPurchase(strPurchaseID);
        if (data != null) {
            PurchaseActivityModel.dao.nextActivity(strPurchaseID);
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPurchase(String strPurchaseID) {
        PurchaseBean data = getPurchase(strPurchaseID);
        if (data != null) {
            ArrayList<PurchaseAttachFileBean> attachFileBeen = data.getAttachFileList();
            for (int i = 0; i< attachFileBeen.size(); i++) {
                mapAttachFile.remove(attachFileBeen.get(i));
            }
            PurchaseActivityModel.dao.removePurchase(strPurchaseID);
            lstPurchaseData.remove(data);
        }
        return ErrorCode.SUCCESS;
    }

    public String agreePurchase(String strPurchaseID, OpinionBean opinionBean) {
        PurchaseBean data = getPurchase(strPurchaseID);
        if (data != null) {
            PurchaseActivityModel.dao.nextActivity(strPurchaseID);
            data.addOpinion(opinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePurchase(String strPurchaseID, OpinionBean opinionBean) {
        PurchaseBean data = getPurchase(strPurchaseID);
        if (data != null) {
            PurchaseActivityModel.dao.prevActivity(strPurchaseID);
            data.addOpinion(opinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public ArrayList<PurchaseBean> getPurchaseList() {
        return lstPurchaseData;
    }

    public PurchaseBean getPurchase(String strPurchaseID) {
        for (int i = 0; i< lstPurchaseData.size(); i++) {
            if (lstPurchaseData.get(i).getPurchaseID().equals(strPurchaseID)) {
                return lstPurchaseData.get(i);
            }
        }
        return null;
    }

    public void addAttachFile(String strPurchaseID, PurchaseAttachFileBean item) {
        ArrayList<PurchaseAttachFileBean> lst = mapAttachFile.get(strPurchaseID);
        if (lst == null) {
            lst = new ArrayList<PurchaseAttachFileBean>();
            mapAttachFile.put(strPurchaseID, lst);
        }
        lst.add(item);
    }
}
