package facebook;

import facebook4j.Group;

import java.util.List;

/**
 * Created by marcus on 20/08/2015.
 */
public class GroupBean {
    private String GroupID;
    private Group Group;

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public facebook4j.Group getGroup() {
        return Group;
    }

    public void setGroup(facebook4j.Group group) {
        Group = group;
    }

    @Override
    public String toString() {
        return "Group='" + GroupID+" UpdateTime: "+Group.getUpdatedTime();
    }
}
