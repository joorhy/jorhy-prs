package model;

import bean.PackageAttachFileBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PackageFileAttachModel extends Model<PackageFileAttachModel> {
    public static final PackageFileAttachModel dao = new PackageFileAttachModel();

    public void addAttachFile(int nPackageID, String strFileType, PackageAttachFileBean packageAttachFileBean) {
        PackageFileAttachModel packageFileAttachModel = new PackageFileAttachModel();
        packageFileAttachModel.set("uuid", packageAttachFileBean.strFileID)
                .set("name", packageAttachFileBean.strFileName).set("path", packageAttachFileBean.strFilePath)
                .set("size", packageAttachFileBean.fileSize).set("package_id", nPackageID)
                .set("type", strFileType).save();
    }

    public void removePackageAttachFiles(int nPackageID) {
        String url = "select paf.id from package_attach_file paf where paf.package_id='" + nPackageID + "'";
        List<PackageFileAttachModel> m = dao.find(url);
        dao.deleteById(m);
    }

    public void removeAttachFile(String strFileID) {
        String url = "select id from package_attach_file paf where paf.uuid='" + strFileID + "'";
        PackageFileAttachModel packageFileAttachModel = dao.findFirst(url);
        if (packageFileAttachModel != null) {
            packageFileAttachModel.delete();
        }
    }

    public PackageAttachFileBean getAttachFileItem(String strFileID) {
        PackageAttachFileBean packageAttachFileBean = null;
        String url = "select paf.*,p.package_uuid from package_attach_file paf left join package p on " +
                "paf.package_id=p.id where paf.uuid='" + strFileID + "'";
        PackageFileAttachModel packageFileAttachModel = dao.findFirst(url);
        if (packageFileAttachModel != null) {
            packageAttachFileBean = new PackageAttachFileBean();
            packageAttachFileBean.strFileID = packageFileAttachModel.getStr("uuid");
            packageAttachFileBean.strFileName = packageFileAttachModel.getStr("name");
            packageAttachFileBean.strFilePath = packageFileAttachModel.getStr("path");
            packageAttachFileBean.fileSize = packageFileAttachModel.getInt("size");
            packageAttachFileBean.strPackageID = packageFileAttachModel.getStr("package_uuid");
        }
        return packageAttachFileBean;
    }
}
