package bean;

import java.util.ArrayList;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PacketBean {
    public String strPackID;                        // 包ID
    public String strPackName;                      // 包名
    public String strPackCode;                      // 采购文号
    public String strPurAddress;                    // 采购地点
    public String strPurDate;                       // 采购日期
    public String strExpertCount;                   // 抽取专家人数
    public String strPurMethod;                     // 采购方式
    public String strPublicity;                     // 采购需求公告
    public String strSupplier;                      // 中标供应商
    public String strAmount;                        // 中标金额

    public ArrayList<ProductBean> lstProduct = new ArrayList<ProductBean>();           // 采购项目
}
