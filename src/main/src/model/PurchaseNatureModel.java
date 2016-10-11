package model;

import com.jfinal.plugin.activerecord.Model;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.List;

/**
 * Created by JooLiu on 2016/10/11.
 */
public class PurchaseNatureModel extends Model<PurchaseNatureModel> {
    public static final PurchaseNatureModel dao = new PurchaseNatureModel();

    public JSONArray getPurchaseNatureList() {
        String url = "select * from purchase_nature";
        List<PurchaseNatureModel> m = dao.find(url);
        JSONArray lst = new JSONArray();
        for (int i=0; i<m.size(); i++) {
            PurchaseNatureModel item  = m.get(i);
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
