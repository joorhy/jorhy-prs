package model;

import bean.ActivityBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class ActivityModel extends Model<PacketModel> {
    public static final ActivityModel dao = new ActivityModel();

    private Map<String, ActivityBean> mapActivity = new HashMap<String, ActivityBean>();

    public void addPurchasing(String strPurchasingID) {
        ActivityBean activityBean = mapActivity.get(strPurchasingID);
        if (activityBean == null) {
            activityBean = new ActivityBean();
            activityBean.setActivityStatus(ActivityBean.INITIALIZE);
            mapActivity.put(strPurchasingID, activityBean);
        }
    }

    public void removePurchasing(String strPurchasingID) {
        ActivityBean activityBean = mapActivity.get(strPurchasingID);
        if (activityBean != null) {
            mapActivity.remove(activityBean);
        }
    }

    public int getActivityStatus(String strPurchasingID) {
        ActivityBean activityBean = mapActivity.get(strPurchasingID);
        if (activityBean != null) {
            return activityBean.getActivityStatus();
        }
        return 100;
    }

    public void nextActivity(String strPurchasingID) {
        ActivityBean activityBean = mapActivity.get(strPurchasingID);
        if (activityBean != null) {
            activityBean.setNextStatus();
        }
    }

    public void prevActivity(String strPurchasingID) {
        ActivityBean activityBean = mapActivity.get(strPurchasingID);
        if (activityBean != null) {
            activityBean.setPrevStatus();
        }
    }
}
