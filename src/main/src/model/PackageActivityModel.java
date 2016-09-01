package model;

import bean.PackageActivityBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joo on 2016/9/1.
 */
public class PackageActivityModel {
    public static final PackageActivityModel dao = new PackageActivityModel();

    private Map<String, PackageActivityBean> mapActivity = new HashMap<String, PackageActivityBean>();

    public void addPackage(String strPackageID) {
        PackageActivityBean packageActivityBean = mapActivity.get(strPackageID);
        if (packageActivityBean == null) {
            packageActivityBean = new PackageActivityBean();
            packageActivityBean.setActivityStatus(PackageActivityBean.INITIALIZE);
            mapActivity.put(strPackageID, packageActivityBean);
        }
    }

    public void removePackage(String strPackageID) {
        PackageActivityBean packageActivityBean = mapActivity.get(strPackageID);
        if (packageActivityBean != null) {
            mapActivity.remove(packageActivityBean);
        }
    }

    public int getActivityStatus(String strPackageID) {
        PackageActivityBean packageActivityBean = mapActivity.get(strPackageID);
        if (packageActivityBean != null) {
            return packageActivityBean.getActivityStatus();
        }
        return 100;
    }

    public void nextActivity(String strPackageID) {
        PackageActivityBean packageActivityBean = mapActivity.get(strPackageID);
        if (packageActivityBean != null) {
            packageActivityBean.setNextStatus();
        }
    }

    public void prevActivity(String strPackageID) {
        PackageActivityBean packageActivityBean = mapActivity.get(strPackageID);
        if (packageActivityBean != null) {
            packageActivityBean.setPrevStatus();
        }
    }
}
