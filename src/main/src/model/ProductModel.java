package model;

import bean.ProductBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by JooLiu on 2016/9/9.
 */
public class ProductModel extends Model<ProductModel> {
    public static final ProductModel dao = new ProductModel();

    public void addProduct(int nPurchaseID, ProductBean productBean) {
        dao.set("uuid", productBean.strProductID).set("type", productBean.strPrjType)
                .set("name", productBean.strPrjName).set("count", productBean.nPrjCount)
                .set("purchase_id", nPurchaseID).set("spec", productBean.strPrjSpec)
                .set("pre_price", productBean.fPrjPrePrice).set("param", productBean.strPrjParam)
                .set("packaged_count", productBean.nPackagedCount).save();
    }

    public void removePurchaseAttachFiles(int nPurchaseID) {
        String url = "select p.id from product p where p.purchase_id=" + nPurchaseID;
        List<ProductModel> m = dao.find(url);
        dao.deleteById(m);
    }
}
