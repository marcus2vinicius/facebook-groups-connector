package xapi;

import com.rusticisoftware.tincan.*;
import facebook.LikeBean;
import facebook.PostBean;

import java.net.URISyntaxException;

/**
 * Created by Marcus on 25/08/2015.
 */
public class LikeAdapter {

    public Statement toStatement(LikeBean likeBean) throws URISyntaxException {
        AgentAccount acc = new AgentAccount();
        acc.setName("https://www.facebook.com/"+likeBean.getLikeID());
        acc.setHomePage(acc.getName());
        Agent agent = new Agent();
        agent.setAccount(acc);
        Verb verb = new Verb("http://activitystrea.ms/schema/1.0/like");
        Activity activity;
        activity = new Activity(likeBean.getUrlPost());
        Statement st = new Statement();
        st.setActor(agent);
        st.setVerb(verb);
        st.setObject(activity);
        return st;
    }
}
