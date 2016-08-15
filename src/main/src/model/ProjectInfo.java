package model;

import com.jfinal.plugin.activerecord.Model;
import bean.PrjData;

import java.util.ArrayList;

/**
 * Created by Joo on 2016/7/30.
 */
public class ProjectInfo extends Model<ProjectInfo> {
    public static final String SUCCESS = "success";
    public static final ProjectInfo dao = new ProjectInfo();

    private ArrayList<PrjData> lstPrjData = new ArrayList<PrjData>();
    public String addProject(PrjData data) {
        ProjectInfo info = null;//ProjectInfo.dao.findFirst("select * from cg_xm_jbxx where CG_XM_JBXXcol_CGHBH=" +
                //data.getPurcCode());
        if (info == null) {
            /*String strID = java.util.UUID.randomUUID().toString();
            //dao.set("CG_XM_JBXXcol_ID", strID).set("CG_XM_JBXXcol_CGHBH", data.getPurcCode())
                    .set("CG_XM_JBXXcol_ZJLY", data.getStrFundsSrc()).set("CG_XM_JBXXcol_ZJLYWJ",
                    data.getStrFundsNature()).save();*/
            lstPrjData.add(data);
        }

        return SUCCESS;
    }

    public String updateProject(PrjData data) {
        return SUCCESS;
    }

    public ArrayList<PrjData> getPrjDataList() {
        return lstPrjData;
    }

    public PrjData getPrjData(String strPurcCode) {
        for (int i=0; i<lstPrjData.size(); i++) {
            if (lstPrjData.get(i).getPurcCode() == strPurcCode) {
                return lstPrjData.get(i);
            }
        }
        return null;
    }
}
