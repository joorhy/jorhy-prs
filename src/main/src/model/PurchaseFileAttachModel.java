package model;

import bean.PurchaseAttachFileBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by JooLiu on 2016/9/9.
 */
public class PurchaseFileAttachModel extends Model<PurchaseFileAttachModel> {
    public static final PurchaseFileAttachModel dao = new PurchaseFileAttachModel();

    public void addAttachFile(int nPurchaseID, PurchaseAttachFileBean purchaseAttachFileBean) {
        PurchaseFileAttachModel purchaseFileAttachModel = new PurchaseFileAttachModel();
        purchaseFileAttachModel.set("uuid", purchaseAttachFileBean.strFileID)
                .set("name", purchaseAttachFileBean.strFileName).set("path", purchaseAttachFileBean.strFilePath)
                .set("size", purchaseAttachFileBean.fileSize).set("purchase_id", nPurchaseID).save();
    }

    public void removePurchaseAttachFiles(int nPurchaseID) {
        String sql = "select paf.id from purchase_attach_file paf where paf.purchase_id=" + nPurchaseID;
        List<PurchaseFileAttachModel> m = dao.find(sql);
        dao.deleteById(m);
    }

    public PurchaseAttachFileBean getAttachFileItem(String strFileID) {
        String sql = "select paf.*, p.purchase_uuid from purchase_attach_file paf left join purchase p " +
                "on paf.purchase_id=p.id where paf.uuid='" + strFileID + "'";

        PurchaseFileAttachModel purchaseFileAttachModel = dao.findFirst(sql);
        if (purchaseFileAttachModel != null) {
            PurchaseAttachFileBean purchaseAttachFileBean = new PurchaseAttachFileBean();
            purchaseAttachFileBean.strFileID = purchaseFileAttachModel.getStr("uuid");
            purchaseAttachFileBean.strFileName = purchaseFileAttachModel.getStr("name");
            purchaseAttachFileBean.strFilePath = purchaseFileAttachModel.getStr("path");
            purchaseAttachFileBean.fileSize = purchaseFileAttachModel.getInt("size");
            purchaseAttachFileBean.strPurchaseID = purchaseFileAttachModel.getStr("purchase_uuid");

            return purchaseAttachFileBean;
        }
        return null;
    }

    public void delAttachFile(String strFileID) {
        String sql = "select paf.id from purchase_attach_file paf where paf.uuid='" + strFileID + "'";
        PurchaseFileAttachModel purchaseFileAttachModel = dao.findFirst(sql);
        purchaseFileAttachModel.delete();
    }
}
