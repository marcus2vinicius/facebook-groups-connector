package view;

import datasource.GroupDao;
import domain.*;
import domain.User;
import facebook.ConfigurationGroup;
import facebook.FacebookConnection;
import facebook4j.*;
import facebook4j.Facebook;
import facebook4j.Group;
import facebook4j.auth.AccessToken;
import process.GroupProcess;
import service.Executor;
import util.SystemMessages;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marcus on 28/06/2015.
 */
@ManagedBean(name = "index")
@SessionScoped
public class IndexView {
    private String code;
    @ManagedProperty(value = "#{loginView.facebook}")
    private Facebook facebook;
    private String nameGroupSearch;
    ConfigurationGroup cg;
    private List<Group> groups;
    private Group groupDetails;
    private boolean render_idGroupDetail;
    private boolean render_addId;
    private boolean render_searchGroups;
    private boolean render_confirDate;
    private boolean render_viewFim;
    private boolean render_GroupsSearchByName;
    @ManagedProperty(value = "#{connectView.user}")
    private domain.User user;


    public IndexView(){
        cg = new ConfigurationGroup();
        render_addId = true;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void connectFacebook (){
        if(facebook==null)
        try {
            //accessToken = facebook.getOAuthAccessToken(code).getToken();
            //cg = new ConfigurationGroup(facebook,accessToken,cg.getGroupID(),new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchGroups(String value){
        ResponseList<Group> gs = null;
        try {
            Reading r = new Reading().fields("icon,link,description,cover,updated_time,name,owner");
             gs = facebook.searchGroups(value,r);
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        if(gs!=null)
            groups = gs;
    }

    public ConfigurationGroup getCg() {
        return cg;
    }

    public void setCg(ConfigurationGroup cg) {
        this.cg = cg;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public List<Group> getGroups() {
        groups = getGroupsFull();
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroupsFull() {
        List<Group> fullGroups = new ArrayList<>();
        Reading r = new Reading().fields("name,id").limit(20);
        try {
            PagableList<Group> groups = facebook.getGroups(r);
            Paging<Group> paging;
            do {
                for (Group g: groups)
                    fullGroups.add(g);
                paging = groups.getPaging();
                groups = facebook.fetchNext(paging);
            } while ((paging != null) &&
                    (groups != null));
        } catch (FacebookException ex) {

        }
        return fullGroups;
    }

    public void click_SearchGroup(String groupId){
        Reading r = new Reading().fields("icon,link,description,cover,updated_time,name,owner");
        try {
            cg.setGroupID(groupId);
            groupDetails = facebook.getGroup(cg.getGroupID(), r);
            render_idGroupDetail = true;
            render_addId = false;
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null
                    ,new FacesMessage(FacesMessage.SEVERITY_FATAL,"Failure",""));
        }
        if(groupDetails == null)
            FacesContext.getCurrentInstance().addMessage(null
                    ,new FacesMessage(FacesMessage.SEVERITY_FATAL,"Search Failure ",""));

    }

    public void click_ApplyGroup(){
        render_addId = false;
        render_idGroupDetail = false;
        render_confirDate = true;
        render_GroupsSearchByName = false;
        cg.setGroupID(groupDetails.getId());
        cg.setLastUpdate(new Date());
        saveGroupDB();
    }

    private void saveGroupDB() {
        GroupDao groupDao = new GroupDao();
        domain.Group g = new domain.Group();
        g.setGroupId(cg.getGroupID());
        g.setUser(user);
        groupDao.save(g);
    }


    public void click_ApplyDate() throws Exception {
            render_viewFim = true;
            render_confirDate = false;
            cg.setFacebook(facebook);
            cg.setAccessToken(facebook.getOAuthAccessToken().getToken());
            Thread thread = new Thread(new Executor(cg));
            thread.start();
    }

    public void click_Finalize(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("/");
        }catch (Exception e){

        }
    }

    public void click_ViewLog(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/page/log.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchGroupsByUrl(String value){
        ResponseList<Group> gs = null;
        String url = value.split("groups")[1];
        try {
            Reading r = new Reading().fields("icon,link,description,cover,updated_time,name,owner");
            gs = facebook.searchGroups(url,r);
        } catch (FacebookException e) {
            e.printStackTrace();
        }
        if(gs!=null&& !gs.isEmpty()) {
            groupDetails = gs.get(0);
            render_idGroupDetail = true;
        }

    }

    public Group getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(Group groupDetails) {
        this.groupDetails = groupDetails;
    }

    public boolean isRender_idGroupDetail() {
        return render_idGroupDetail;
    }

    public void setRender_idGroupDetail(boolean render_idGroupDetail) {
        this.render_idGroupDetail = render_idGroupDetail;
    }

    public boolean isRender_addId() {
        return render_addId;
    }

    public void setRender_addId(boolean render_addId) {
        this.render_addId = render_addId;
    }

    public boolean isRender_searchGroups() {
        return render_searchGroups;
    }

    public void setRender_searchGroups(boolean render_searchGroups) {
        this.render_searchGroups = render_searchGroups;
    }

    public boolean isRender_confirDate() {
        return render_confirDate;
    }

    public void setRender_confirDate(boolean render_confirDate) {
        this.render_confirDate = render_confirDate;
    }

    public boolean isRender_viewFim() {
        return render_viewFim;
    }

    public void setRender_viewFim(boolean render_viewFim) {
        this.render_viewFim = render_viewFim;
    }

    public boolean isRender_GroupsSearchByName() {
        return render_GroupsSearchByName;
    }

    public void setRender_GroupsSearchByName(boolean render_GroupsSearchByName) {
        this.render_GroupsSearchByName = render_GroupsSearchByName;
    }

    public String getNameGroupSearch() {
        return nameGroupSearch;
    }

    public void setNameGroupSearch(String nameGroupSearch) {
        this.nameGroupSearch = nameGroupSearch;
    }

    public domain.User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
