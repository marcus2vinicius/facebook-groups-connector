package view.group;

import domain.Group;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 * Created by marcus on 06/09/2015.
 */
@ManagedBean(name = "group1View")
@ViewScoped
public class GroupView {
    protected static boolean render_list;
    protected static boolean render_detail;
    protected static boolean readOnly;
    protected static boolean render_searchGroups;
    protected static boolean render_functionsGroup;
    private static Group groupSelec;

    public GroupView(){

    }

    @PostConstruct
    public void init(){
        setRender_list(true);
    }

    public boolean isRender_list() {
        return render_list;
    }

    public void setRender_list(boolean render_list) {
        this.render_list = render_list;
        render_detail = !render_list;
    }

    public boolean isRender_detail() {
        return render_detail;
    }

    public void setRender_detail(boolean render_detail) {
        this.render_detail = render_detail;
        render_list = !render_detail;

    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        GroupView.readOnly = readOnly;
    }

    public boolean isRender_searchGroups() {
        return render_searchGroups;
    }

    public void setRender_searchGroups(boolean render_searchGroups) {
        GroupView.render_searchGroups = render_searchGroups;
    }

    public boolean isRender_functionsGroup() {
        return render_functionsGroup;
    }

    public void setRender_functionsGroup(boolean render_functionsGroup) {
        this.render_functionsGroup = render_functionsGroup;
    }

    public Group getGroupSelec() {
        return groupSelec;
    }

    public void setGroupSelec(Group groupSelec) {
        this.groupSelec = groupSelec;
    }

    @PreDestroy
    public void exit(){
        setRender_list(true);
    }
}
