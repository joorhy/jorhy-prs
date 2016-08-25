package controller;

import bean.PacketBean;
import bean.PurchaseLeftMenu;
import bean.PurchasingBean;
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
    // 页面初始化
    public void index() {
        renderJsp("/jsp/purchase/purchase.jsp");
    }

    public void purchaseTree() {
        renderText(PurchaseLeftMenu.getTree().toString());
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

    public void save() {
        String strBaseData = getPara("base");
        String strProducts = getPara("products");

        JSONObject objBaseData = new JSONObject(strBaseData);
        PacketBean packetBean = PacketModel.dao.getPacket(objBaseData.getString("packet_id"));
        if (packetBean == null) {
            packetBean = new PacketBean();
        }

        JSONObject objProducts = new JSONObject(strProducts);
        packetBean.parseBaseData(objBaseData);
        packetBean.parseProductData(objProducts);
        PacketModel.dao.savePacket(packetBean);

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
        PacketModel.dao.removePacket(strPacketID);

        setAttr("result", "success");
        renderJson();
    }
}
