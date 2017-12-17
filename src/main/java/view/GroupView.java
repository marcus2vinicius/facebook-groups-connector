package view;

import datasource.GroupDao;
import datasource.XapiAccountDao;
import domain.Group;
import domain.User;
import domain.XapiAccount;
import facebook.ConfigurationGroup;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Reading;
import facebook4j.ResponseList;
import org.primefaces.event.SelectEvent;
import service.Executor;
import service.Threads;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marcus on 22/08/2015.
 */
@ManagedBean
@SessionScoped
public class GroupView {
    @ManagedProperty(value = "#{homeView.groups}")
    private List<Group> groups;
    private GroupDao groupDao;
    @ManagedProperty(value = "#{homeView.user}")
    private User user;
    private Group groupSelect;
    private String urlOrIdGroup;
    private facebook4j.Group grougFace;
    @ManagedProperty(value = "#{connectView.facebook}")
    private Facebook facebook;
    private boolean render_groupDetail;
    private boolean render_datatable;
    private boolean render_validButton;
    private boolean saveIsValid;
    private boolean isClickValid;
    private boolean disable_buttonsave;
    private ConfigurationGroup cg;

    @PostConstruct
    public void init(){

    }

    public GroupView(){
        groupDao = new GroupDao();
        groupSelect = new Group();
        render_datatable = true;
        cg = new ConfigurationGroup();
        cg.setLastUpdate(new Date());
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

    public String getUrlOrIdGroup() {
        return urlOrIdGroup;
    }

    public void setUrlOrIdGroup(String urlOrIdGroup) {
        this.urlOrIdGroup = urlOrIdGroup;
    }

    public void validade(){
        isClickValid = true;
        Reading r = new Reading().fields("id,icon,link,description,cover,updated_time,name,owner");
        if(ifUrl(urlOrIdGroup)) {
            ResponseList<facebook4j.Group> gs = null;
            String url = urlOrIdGroup.split("www.facebook.com/groups/")[1];
            try {
                gs = facebook.searchGroups(url, r);
            } catch (FacebookException e) {
                e.printStackTrace();
            }
            if (gs != null && !gs.isEmpty()) {
                grougFace = gs.get(0);
            }
        }

        if(grougFace==null){
            try {
                String id;
                if(ifUrl(urlOrIdGroup)){
                    URI uri = new URI(urlOrIdGroup);
                    id = uri.getPath().replace("groups","").replace("/","");
                }else{
                    id = urlOrIdGroup;
                }
                grougFace = facebook.getGroup(id, r);
            } catch (URISyntaxException e) {}
            catch (FacebookException f){}
        }

        if(groupSelect==null){
            groupSelect = new Group();
        }
        if(grougFace!=null){
            groupSelect.setGroupId(grougFace.getId());
            groupSelect.setName(grougFace.getName());
            groupSelect.setDescription(grougFace.getDescription());
            groupSelect.setUpdatedTime(grougFace.getUpdatedTime());
            saveIsValid = true;
            grougFace = null;
            FacesContext.getCurrentInstance().addMessage("msgurlOrIdGroup"
                    ,new FacesMessage(FacesMessage.SEVERITY_INFO,"Group Valid",""));
        }else {
            FacesContext.getCurrentInstance().addMessage("msgurlOrIdGroup"
                    ,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Group Invalid",""));

        }
    }

    public void click_Cancel(){
        render_datatable = true;
        render_groupDetail = false;
        render_validButton = false;
    }

    public void click_Delete(){
        groups.remove(groupSelect);
        GroupDao groupDao = new GroupDao();
        groupDao.delete(groupSelect);
        click_Cancel();
    }

    public void save(){
        if(cg.getLastUpdate()==null){
            FacesContext.getCurrentInstance().addMessage(null
                    , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date Start Required", ""));

        }else {

            if (!isClickValid)
                validade();
            if (saveIsValid) {
                GroupDao groupDao = new GroupDao();
                groupSelect.setUser(user);
                groupSelect.setRunned(true);
                groupSelect.setLastVerify(groupSelect.getStarted());
                groupSelect.setCreated(new Date());
                groupDao.save(groupSelect);
                FacesContext.getCurrentInstance().addMessage(null
                        , new FacesMessage(FacesMessage.SEVERITY_INFO, "Group Saved", ""));
                render_datatable = true;
                render_groupDetail = false;
                render_validButton = false;
                groups.add(groupSelect);
                startService();
            } else {
                FacesContext.getCurrentInstance().addMessage(null
                        , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Group Failure Saved", ""));

            }
        }
    }

    private void startService(){
        //Thread thread = null;
        try {
            setConfigurationGroup();
            groupDao.save(groupSelect);
            Threads.add(groupSelect.getId(),new Executor(cg));
            if(cg.getXapiAccount()==null)
                FacesContext.getCurrentInstance().addMessage(null
                        , new FacesMessage(FacesMessage.SEVERITY_WARN, "Account XAPI is not configured", ""));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null
                    , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure init service", ""));

        }
        //thread.start();
    }

    private void setConfigurationGroup() {
        cg.setGroupID(groupSelect.getGroupId());
        cg.setFacebook(facebook);
        cg.setAccessToken(facebook.getOAuthAccessToken().getToken());
        cg.setLastUpdate(groupSelect.getStarted());
        cg.setXapiAccount(getXapiAccount());
        if(groupSelect.getIntervalPoll()>0)
            cg.setIntervalPoll(60000*groupSelect.getIntervalPoll());
        else{
            cg.setIntervalPoll(60000*5);
        }
    }

    private XapiAccount getXapiAccount() {
        XapiAccountDao xdao = new XapiAccountDao();
        XapiAccount xapi = xdao.byUser(user);
        return xapi;
    }

    public boolean isStarted(domain.Group group){
        boolean ret = Threads.contain(group.getId());
        return ret;
    }

    public void stopGroup(Group group){
        group.setRunned(false);
        groupDao.save(group);
        Threads.stop(group.getId());
    }

    public void startGroup(Group group){
        startService();
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public facebook4j.Group getGrougFace() {
        return grougFace;
    }

    public void setGrougFace(facebook4j.Group grougFace) {
        this.grougFace = grougFace;
    }

    private boolean ifUrl(String urlOrIdGroup) {
        return urlOrIdGroup.contains("www.facebook.com/groups/");
    }

    public boolean isRender_groupDetail() {
        return render_groupDetail;
    }

    public void setRender_groupDetail(boolean render_groupDetail) {
        this.render_groupDetail = render_groupDetail;
    }

    public boolean isRender_datatable() {
        return render_datatable;
    }

    public void setRender_datatable(boolean render_datatable) {
        this.render_datatable = render_datatable;
    }

    public boolean isRender_validButton() {
        return render_validButton;
    }

    public void setRender_validButton(boolean render_validButton) {
        this.render_validButton = render_validButton;
    }

    public boolean isDisable_buttonsave() {
        return disable_buttonsave;
    }

    public void setDisable_buttonsave(boolean disable_buttonsave) {
        this.disable_buttonsave = disable_buttonsave;
    }

    public void click_new(){
        groupSelect = new Group();
        render_datatable = false;
        render_groupDetail = true;
        render_validButton = true;
        disable_buttonsave = false;
        urlOrIdGroup = "";
        groupSelect.setStarted(new Date());
        groupSelect.setIntervalPoll(5);
    }

    public void click_ItemDatatable(SelectEvent event){
        groupSelect = (Group)event.getObject();
        render_datatable = false;
        render_groupDetail = true;
        render_validButton = false;
        disable_buttonsave = true;
    }

    public ConfigurationGroup getCg() {
        return cg;
    }

    public void setCg(ConfigurationGroup cg) {
        this.cg = cg;
    }

    public Date dateNow(){
        return new Date();
    }
}
