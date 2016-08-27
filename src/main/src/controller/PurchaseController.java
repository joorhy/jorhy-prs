package controller;

import bean.PackageBean;
import bean.PurchaseBean;
import com.jfinal.core.Controller;
import model.PacketModel;
import model.PurchasingModel;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PurchaseController extends Controller {
    public void toDivideItems() {
        String strPurchasingID = getPara("purchasing_id");
        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
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
        PackageBean packageBean = PacketModel.dao.getPackage(objBaseData.getString("packet_id"));
        if (packageBean == null) {
            packageBean = new PackageBean();
        }

        JSONObject objProducts = new JSONObject(strProducts);
        packageBean.parseBaseData(objBaseData);
        packageBean.parseProductData(objProducts);
        PacketModel.dao.savePackage(packageBean);

        setAttr("result", "success");
        renderJson();
    }

    public void submit() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingModel.dao.submitPurchasing(strPurchasingID);

        setAttr("result", "success");
        renderJson();
    }

    public void repacket() {
        String strPacketID = getPara("packet_id");
        PacketModel.dao.removePackage(strPacketID);

        setAttr("result", "success");
        renderJson();
    }
}
