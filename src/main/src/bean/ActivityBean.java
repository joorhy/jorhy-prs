package bean;

/**
 * Created by JooLiu on 2016/8/25.
 */
public class ActivityBean {
    /** 定义采购工作流状态 */
    public static final int INITIALIZE                  = 0;          // 新创建项目
    public static final int ACC_APPROVE                 = 1;          // 单位会计审批
    public static final int DIR_APPROVE                 = 2;          // 分管股室局长审批
    public static final int FINANCIAL_APPROVE           = 3;          // 财政监管股室股审批
    public static final int FIN_BUREAU_APPROVE          = 4;          // 财政局审批
    public static final int SUBCONTRACTING              = 5;          // 未分包
    public static final int SUBCONTRACTED               = 6;          // 已分包
    public static final int ACCEPTED                      = 7;          // 已验收
    public static final int PAID                        = 8;          // 已支付
    public static final int ACC_APPROVE_FAILED          = 101;        // 单位会计审批失败
    public static final int DIR_APPROVE_FAILED          = 102;        // 分管股室局长审批失败
    public static final int FINANCIAL_APPROVE_FAILED    = 103;        // 财政监管股室股审批失败
    public static final int FIN_BUREAU_APPROVE_FAILED   = 104;        // 财政局审批失败

    /** 定义成员变量 */
    private int nActivityStatus;

    public int getActivityStatus() {
        return nActivityStatus;
    }

    public void setActivityStatus(int nStatus) {
        nActivityStatus = nStatus;
    }

    public void setNextStatus() {
        nActivityStatus += 1;
    }

    public void setPrevStatus() {
        nActivityStatus += 100;
    }
}
