package model;

import com.jfinal.plugin.activerecord.Model;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.util.List;

/**
 * Created by JooLiu on 2016/10/10.
 */
public class FinanceDeptModel extends Model<FinanceDeptModel> {
    public static final FinanceDeptModel dao = new FinanceDeptModel();

    public JSONArray getFinanceDeptList() {
        String url = "select * from finance_dept";
        List<FinanceDeptModel> m = dao.find(url);
        JSONArray lst = new JSONArray();
        for (int i=0; i<m.size(); i++) {
            FinanceDeptModel item  = m.get(i);
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
