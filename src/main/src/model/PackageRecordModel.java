package model;

import bean.ErrorCode;
import bean.PackageOpinionBean;
import bean.PurchaseOpinionBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PackageRecordModel extends Model<PackageRecordModel> {
    public static final PackageRecordModel dao = new PackageRecordModel();

    public String addApproveRecord(int purchaseID, int purchaseActivityID, PackageOpinionBean packageOpinionBean) {
        return ErrorCode.SUCCESS;
    }

    public String addRejectRecord(int purchaseID, int purchaseActivityID, PackageOpinionBean packageOpinionBean) {
        return ErrorCode.SUCCESS;
    }
}
