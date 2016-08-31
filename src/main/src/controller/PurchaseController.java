package controller;

import bean.PackageBean;
import bean.PurchaseBean;
import com.jfinal.core.Controller;
import model.PackageModel;
import model.PurchaseModel;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PurchaseController extends Controller {
    public void toDivideItems() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(strPurchaseID);
        if (purchaseBean != null) {
            setAttr("rows", purchaseBean.getJSONToDivideItems());
            setAttr("total", purchaseBean.getJSONToDivideItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void save() {
        String strBaseData = getPara("base");
        String strProducts = getPara("products");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PackageBean packageBean = PackageModel.dao.getPackage(objBaseData.getString("packet_id"));
        if (packageBean == null) {
            packageBean = new PackageBean();
        }

        JSONObject objProducts = new JSONObject(strProducts);
        packageBean.parseBaseData(objBaseData);
        packageBean.parseProductData(objProducts);
        PackageModel.dao.savePackage(packageBean);

        setAttr("result", "success");
        renderJson();
    }

    public void submit() {
        String strPurchaseID = getPara("purchase_id");
        PurchaseModel.dao.submitPurchase(strPurchaseID);

        setAttr("result", "success");
        renderJson();
    }

    public void repacket() {
        String strPacketID = getPara("packet_id");
        PackageModel.dao.removePackage(strPacketID);

        setAttr("result", "success");
        renderJson();
    }
}
