package model;

import bean.ErrorCode;
import bean.PurchaseOpinionBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class ApproveRecordModel extends Model<ApproveRecordModel> {
    public static final ApproveRecordModel dao = new ApproveRecordModel();

    public String addApproveRecord(int purchaseID, int purchaseActivityID, PurchaseOpinionBean purchaseOpinionBean) {
        String sql = "select id from approve_record r where r.user_id=" + purchaseOpinionBean.userID +
                     "and r.purchase_id=" + purchaseID;
        ApproveRecordModel approveRecordModel = ApproveRecordModel.dao.findFirst(sql);
        if (approveRecordModel != null) {
            return ErrorCode.EXIST;
        }
        ApproveRecordModel.dao.set("user_id", purchaseOpinionBean.userID).set("purchase_id", purchaseID)
                .set("purchase_activity_id", purchaseActivityID).set("opinion", purchaseOpinionBean.strOpinion)
                .set("approve_date", purchaseOpinionBean.strApproveDate).save();

        return ErrorCode.SUCCESS;
    }

    public String addRejectRecord(int purchaseID, int purchaseActivityID, PurchaseOpinionBean purchaseOpinionBean) {
        ApproveRecordModel.dao.set("user_id", purchaseOpinionBean.userID).set("purchase_id", purchaseID)
                .set("purchase_activity_id", purchaseActivityID).set("opinion", purchaseOpinionBean.strOpinion)
                .set("approve_date", purchaseOpinionBean.strApproveDate).save();

        return ErrorCode.SUCCESS;
    }
}
