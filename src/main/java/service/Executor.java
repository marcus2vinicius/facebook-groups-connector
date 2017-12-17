package service;

import datasource.*;
import domain.Group;
import domain.LogGroup;
import domain.User;
import facebook.ConfigurationGroup;
import facebook.GroupBean;
import facebook4j.Facebook;
import process.GroupProcess;
import util.Constants;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by NTI-Sistema on 18/08/2015.
 */
public class Executor implements Runnable{
    private ConfigurationGroup configurationGroup;
    private GroupProcess groupProcess;
    private boolean Running = false;
    //final static Logger logger = Logger.getLogger(Executor.class);


    public Executor(ConfigurationGroup configurationGroup) throws Exception {
        if(isValid(configurationGroup)) {
            this.configurationGroup = configurationGroup;
        }else{
            throw new Exception("Configuration Invalid");
        }
    }

    private static boolean isValid(ConfigurationGroup configurationGroup) {
        return configurationGroup.getGroupID()!=null && configurationGroup.getAccessToken()!=null &&
                configurationGroup.getFacebook()!=null && configurationGroup.getLastUpdate()!=null&&
                configurationGroup.getIntervalPoll()>0;
    }

    @Override
    public void run() {
        Date lastDate;
        try{
            setRunning(true);
            Thread.sleep(configurationGroup.getIntervalPoll());
        }catch (Exception e){e.printStackTrace();}
        sendGroup();
        DataStatic.add("0","Config DateLast: "+configurationGroup.getLastUpdate()+" [Send Done Group]");
        do {
            try {
                lastDate = new Date();
                DataStatic.add("0","Config DateLast: "+configurationGroup.getLastUpdate()+" [Init GroupProcess]");
                groupProcess = new GroupProcess(configurationGroup);
                if (groupProcess.isUpdated()) {
                    groupProcess.send();
                    configurationGroup.setLastUpdate(lastDate);
                    updateDatabase(lastDate);
                }
                Thread.sleep(configurationGroup.getIntervalPoll());
            }catch (Exception e){
                //setNewAccesToken();
                salveErro("Token Expired!");
                setRunning(false);
            }
        }while (Running);
    }

    private void updateDatabase(Date lastDate) {
        domain.Group g = new GroupDao().byGroupID(configurationGroup.getGroupID());
        g.setLastVerify(lastDate);
        new GroupDao().save(g);
    }

    private void setNewAccesToken() {
        String token = getToken(configurationGroup.getXapiAccount().getUser());
        if(token!=null)
            configurationGroup.setAccessToken(token);
    }

    private void sendGroup() {
        try {
            groupProcess = new GroupProcess(configurationGroup);
            GroupBean groupBean = new GroupBean();
            groupBean.setGroup(groupProcess.getGroup());
            groupBean.setGroupID(groupBean.getGroup().getId());
            Receptor.add(configurationGroup, groupBean);
        }catch (Exception e){
            salveErro("Token Expired");
            e.printStackTrace();
        }
    }

    public ConfigurationGroup getConfigurationGroup() {
        return configurationGroup;
    }

    public void setConfigurationGroup(ConfigurationGroup configurationGroup) {
        this.configurationGroup = configurationGroup;
    }

    private void salveErro(String e){
        try {
            LogGroup logGroup = new LogGroup();
            logGroup.setGroupID(configurationGroup.getGroupID());
            logGroup.setContent(e);
            logGroup.setGroup(getGroupById());
            LogGroupDao logGroupDao = new LogGroupDao();
            logGroupDao.save(logGroup);
        }catch (Exception ex){ex.printStackTrace();}
    }
    public Group getGroupById(){
        GroupDao groupDao = new GroupDao();
        return groupDao.byGroupID(configurationGroup.getGroupID());
    }
    private String getToken(User u){
        FacebookDao facebookDao = new FacebookDao();
        domain.Facebook f = facebookDao.getFacebookByUser(u);
        return f != null ? f.getAccessToken() : null;
    }
    public boolean isRunning() {
        return Running;
    }


    public void setRunning(boolean running) {
        Running = running;
    }
}
