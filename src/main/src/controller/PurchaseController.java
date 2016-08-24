package controller;

import bean.PurchasingBean;
import com.jfinal.core.Controller;
import model.PurchasingModel;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PurchaseController extends Controller {
    // 页面初始化
    public void index() {
        renderJsp("/jsp/purchase/purchase.jsp");
    }

    public void purchaseTree() {
        renderText(PurchasingBean.getPurchaseTree().toString());
    }

    public void toDivideItems() {
        String strPurchasingID = getPara("purchasing_id");
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(strPurchasingID);
        if (purchasingBean != null) {
            setAttr("rows", purchasingBean.getJSONToDivideItems());
            setAttr("total", purchasingBean.getJSONToDivideItems().size());
        } else {
            setAttr("rows", new ArrayList<Map<String, String>>());
            setAttr("total", 0);
        }
        renderJson();
    }

    public void dividedItems() {

    }

    public void save() {
        String strBaseData = getPara("base");
        String strProducts = getPara("products");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(objBaseData.getString("purchasing_id"));
        if (purchasingBean != null) {
            JSONObject objProducts = new JSONObject(strProducts);
            purchasingBean.savePacket(objBaseData, objProducts);
            PurchasingModel.dao.savePurchasing(purchasingBean);
        }
        setAttr("result", "success");
        renderJson();
    }
}
