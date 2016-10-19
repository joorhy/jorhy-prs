package bean;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PurchaseActivityBean {
    /** 定义采购工作流状态 */
    public static final int INITIALIZE                  = 1;          // 新创建项目
    public static final int ACC_APPROVE                 = 2;          // 单位会计审批
    public static final int LEAD_APPROVE                 = 3;          // 单位分管领导审批
    public static final int DIR_APPROVE                 = 4;          // 单位领导审批
    public static final int SECTOR_APPROVE              = 5;          // 分管股室审批
    public static final int FINANCIAL_APPROVE           = 6;          // 财政监管股室股审批
    public static final int FIN_BUREAU_APPROVE          = 7;          // 财政局审批
    public static final int PURCHASE                    = 8;          // 未分包
    public static final int PAYMENT                     = 9;          // 未分包
}
