package bean;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PackageActivityBean {
    public static final int INITIALIZE                       = 1;          // 未验收
    public static final int TO_APPLY_PAY                     = 2;          // 未申请付款
    public static final int TO_PAY                           = 3;          // 未支付
    public static final int TO_REAPPLY_PAY                   = 4;          // 准备申请尾款
    public static final int TO_REPAY                         = 5;          // 准备支付尾款
    public static final int PAID                             = 6;          // 支付完成
}
