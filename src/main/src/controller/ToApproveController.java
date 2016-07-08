package controller;

import com.jfinal.core.Controller;
import model.PurchaseInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by Administrator on 2016/7/7.
 */
public class ToApproveController extends Controller  {
    // 页面初始化
    public void index(){
        List< Map<String, String>> dataList = new ArrayList< Map<String, String>>();
        Map<String, String> dataInfo;
        for(int i = 0 ;i < 8; i++) {
            dataInfo = new HashMap<String, String>();
            dataInfo.put("itemName", "XX采购申请");
            dataInfo.put("itemNo", "FI-SW-"+String.valueOf(i+1));
            dataInfo.put("person", "张三");
            dataInfo.put("advice", "同意");
            dataInfo.put("approveDate", "2016-07-07 15:28:35");
            dataInfo.put("remark", "同意采购");
            dataList.add(dataInfo);
        }

        setAttr("rows",dataList);
        setAttr("total",dataList.size());
        renderJson();
    }
}
