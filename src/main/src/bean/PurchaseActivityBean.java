package bean;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PurchaseActivityBean {
    /** 定义采购工作流状态 */
    public static final int INITIALIZE                  = 1;          // 新创建项目
    public static final int ACC_APPROVE                 = 2;          // 单位会计审批
    public static final int DIR_APPROVE                 = 3;          // 分管股室局长审批
    public static final int FINANCIAL_APPROVE           = 4;          // 财政监管股室股审批
    public static final int FIN_BUREAU_APPROVE          = 5;          // 财政局审批
    public static final int SUBCONTRACTING              = 6;          // 未分包
    public static final int SUBCONTRACTED               = 7;          // 已分包
    public static final int PAID                        = 8;          // 已支付
}
