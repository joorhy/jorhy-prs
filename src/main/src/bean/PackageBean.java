package bean;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JooLiu on 2016/8/23.
 */
public class PackageBean {
    private String strPackID;                        // 包ID
    private String strPackName;                      // 包名
    private String strPackCode;                      // 采购文号
    private String strPurAddress;                    // 采购地点
    private String strPurDate;                       // 采购日期
    private Integer nExpertCount;                   // 抽取专家人数
    private String strPurMethod;                     // 采购方式
    private Boolean isPublicity;                     // 采购需求公告
    private String strSupplier;                      // 中标供应商
    private Double fAmount;                        // 中标金额
    private String strPurchaseID;                    // 采购函ID
    private int nPackageCount;

    public ArrayList<ProductBean> lstProduct = new ArrayList<ProductBean>();           // 采购项目
    /**
     * 合同附件
     */
    private ArrayList<PackageAttachFileBean> lstAttachFile = new ArrayList<PackageAttachFileBean>();  // 附件
    /** 审批流状态及内容 */
    private ArrayList<PackageOpinionBean> lstOpinion = new ArrayList<PackageOpinionBean>();           // 审批意见

    /** 定义 Model 接口 */
    public String getPackageID() {
        return strPackID;
    }

    public void setPackageID(String strPackageID) {
        this.strPackID = strPackageID;
    }

    public void setPackageName(String strPackName) {
        this.strPackName = strPackName;
    }

    public void setPackageCode(String strPackageCode) {
        this.strPackCode = strPackageCode;
    }

    public void setPurAddress(String strPurAddress) {
        this.strPurAddress = strPurAddress;
    }

    public void setPurDate(String strPurDate) {
        this.strPurDate = strPurDate;
    }

    public void setExpertCount(int nExpertCount) {
        this.nExpertCount = nExpertCount;
    }

    public void setPurMethod(String strPurMethod) {
        this.strPurMethod = strPurMethod;
    }

    public void setPublicity(Boolean isPublicity) {
        this.isPublicity = isPublicity;
    }

    public void setSupplier(String strSupplier) {
        this.strSupplier = strSupplier;
    }

    public void setAmount(Double fAmount) {
        this.fAmount = fAmount;
    }

    public void setPurchaseID (String strPurchaseID) {
        this.strPurchaseID = strPurchaseID;
    }

    public void setPackageCount (int nPackageCount) {
        this.nPackageCount = nPackageCount;
    }
}
