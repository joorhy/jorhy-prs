package model;

import bean.PurchaseActivityBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class PurchaseActivityModel extends Model<PackageModel> {
    public static final PurchaseActivityModel dao = new PurchaseActivityModel();

    private Map<String, PurchaseActivityBean> mapActivity = new HashMap<String, PurchaseActivityBean>();

    public void addPurchase(String strPurchaseID) {
        PurchaseActivityBean purchaseActivityBean = mapActivity.get(strPurchaseID);
        if (purchaseActivityBean == null) {
            purchaseActivityBean = new PurchaseActivityBean();
            purchaseActivityBean.setActivityStatus(PurchaseActivityBean.INITIALIZE);
            mapActivity.put(strPurchaseID, purchaseActivityBean);
        }
    }

    public void removePurchase(String strPurchaseID) {
        PurchaseActivityBean purchaseActivityBean = mapActivity.get(strPurchaseID);
        if (purchaseActivityBean != null) {
            mapActivity.remove(purchaseActivityBean);
        }
    }

    public int getActivityStatus(String strPurchaseID) {
        PurchaseActivityBean purchaseActivityBean = mapActivity.get(strPurchaseID);
        if (purchaseActivityBean != null) {
            return purchaseActivityBean.getActivityStatus();
        }
        return 100;
    }

    public void nextActivity(String strPurchaseID) {
        PurchaseActivityBean purchaseActivityBean = mapActivity.get(strPurchaseID);
        if (purchaseActivityBean != null) {
            purchaseActivityBean.setNextStatus();
        }
    }

    public void prevActivity(String strPurchaseID) {
        PurchaseActivityBean purchaseActivityBean = mapActivity.get(strPurchaseID);
        if (purchaseActivityBean != null) {
            purchaseActivityBean.setPrevStatus();
        }
    }
}
