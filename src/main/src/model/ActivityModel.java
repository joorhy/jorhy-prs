package model;

import bean.ActivityBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class ActivityModel extends Model<PackageModel> {
    public static final ActivityModel dao = new ActivityModel();

    private Map<String, ActivityBean> mapActivity = new HashMap<String, ActivityBean>();

    public void addPurchase(String strPurchaseID) {
        ActivityBean activityBean = mapActivity.get(strPurchaseID);
        if (activityBean == null) {
            activityBean = new ActivityBean();
            activityBean.setActivityStatus(ActivityBean.INITIALIZE);
            mapActivity.put(strPurchaseID, activityBean);
        }
    }

    public void removePurchase(String strPurchaseID) {
        ActivityBean activityBean = mapActivity.get(strPurchaseID);
        if (activityBean != null) {
            mapActivity.remove(activityBean);
        }
    }

    public int getActivityStatus(String strPurchaseID) {
        ActivityBean activityBean = mapActivity.get(strPurchaseID);
        if (activityBean != null) {
            return activityBean.getActivityStatus();
        }
        return 100;
    }

    public void nextActivity(String strPurchaseID) {
        ActivityBean activityBean = mapActivity.get(strPurchaseID);
        if (activityBean != null) {
            activityBean.setNextStatus();
        }
    }

    public void prevActivity(String strPurchaseID) {
        ActivityBean activityBean = mapActivity.get(strPurchaseID);
        if (activityBean != null) {
            activityBean.setPrevStatus();
        }
    }
}
