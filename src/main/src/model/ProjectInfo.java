package model;

import com.jfinal.plugin.activerecord.Model;
import bean.PrjData;

/**
 * Created by Joo on 2016/7/30.
 */
public class ProjectInfo extends Model<ProjectInfo> {
    public static final ProjectInfo dao = new ProjectInfo();

    public String AddProject(PrjData data) {
        return null;
    }
}
