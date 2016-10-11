package model;

import com.jfinal.plugin.activerecord.Model;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.List;

/**
 * Created by JooLiu on 2016/10/11.
 */
public class PurchaseTypeModel extends Model<PurchaseTypeModel> {
    public static final PurchaseTypeModel dao = new PurchaseTypeModel();

    public JSONArray getPurchaseTypeList() {
        String url = "select * from purchase_type";
        List<PurchaseTypeModel> m = dao.find(url);
        JSONArray lst = new JSONArray();
        for (int i=0; i<m.size(); i++) {
            PurchaseTypeModel item  = m.get(i);
            JSONObject obj = new JSONObject();
            obj.put("id", item.getInt("id"));
            obj.put("text", item.getStr("name"));
            if (i == 0) {
                obj.put("selected", true);
            }
            lst.put(obj);
        }
        return lst;
    }
}
