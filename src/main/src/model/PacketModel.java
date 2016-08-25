package model;

import bean.PacketBean;
import bean.ProductBean;
import bean.PurchasingBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PacketModel extends Model<PacketModel> {
    public static final PacketModel dao = new PacketModel();
	
	/** 分包 */
    private ArrayList<PacketBean> lstPacket = new ArrayList<PacketBean>();      // 已分包项目

    public ArrayList<PacketBean> getPacketList() {
        return lstPacket;
    }

    public void savePacket(PacketBean packetBean) {
        for (int i=0; i<lstPacket.size(); i++) {
            if (lstPacket.get(i).strPackID.equals(packetBean.strPackID)) {
                packetBean = lstPacket.get(i);
                lstPacket.remove(packetBean);
                break;
            }
        }

        PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(packetBean.strPurchasingID);
        if (purchasingBean != null) {
            for (int i=0; i<packetBean.lstProduct.size(); i++) {
                ProductBean prjItem = packetBean.lstProduct.get(i);
                for (int j = 0; j < purchasingBean.getProductList().size(); j++) {
                    if (purchasingBean.getProductList().get(j).strProductID.equals(prjItem.strProductID)) {
                        purchasingBean.getProductList().get(j).nPackedCount = prjItem.nPrjCount;
                        break;
                    }
                }
            }
            lstPacket.add(packetBean);
        }
    }

    public void removePacket(String strPacketID) {
        for (int i=0; i<lstPacket.size(); i++) {
            PacketBean packetBean = lstPacket.get(i);
            if (packetBean.strPackID.equals(strPacketID)) {
                ProductBean prjItem = packetBean.lstProduct.get(i);
                PurchasingBean purchasingBean = PurchasingModel.dao.getPurchasing(packetBean.strPurchasingID);
                for (int j = 0; j < purchasingBean.getProductList().size(); j++) {
                    if (purchasingBean.getProductList().get(j).strProductID.equals(prjItem.strProductID)) {
                        purchasingBean.getProductList().get(j).nPackedCount = 0;
                        break;
                    }
                }
                lstPacket.remove(packetBean);
                break;
            }
        }
    }

    public PacketBean getPacket(String strPacketID) {
        for (int i=0; i<lstPacket.size(); i++) {
            PacketBean packetBean = lstPacket.get(i);
            if (packetBean.strPackID.equals(strPacketID)) {
                return packetBean;
            }
        }
        return null;
    }
}
