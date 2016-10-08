package model;

import bean.PackageAttachFileBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by JooLiu on 2016/9/23.
 */
public class PackageFileAttachModel extends Model<PackageFileAttachModel> {
    public static final PackageFileAttachModel dao = new PackageFileAttachModel();

    public void addAttachFile(int nPackageID, PackageAttachFileBean packageAttachFileBean) {
        PackageFileAttachModel packageFileAttachModel = new PackageFileAttachModel();
        packageFileAttachModel.set("uuid", packageAttachFileBean.strFileID)
                .set("name", packageAttachFileBean.strFileName).set("path", packageAttachFileBean.strFilePath)
                .set("size", packageAttachFileBean.fileSize).set("package_id", nPackageID).save();
    }

    public void removePackageAttachFiles(int nPackageID) {
        String url = "select paf.id from package_attach_file paf where paf.package_id=" + nPackageID;
        List<PackageFileAttachModel> m = dao.find(url);
        dao.deleteById(m);
    }

    public void removeAttachFile(String strFileID) {
        String url = "select id from package_attach_file paf where paf.uuid=" + strFileID;
        PackageFileAttachModel m = dao.findFirst(url);
        if (m != null) {
            dao.deleteById(m);
        }
    }

    public PackageAttachFileBean getAttachFileItem(String strFileID) {
        PackageAttachFileBean packageAttachFileBean = null;
        String url = "select * from package_attach_file paf where paf.uuid=" + strFileID;
        PackageFileAttachModel m = dao.findFirst(url);
        if (m != null) {
            packageAttachFileBean = new PackageAttachFileBean();
            packageAttachFileBean.strPackageID = m.get("package_id");
        }
        return packageAttachFileBean;
    }
}
