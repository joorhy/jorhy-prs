package model;

import bean.ProductBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JooLiu on 2016/9/9.
 */
public class ProductModel extends Model<ProductModel> {
    public static final ProductModel dao = new ProductModel();

    public void addProduct(int nPurchaseID, ProductBean productBean) {
        String url = "select id from product p where p.uuid='" + productBean.strProductID + "'";
        ProductModel productModel = ProductModel.dao.findFirst(url);
        if (productModel != null) {
            productModel.set("uuid", productBean.strProductID).set("type", productBean.strPrjType)
                    .set("name", productBean.strPrjName).set("count", productBean.nPrjCount)
                    .set("purchase_id", nPurchaseID).set("spec", productBean.strPrjSpec)
                    .set("price", productBean.fPrjPrice).set("pre_price", productBean.fPrjPrePrice)
                    .set("param", productBean.strPrjParam).set("packaged_count", productBean.nPackagedCount)
                    .update();
        } else {
            productModel = new ProductModel();
            productModel.set("uuid", productBean.strProductID).set("type", productBean.strPrjType)
                    .set("name", productBean.strPrjName).set("count", productBean.nPrjCount)
                    .set("purchase_id", nPurchaseID).set("spec", productBean.strPrjSpec)
                    .set("price", productBean.fPrjPrice).set("pre_price", productBean.fPrjPrePrice)
                    .set("param", productBean.strPrjParam).set("packaged_count", productBean.nPackagedCount)
                    .save();
        }
    }

    public void removePurchaseProducts(int nPurchaseID) {
        String url = "select p.id from product p where p.purchase_id=" + nPurchaseID;
        List<ProductModel> m = dao.find(url);
        dao.deleteById(m);
    }

    public void updatePackagedCount(String strProductID, int packagedCount) {
        String sql = "select p.id, p.packaged_count from product p where p.uuid='" + strProductID + "'";
        ProductModel productModel = ProductModel.dao.findFirst(sql);
        if (productModel != null) {
            int nPackagedCount = productModel.get("packaged_count");
            productModel.set("packaged_count", nPackagedCount + packagedCount).update();
        }
    }

    public ArrayList<Map<String, String>> getProductItems(String strPurchaseID, String strProductType) {
        String sql = "select p.* from product p left join purchase pur on p.purchase_id=pur.id where " +
                "pur.purchase_uuid='" + strPurchaseID +  "' and p.type='" + strProductType + "'";

        List<ProductModel> productModelList = dao.find(sql);
        ArrayList<Map<String, String>> productArray = new ArrayList<Map<String, String>>();
        for (int i = 0; i < productModelList.size(); i++) {
            ProductModel item = productModelList.get(i);
            Map<String, String> obj = new HashMap<String, String>();
            obj.put("project_id", item.getStr("uuid"));
            obj.put("product_type", item.getStr("type"));
            obj.put("prj_name", item.getStr("name"));
            obj.put("prj_count", String.valueOf(item.getInt("count")));
            obj.put("prj_price", String.valueOf(item.getDouble("price")));
            obj.put("prj_pre_price", String.valueOf(item.getDouble("pre_price")));
            obj.put("prj_param", item.getStr("param"));
            obj.put("prj_spec", item.getStr("spec"));
            productArray.add(obj);
        }
        return productArray;
    }

    public ArrayList<Map<String, String>> getToDivideItems(String strPurchaseID) {
        String sql = "select p.* from product p left join purchase pur on p.purchase_id=pur.id where " +
                "pur.purchase_uuid='" + strPurchaseID +  "'";
        List<ProductModel> productModelList = dao.find(sql);
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int i = 0; i < productModelList.size(); i++) {
            ProductModel item = productModelList.get(i);
            if (item.getInt("packaged_count") < item.getInt("count")) {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("project_id", item.getStr("uuid"));
                obj.put("product_type", item.getStr("type"));
                obj.put("prj_name", item.getStr("name"));
                obj.put("prj_package_count", String.valueOf(item.getInt("count") - item.getInt("packaged_count")));
                obj.put("prj_count", String.valueOf(item.getInt("count") - item.getInt("packaged_count")));
                obj.put("prj_price", String.valueOf(item.getDouble("price")));
                obj.put("prj_pre_price", String.valueOf(item.getDouble("pre_price")));
                obj.put("prj_param", item.getStr("param"));
                obj.put("prj_spec", item.getStr("spec"));
                obj.put("prj_select", "selected");
                lst.add(obj);
            }
        }
        return lst;
    }
}
