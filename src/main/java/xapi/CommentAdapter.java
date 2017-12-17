package xapi;

import com.rusticisoftware.tincan.*;
import facebook.CommentBean;
import facebook.PostBean;

import java.net.URISyntaxException;

/**
 * Created by Marcus on 25/08/2015.
 */
public class CommentAdapter {

    public Statement toStatement(CommentBean commentBean) throws URISyntaxException {
        AgentAccount acc = new AgentAccount();
        acc.setName("https://www.facebook.com/"+commentBean.getComment().getFrom().getId());
        acc.setHomePage(acc.getName());
        Agent agent = new Agent();
        agent.setAccount(acc);
        Verb verb = new Verb("http://adlnet.gov/expapi/verbs/commented");//VerbComment
        Activity activity = new Activity(commentBean.getPost().getActions().get(0).getLink());
        Statement st = new Statement();
        st.setActor(agent);
        st.setVerb(verb);
        st.setObject(activity);
        return st;
    }
}
