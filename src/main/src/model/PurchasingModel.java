package model;

import bean.AttachFileBean;
import bean.OpinionBean;
import com.jfinal.plugin.activerecord.Model;
import bean.PurchasingBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchasingModel extends Model<PurchasingModel> {
    public static final String SUCCESS = "success";
    public static final PurchasingModel dao = new PurchasingModel();

    private Map<String,ArrayList<AttachFileBean>> mapAttachFile = new HashMap<String, ArrayList<AttachFileBean>>();
    private ArrayList<PurchasingBean> lstPurchasingData = new ArrayList<PurchasingBean>();
    public String savePurchasing(PurchasingBean data) {
        /*PurchasingModel model = PurchasingModel.dao.findFirst("select * from cg_xm_jbxx where CG_XM_JBXXcol_CGHBH=" +
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
        ArrayList<AttachFileBean> lstFile = mapAttachFile.get(data.getPurchasingID());
        if (lstFile != null){
            for (int i=0; i<lstFile.size(); i++) {
                data.addAttachFile(lstFile.get(i));
            }
            lstFile.clear();
        }
        data.setApproveStatus(PurchasingBean.INITIALIZE);
        lstPurchasingData.add(data);

        return SUCCESS;
    }

    public String submitPurchasing(String strPurchasingID) {
        PurchasingBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            data.setApproveStatus(PurchasingBean.ACC_APPROVE);
        }
        return SUCCESS;
    }

    public String cancelPurchasing(String strPurchasingID) {
        PurchasingBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            ArrayList<AttachFileBean> attachFileBeen = data.getAttachFileList();
            for (int i = 0; i< attachFileBeen.size(); i++) {
                mapAttachFile.remove(attachFileBeen.get(i));
            }
            lstPurchasingData.remove(data);
        }
        return SUCCESS;
    }

    public String agreePurchasing(String strPurchasingID, OpinionBean opinionBean) {
        PurchasingBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            data.setApproveStatus(data.getApproveStatus() + 1);
            data.addOpinion(opinionBean);
        }
        return SUCCESS;
    }

    public String disagreePurchasing(String strPurchasingID, OpinionBean opinionBean) {
        PurchasingBean data = getPurchasing(strPurchasingID);
        if (data != null) {
            data.setApproveStatus(data.getApproveStatus() + 100);
            data.addOpinion(opinionBean);
        }
        return SUCCESS;
    }

    public ArrayList<PurchasingBean> getPurchasingList() {
        return lstPurchasingData;
    }

    public PurchasingBean getPurchasing(String strPurchasingID) {
        for (int i = 0; i< lstPurchasingData.size(); i++) {
            if (lstPurchasingData.get(i).getPurchasingID().equals(strPurchasingID)) {
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
