package datasource;

import facebook.GroupBean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by NTI-Sistema on 20/08/2015.
 */
@ManagedBean
@ApplicationScoped
public class DataStatic {
    private static List<Message> logListt = new ArrayList<>();
    private static int limit = 8000;

    static {
        logListt = new ArrayList<>();
        limit = 8000;
    }

    public static void add(String groupId, String msg){
        logListt.add(new Message(groupId,msg));
        clearLimit();
    }

    private static void clearLimit() {
        if(logListt.size()>=limit){
            logListt.clear();
        }
    }

    public static List<Message> getLogListt() {
        return logListt;
    }

    public static List<Message> getLogListt(String groupId) {
        List<Message> msgs = logListt.stream().filter(q->q.getGroupId().equals(groupId)).collect(Collectors.toList());
        return msgs;
    }

    public static void setLogListt(List<Message> logListt) {
        DataStatic.logListt = logListt;
    }
}
