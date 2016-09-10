package controller;

import com.jfinal.core.Controller;
import model.PackageModel;

/**
 * Created by Joo on 2016/9/1.
 */
public class PackageController extends Controller {
    public void submit() {
        String strPackageID = getPara("package_id");
        PackageModel.dao.submitPackage(strPackageID);

        setAttr("result", "success");
        renderJson();
    }
}
