package view.group;

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
import service.Executor;
import service.Threads;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * Created by marcus on 06/09/2015.
 */
@ManagedBean
@ViewScoped
public class GroupDetailsView extends GroupView{
    @ManagedProperty(value = "#{groupListView.groupSelect}")
    private Group groupSelect;
    @ManagedProperty(value = "#{homeView.user}")
    private User user;
    @ManagedProperty(value = "#{connectView.facebook}")
    private Facebook facebook;
    @ManagedProperty(value = "#{homeView.groups}")
    private List<Group> groups;
    private facebook4j.Group grougFace;
    private boolean isClickValid;
    private String urlOrIdGroup;
    private boolean saveIsValid;
    private boolean disable_buttonsave;
    private ConfigurationGroup cg;

    @PostConstruct
    public void init(){
        if(groupSelect != null && groupSelect.isSalved())
            disable_buttonsave = true;
    }
    public GroupDetailsView(){

    }


    public Group getGroupSelect() {
        return groupSelect;
    }

    public void setGroupSelect(Group groupSelect) {
        this.groupSelect = groupSelect;
    }

    public Date dateNow(){
        return new Date();
    }

    public boolean isStarted(domain.Group group){
        boolean ret = Threads.contain(group.getId());
        return ret;
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
            String url = urlOrIdGroup.split("facebook.com/groups/")[1];
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

    private boolean ifUrl(String urlOrIdGroup) {
        return urlOrIdGroup.contains("facebook.com/groups/");
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public void startGroup(){
        startService();
    }

    private void startService(){
        //Thread thread = null;
        try {
            setConfigurationGroup();
            groupSelect.setRunned(true);
            new GroupDao().save(groupSelect);
            Threads.add(groupSelect.getId(),new Executor(cg));
            if(cg.getXapiAccount()==null)
                FacesContext.getCurrentInstance().addMessage(null
                        , new FacesMessage(FacesMessage.SEVERITY_WARN, "Account XAPI is not configured", ""));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null
                    , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure init service", ""));

        }
    }

    private void setConfigurationGroup() {
        if(cg==null)
            cg = new ConfigurationGroup();
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

    public void save(){
    if(false/*cg.getLastUpdate()==null*/){
    /*
            FacesContext.getCurrentInstance().addMessage(null
                    , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date Start Required", ""));
                    */

        }else {

            if (!isClickValid)
                validade();
            if (saveIsValid) {
                GroupDao groupDao = new GroupDao();
                groupSelect.setUser(user);
                //groupSelect.setRunned(true);
                groupSelect.setLastVerify(groupSelect.getStarted());
                groupSelect.setCreated(new Date());
                groupDao.save(groupSelect);
                FacesContext.getCurrentInstance().addMessage(null
                        , new FacesMessage(FacesMessage.SEVERITY_INFO, "Group Saved", ""));
                groups.add(groupSelect);
                //startService();
                setRender_functionsGroup(true);
                setRender_searchGroups(false);
                disable_buttonsave = true;
                groupSelect.setIntervalPoll(5);
                groupSelect.setStarted(new Date());
            } else {
                FacesContext.getCurrentInstance().addMessage(null
                        , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Group Failure Saved", ""));

            }
        }
    }

    public void click_Cancel(){
        setRender_list(true);
        groupSelect = null;
        urlOrIdGroup = "";
        FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("groupDetailsView");
    }

    public void click_Delete(){
        groups.remove(groupSelect);
        GroupDao groupDao = new GroupDao();
        groupDao.delete(groupSelect);
        click_Cancel();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public boolean isDisable_buttonsave() {
        return disable_buttonsave;
    }

    public void setDisable_buttonsave(boolean disable_buttonsave) {
        this.disable_buttonsave = disable_buttonsave;
    }

    public void stopGroup(){
        groupSelect.setRunned(false);
        new GroupDao().save(groupSelect);
        Threads.stop(groupSelect.getId());
    }
}
