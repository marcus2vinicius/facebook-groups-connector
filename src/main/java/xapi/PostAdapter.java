package xapi;

import com.rusticisoftware.tincan.*;
import facebook.PostBean;

import java.net.URISyntaxException;

/**
 * Created by Marcus on 25/08/2015.
 */
public class PostAdapter {

    public Statement toStatement(PostBean postBean) throws URISyntaxException {
        AgentAccount acc = new AgentAccount();
        acc.setName("https://www.facebook.com/"+postBean.getPost().getFrom().getId());
        acc.setHomePage(acc.getName());
        Agent agent = new Agent();
        agent.setAccount(acc);
        Verb verb = new Verb("http://activitystrea.ms/schema/1.0/create");
        Activity activity = new Activity(postBean.getPost().getActions().get(0).getLink());
        Statement st = new Statement();
        st.setActor(agent);
        st.setVerb(verb);
        st.setObject(activity);
        return st;
    }
}
