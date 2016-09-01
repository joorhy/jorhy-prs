package bean;

/**
 * Created by Joo on 2016/9/1.
 */
public class PackageActivityBean {
    public static final int INITIALIZE                       = 0;          // 未验收
    public static final int TO_APPLY_PAY                     = 1;          // 未申请付款
    public static final int TO_PAY                           = 2;          // 未支付
    public static final int TO_REAPPLY_PAY                   = 3;          // 准备申请尾款
    public static final int TO_REPAY                         = 4;          // 准备支付尾款
    public static final int PAID                             = 5;          // 支付完成

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
