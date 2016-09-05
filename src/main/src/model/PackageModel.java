package model;

import bean.*;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PackageModel extends Model<PackageModel> {
    public static final PackageModel dao = new PackageModel();

    private Map<String,ArrayList<PackageAttachFileBean>> mapAttachFile = new HashMap<String, ArrayList<PackageAttachFileBean>>();
	/** 分包 */
    private ArrayList<PackageBean> lstPackage = new ArrayList<PackageBean>();      // 已分包项目

    public void savePackage(PackageBean packageBean) {
        for (int i=0; i<lstPackage.size(); i++) {
            if (lstPackage.get(i).getPackageID().equals(packageBean.getPackageID())) {
                packageBean = lstPackage.get(i);
                lstPackage.remove(packageBean);
                break;
            }
        }

        PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(packageBean.strPurchaseID);
        if (purchaseBean != null) {
            for (int i = 0; i< packageBean.lstProduct.size(); i++) {
                ProductBean prjItem = packageBean.lstProduct.get(i);
                for (int j = 0; j < purchaseBean.getProductList().size(); j++) {
                    if (purchaseBean.getProductList().get(j).strProductID.equals(prjItem.strProductID)) {
                        purchaseBean.getProductList().get(j).nPackagedCount = prjItem.nPrjCount;
                        break;
                    }
                }
            }
            ArrayList<PackageAttachFileBean> lstFile = mapAttachFile.get(packageBean.getPackageID());
            if (lstFile != null){
                for (int i=0; i<lstFile.size(); i++) {
                    packageBean.addAttachFile(lstFile.get(i));
                }
                lstFile.clear();
            }
            PackageActivityModel.dao.addPackage(packageBean.getPackageID());
            lstPackage.add(packageBean);
        }
    }

    public void removePackage(String strPackageID) {
        for (int i=0; i<lstPackage.size(); i++) {
            PackageBean packageBean = lstPackage.get(i);
            if (packageBean.getPackageID().equals(strPackageID)) {
                ProductBean prjItem = packageBean.lstProduct.get(i);
                PurchaseBean purchaseBean = PurchaseModel.dao.getPurchase(packageBean.strPurchaseID);
                for (int j = 0; j < purchaseBean.getProductList().size(); j++) {
                    if (purchaseBean.getProductList().get(j).strProductID.equals(prjItem.strProductID)) {
                        purchaseBean.getProductList().get(j).nPackagedCount = 0;
                        break;
                    }
                }
                lstPackage.remove(packageBean);
                break;
            }
        }
    }

    public String agreePackage(String strPackageID, PackageOpinionBean packageOpinionBean) {
        PackageBean packageBean = getPackage(strPackageID);
        if (packageBean != null) {
            PackageActivityModel.dao.nextActivity(strPackageID);
            packageBean.addOpinion(packageOpinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public String disagreePackage(String strPackageID, PackageOpinionBean packageOpinionBean) {
        PackageBean packageBean = getPackage(strPackageID);
        if (packageBean != null) {
            PackageActivityModel.dao.prevActivity(strPackageID);
            packageBean.addOpinion(packageOpinionBean);
        }
        return ErrorCode.SUCCESS;
    }

    public PackageBean getPackage(String strPackageID) {
        for (int i=0; i<lstPackage.size(); i++) {
            PackageBean packageBean = lstPackage.get(i);
            if (packageBean.getPackageID().equals(strPackageID)) {
                return packageBean;
            }
        }
        return null;
    }

    public ArrayList<Map<String, String>> getPackageList(String strPurchaseID) {
        ArrayList<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        for (int j = 0; j< lstPackage.size(); j++) {
            Map<String, String> node= new HashMap<String, String>();
            PackageBean item = lstPackage.get(j);
            if (item.strPurchaseID.equals(strPurchaseID)) {
                node.put("id", item.getPackageID());
                node.put("text", "第" + String.valueOf(lst.size() + 1) + "包");
                node.put("iconCls", "icon-cut");
                node.put("type", "package");
                lst.add(node);
            }
        }
        return lst;
    }

    public void addAttachFile(String strPackageID, PackageAttachFileBean item) {
        ArrayList<PackageAttachFileBean> lst = mapAttachFile.get(strPackageID);
        if (lst == null) {
            lst = new ArrayList<PackageAttachFileBean>();
            mapAttachFile.put(strPackageID, lst);
        }
        lst.add(item);
    }
}
