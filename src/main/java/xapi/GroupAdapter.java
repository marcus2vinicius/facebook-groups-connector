package xapi;

import com.rusticisoftware.tincan.*;
import facebook.GroupBean;
import facebook.PostBean;

import java.net.URISyntaxException;

/**
 * Created by Marcus on 25/08/2015.
 */
public class GroupAdapter {

    public Statement toStatement(GroupBean groupBean) throws URISyntaxException {
        AgentAccount acc = new AgentAccount();
        acc.setName("https://www.facebook.com/"+groupBean.getGroup().getOwner().getId());
        acc.setHomePage(acc.getName());
        Agent agent = new Agent();
        agent.setAccount(acc);
        Verb verb = new Verb("http://activitystrea.ms/schema/1.0/create");
        Activity activity = new Activity("https://www.facebook.com/groups/"+groupBean.getGroup().getId());
        Statement st = new Statement();
        st.setActor(agent);
        st.setVerb(verb);
        st.setObject(activity);
        return st;
    }
}
