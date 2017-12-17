package view;

import datasource.FacebookDao;
import datasource.GroupDao;
import datasource.XapiAccountDao;
import domain.Facebook;
import domain.Group;
import domain.User;
import service.Threads;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * Created by marcus on 18/08/2015.
 */
@ManagedBean
@SessionScoped
public class HomeView implements Serializable{
    @ManagedProperty(value = "#{connectView.user}")
    private User user;
    private List<Group> groups;
    private domain.Facebook facebook;

    @PostConstruct
    public void init() {

    }
    public HomeView(){

    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Group> getGroups() {
        if(groups==null)
            addGroups();
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroups(){
        GroupDao groupDao = new GroupDao();
        groups = groupDao.byUser(user);
    }

    public int countGroupsRunning(){
        return groups.stream().filter(g->Threads.contain(g.getId())).collect(Collectors.toList()).size();
    }

    public boolean isStarted(domain.Group group){
        boolean ret = Threads.contain(group.getId());
        return ret;
    }


}
