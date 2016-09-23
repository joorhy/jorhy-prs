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
        dao.set("uuid", purchaseAttachFileBean.strFileID).set("name", purchaseAttachFileBean.strFileName)
                .set("path", purchaseAttachFileBean.strFilePath).set("size", purchaseAttachFileBean.fileSize)
                .set("purchase_id", nPurchaseID).save();
    }

    public void removePurchaseAttachFiles(int nPurchaseID) {
        String url = "select paf.id from purchase_attach_file paf where paf.purchase_id=" + nPurchaseID;
        List<PurchaseFileAttachModel> m = dao.find(url);
        dao.deleteById(m);
    }


}
