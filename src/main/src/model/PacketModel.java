package model;

import bean.PackageBean;
import bean.ProductBean;
import bean.PurchaseBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PacketModel extends Model<PacketModel> {
    public static final PacketModel dao = new PacketModel();
	
	/** 分包 */
    private ArrayList<PackageBean> lstPacket = new ArrayList<PackageBean>();      // 已分包项目

    public void savePackage(PackageBean packageBean) {
        for (int i=0; i<lstPacket.size(); i++) {
            if (lstPacket.get(i).strPackID.equals(packageBean.strPackID)) {
                packageBean = lstPacket.get(i);
                lstPacket.remove(packageBean);
                break;
            }
        }

        PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(packageBean.strPurchasingID);
        if (purchaseBean != null) {
            for (int i = 0; i< packageBean.lstProduct.size(); i++) {
                ProductBean prjItem = packageBean.lstProduct.get(i);
                for (int j = 0; j < purchaseBean.getProductList().size(); j++) {
                    if (purchaseBean.getProductList().get(j).strProductID.equals(prjItem.strProductID)) {
                        purchaseBean.getProductList().get(j).nPackedCount = prjItem.nPrjCount;
                        break;
                    }
                }
            }
            lstPacket.add(packageBean);
        }
    }

    public void removePackage(String strPacketID) {
        for (int i=0; i<lstPacket.size(); i++) {
            PackageBean packageBean = lstPacket.get(i);
            if (packageBean.strPackID.equals(strPacketID)) {
                ProductBean prjItem = packageBean.lstProduct.get(i);
                PurchaseBean purchaseBean = PurchasingModel.dao.getPurchasing(packageBean.strPurchasingID);
                for (int j = 0; j < purchaseBean.getProductList().size(); j++) {
                    if (purchaseBean.getProductList().get(j).strProductID.equals(prjItem.strProductID)) {
                        purchaseBean.getProductList().get(j).nPackedCount = 0;
                        break;
                    }
                }
                lstPacket.remove(packageBean);
                break;
            }
        }
    }

    public PackageBean getPackage(String strPacketID) {
        for (int i=0; i<lstPacket.size(); i++) {
            PackageBean packageBean = lstPacket.get(i);
            if (packageBean.strPackID.equals(strPacketID)) {
                return packageBean;
            }
        }
        return null;
    }

    public ArrayList<Map<String, String>> getPackageList(String strPurchaseID) {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int j = 0; j< lstPacket.size(); j++) {
            Map<String, String> node= new HashMap<String, String>();
            PackageBean item = lstPacket.get(j);
            if (item.strPurchasingID.equals(strPurchaseID)) {
                node.put("id", item.strPackID);
                node.put("text", "第" + String.valueOf(lst.size() + 1) + "包");
                node.put("iconCls", "icon-cut");
                node.put("type", "packet");
                lst.add(node);
            }
        }
        return lst;
    }
}
