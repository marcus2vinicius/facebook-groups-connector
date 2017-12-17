package util;

import facebook.GroupBean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 17/08/2015.
 */
@SessionScoped
@ManagedBean
public class SystemMessages {
    private List<Message> messages;


    public SystemMessages(){

    }
/*
    public void add(List<GroupBean> groupBeanList){
        for ()

    }
    public static List<GroupBean> getGroupBeanslList() {
        return groupBeanslList;
    }

    public static List<GroupBean> getGroupBeanslList(String GroupId) {
        return groupBeanslList.stream().filter(q->q.getGroupId().equals(GroupId)).collect(Collectors.toList());
    }

    public static void setGroupBeanslList(List<GroupBean> groupBeanslList) {
        SystemMessages.groupBeanslList = groupBeanslList;
    }*/

    public class Message{
        String msg;
        String GroupId;
    }
}
