package facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marcus on 15/08/2015.
 */

public class FacebookConnection {
    private static List<ConfigurationGroup> confs = new ArrayList<ConfigurationGroup>();

    public static ConfigurationGroup getInstance(String accessToken, String groupId){
        ConfigurationGroup cg = new ConfigurationGroup(groupId);
        if(confs.contains(cg)){
            cg = confs.get(confs.indexOf(cg));
            AccessToken at = new AccessToken(accessToken);
            cg.getFacebook().setOAuthAccessToken(at);
        }else{
            Facebook f  = null;/*new FacebookFactory().getInstance();*/
            AccessToken at = new AccessToken(accessToken);
            f.setOAuthAccessToken(at);
            cg = new ConfigurationGroup(f,accessToken,groupId,new Date());
            confs.add(cg);
        }
        return cg;
    }

    public static ConfigurationGroup getInstance(ConfigurationGroup cg){
        if(confs.contains(cg)){
            cg = confs.get(confs.indexOf(cg));
            AccessToken at = new AccessToken(cg.getAccessToken());
            cg.getFacebook().setOAuthAccessToken(at);
        }else{
            if(cg.getFacebook()==null) {
                Facebook f = new FacebookFactory().getInstance();
                cg.setFacebook(f);
            }
            AccessToken at = new AccessToken(cg.getAccessToken());
            cg.getFacebook().setOAuthAccessToken(at);
            cg = new ConfigurationGroup(cg.getFacebook(),cg.getAccessToken(),cg.getGroupID(),new Date());
            confs.add(cg);
        }
        return cg;
    }

}
