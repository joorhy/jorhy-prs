package model;

import com.jfinal.plugin.activerecord.Model;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JooLiu on 2016/9/9.
 */
public class FundsNatureModel extends Model<FundsNatureModel> {
    public static final FundsNatureModel dao = new FundsNatureModel();

    public JSONArray getFundsNatureList() {
        String url = "select * from funds_nature";
        List<FundsNatureModel> m = dao.find(url);
        JSONArray lst = new JSONArray();
        for (int i=0; i<m.size(); i++) {
            FundsNatureModel item  = m.get(i);
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
