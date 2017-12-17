package datasource;

/**
 * Created by Marcus on 21/08/2015.
 */
public class Message {
    private String groupId;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Message(String groupId, String msg) {
        this.groupId = groupId;
        this.msg = msg;
    }
}
