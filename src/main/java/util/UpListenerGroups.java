package util;

import datasource.FacebookDao;
import datasource.GroupDao;
import datasource.XapiAccountDao;
import domain.Group;
import facebook.ConfigurationGroup;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import service.Executor;
import service.Threads;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Marcus on 31/08/2015.
 */
public class UpListenerGroups {

    public static void init() throws Exception {
        GroupDao groupDao = new GroupDao();
        FacebookDao facebookDao = new FacebookDao();
        XapiAccountDao xapiAccountDao = new XapiAccountDao();
        List<Group> groupList = groupDao.getAll().stream().filter(q->q.isRunned()==true).collect(Collectors.toList());
        for (Group g : groupList){
            ConfigurationGroup cg = new ConfigurationGroup();
            Facebook f = new FacebookFactory().getInstance();
            domain.Facebook faceDB = facebookDao.getFacebookByUser(g.getUser());
            f.setOAuthAccessToken(new AccessToken(faceDB.getAccessToken()));

            if(g.getIntervalPoll()>0)
                cg.setIntervalPoll(60000*g.getIntervalPoll());
            else{
                cg.setIntervalPoll(60000*5);
            }
            cg.setAccessToken(faceDB.getAccessToken());
            cg.setLastUpdate(g.getLastVerify());
            cg.setFacebook(f);
            cg.setXapiAccount(xapiAccountDao.byUser(g.getUser()));
            cg.setGroupID(g.getGroupId());
            Threads.add(g.getId(), new Executor(cg));
        }

    }
}
