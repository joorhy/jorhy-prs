package model;

import bean.ProductBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PackageProductModel extends Model<PackageProductModel> {
    public static final PackageProductModel dao = new PackageProductModel();

    public void addProduct(int nPackageID, ProductBean productBean) {
        String sql = "select id from package_product p where p.uuid='" + productBean.strProductID + "'";

        PackageProductModel packageProductModel = dao.findFirst(sql);
        if (packageProductModel != null) {
            packageProductModel.set("uuid", productBean.strProductID).set("type", productBean.strPrjType)
                    .set("name", productBean.strPrjName).set("count", productBean.nPrjCount)
                    .set("package_id", nPackageID).set("spec", productBean.strPrjSpec)
                    .set("pre_price", productBean.fPrjPrePrice).set("param", productBean.strPrjParam)
                    .set("packaged_count", productBean.nPackagedCount).update();
        } else {
            packageProductModel = new PackageProductModel();
            packageProductModel.set("uuid", productBean.strProductID).set("type", productBean.strPrjType)
                    .set("name", productBean.strPrjName).set("count", productBean.nPrjCount)
                    .set("package_id", nPackageID).set("spec", productBean.strPrjSpec)
                    .set("pre_price", productBean.fPrjPrePrice).set("param", productBean.strPrjParam)
                    .set("packaged_count", productBean.nPackagedCount).save();
        }
    }

    public void removePurchaseProducts(int nPurchaseID) {
        String url = "select p.id from product p where p.purchase_id=" + nPurchaseID;
        List<PackageProductModel> m = dao.find(url);
        dao.deleteById(m);
    }

    public void updatePackagedCount(String strPurchaseID, int packagedCount) {
        String sql = "select p.id from product p where p.purchase_id=" + strPurchaseID;
        ProductModel productModel = ProductModel.dao.findFirst(sql);
        if (productModel != null) {
            productModel.set("packaged_count", packagedCount).update();
        }
    }
}
