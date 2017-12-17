package process;

import datasource.DataStatic;
import datasource.Receptor;
import datasource.ReceptorErro;
import domain.LogGroup;
import facebook.ConfigurationGroup;
import facebook.GroupBean;
import facebook4j.FacebookException;
import facebook4j.Group;
import facebook4j.Reading;

import java.util.Date;

/**
 * Created by marcus on 15/08/2015.
 */
public class GroupProcess extends IProcess{
    private Group group;
    private GroupBean groupBean;
    public GroupProcess(ConfigurationGroup configurationGroup) throws Exception {
        super(configurationGroup);
        group = getGroup(configurationGroup.getGroupID());
    }

    private Group getGroup(String id) {
        Reading readinGroup = new Reading().fields("id,icon,link,description,cover,updated_time,name,owner");
        try {
            return configurationGroup.getFacebook().getGroup(id,readinGroup);
        } catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return null;
        }
    }

    public boolean isUpdated(){
        boolean ret = configurationGroup.getLastUpdate().getTime() < group.getUpdatedTime().getTime();
        DataStatic.add("0", "Config DateLast: " + configurationGroup.getLastUpdate() + " [Init isUpdatedGroup] "+ret+" - "+group.getUpdatedTime());
        return ret;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void send() throws Exception {
        if(isUpdated()) {
            DataStatic.add("0", "Config DateLast: " + configurationGroup.getLastUpdate() + " [Init sendPosts]"+group.getUpdatedTime());
            new PostProcess(configurationGroup).send();
            DataStatic.add("0", "Config DateLast: " + configurationGroup.getLastUpdate() + " [Done sendPosts]"+group.getUpdatedTime());
        }
    }
}
