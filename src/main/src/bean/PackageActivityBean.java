package bean;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PackageActivityBean {
    public static final int INITIALIZE                       = 0;          // 未验收
    public static final int TO_APPLY_PAY                     = 1;          // 未申请付款
    public static final int TO_PAY                           = 2;          // 未支付
    public static final int TO_REAPPLY_PAY                   = 3;          // 准备申请尾款
    public static final int TO_REPAY                         = 4;          // 准备支付尾款
    public static final int PAID                             = 5;          // 支付完成
}
