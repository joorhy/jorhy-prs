package model;

import bean.*;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joo on 2016/7/30.
 */
public class PurchaseModel extends Model<PurchaseModel> {
    public static final PurchaseModel dao = new PurchaseModel();

    private Map<String,ArrayList<PurchaseAttachFileBean>> mapAttachFile = new HashMap<String, ArrayList<PurchaseAttachFileBean>>();
    private ArrayList<PurchaseBean> lstPurchaseData = new ArrayList<PurchaseBean>();
    public String savePurchase(PurchaseBean purchaseBean) {
        String url = "select id from purchase p where p.purchase_uuid=\""
                + purchaseBean.getPurchaseID() + "\"";
        PurchaseModel purchaseModel = PurchaseModel.dao.findFirst(url);
        if (purchaseModel != null) {
            // 删除原有内容
            int nPurchaseID = purchaseModel.get("id");
            PurchaseFileAttachModel.dao.removePurchaseAttachFiles(nPurchaseID);
            ProductModel.dao.removePurchaseAttachFiles(nPurchaseID);
            dao.deleteById(purchaseModel);
        }

        dao.set("purchase_uuid", purchaseBean.getPurchaseID()).set("code", purchaseBean.getPurCode())
                .set("funds_src", purchaseBean.getFundsSrc()).set("contacts", purchaseBean.getContacts())
                .set("phone_num", purchaseBean.getPhoneNum()).set("activity_id", 1)
                .set("funds_nature_id",purchaseBean.getFundsNature()).save();

        int nPurchaseID = dao.get("id");
        ArrayList<PurchaseAttachFileBean> lstFile = mapAttachFile.get(purchaseBean.getPurchaseID());
        if (lstFile != null){
            for (int i=0; i<lstFile.size(); i++) {
                PurchaseFileAttachModel.dao.addAttachFile(nPurchaseID, lstFile.get(i));
            }
            lstFile.clear();
        }

        ArrayList<ProductBean> lstProduct = purchaseBean.getProductList();
        if (lstProduct != null){
            for (int i=0; i<lstProduct.size(); i++) {
                ProductModel.dao.addProduct(nPurchaseID, lstProduct.get(i));
            }
        }

        return ErrorCode.SUCCESS;
    }

    public String submitPurchase(String strPurchaseID) {
        PurchaseBean purchaseBean = getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            PurchaseActivityModel.dao.nextActivity(strPurchaseID);
        }
        return ErrorCode.SUCCESS;
    }

    public String cancelPurchase(String strPurchaseID) {
        PurchaseBean purchaseBean = getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            ArrayList<PurchaseAttachFileBean> attachFileBeen = purchaseBean.getAttachFileList();
            for (int i = 0; i< attachFileBeen.size(); i++) {
                mapAttachFile.remove(attachFileBeen.get(i));
            }
            PurchaseActivityModel.dao.removePurchase(strPurchaseID);
            lstPurchaseData.remove(purchaseBean);
        }
        return ErrorCode.SUCCESS;
    }

    public String agreePurchase(String strPurchaseID, PurchaseOpinionBean purchaseOpinionBean) {
        PurchaseBean purchaseBean = getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            PurchaseActivityModel.dao.nextActivity(strPurchaseID);
            purchaseBean.addOpinion(purchaseOpinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePurchase(String strPurchaseID, PurchaseOpinionBean purchaseOpinionBean) {
        PurchaseBean purchaseBean = getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            PurchaseActivityModel.dao.prevActivity(strPurchaseID);
            purchaseBean.addOpinion(purchaseOpinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public ArrayList<PurchaseBean> getPurchaseList() {
        ArrayList<PurchaseBean> lstPurchaseBean = new ArrayList<PurchaseBean>();
        String sql = "select p.purchase_uuid, p.code, p.name, p.funds_src, p.funds_nature_id, p.contacts, " +
                "p.phone_num, a.status from purchase p left join activity a on p.activity_id=a.id";
        List<PurchaseModel> purchaseList = dao.find(sql);
        for (int i=0; i<purchaseList.size(); i++) {
            PurchaseModel item = purchaseList.get(i);
            PurchaseBean purchaseBean = new PurchaseBean();
            purchaseBean.setPurchaseID(item.getStr("purchase_uuid"));
            purchaseBean.setPurCode(item.getStr("code"));
            purchaseBean.setFundsSrc(item.getStr("funds_src"));
            purchaseBean.setFundsNature(String.valueOf(item.getInt("funds_nature_id")));
            purchaseBean.setContacts(item.getStr("contacts"));
            purchaseBean.setPhoneNum(item.getStr("phone_num"));
            //purchaseBean.setS
            lstPurchaseBean.add(purchaseBean);
        }

        return lstPurchaseBean;
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
