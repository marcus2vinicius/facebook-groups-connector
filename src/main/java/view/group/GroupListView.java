package view.group;

import datasource.GroupDao;
import domain.Group;
import domain.User;
import org.primefaces.event.SelectEvent;
import service.Threads;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

/**
 * Created by marcus on 06/09/2015.
 */
@ManagedBean
@ViewScoped
public class GroupListView extends GroupView{
    @ManagedProperty(value = "#{homeView.groups}")
    private List<Group> groups;
    private GroupDao groupDao;
    @ManagedProperty(value = "#{homeView.user}")
    private User user;
    private Group groupSelect;

    @PostConstruct
    public void init(){
        groupDao = new GroupDao();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroupSelect() {
        return groupSelect;
    }

    public void setGroupSelect(Group groupSelect) {
        this.groupSelect = groupSelect;
    }

    public boolean isStarted(domain.Group group){
        boolean ret = Threads.contain(group.getId());
        return ret;
    }

    public void click_new(){
        groupSelect = new Group();
        groupSelect.setStarted(new Date());
        groupSelect.setIntervalPoll(5);

        super.setGroupSelec(new Group());
        super.getGroupSelec().setStarted(new Date());
        super.getGroupSelec().setIntervalPoll(5);

        setRender_list(false);
        setReadOnly(true);
        setRender_searchGroups(true);
    }

    public void click_ItemDatatable(SelectEvent event){
        groupSelect = (Group)event.getObject();

        super.setGroupSelec(groupSelect);

        setRender_detail(true);
        setRender_list(false);
        setReadOnly(true);
        setRender_searchGroups(false);
        setRender_functionsGroup(true);
    }
}
