package test;

import com.rusticisoftware.tincan.*;
import com.rusticisoftware.tincan.lrsresponses.StatementLRSResponse;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by Marcus on 25/08/2015.
 */
public class TestXAPI {
    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        RemoteLRS lrs = new RemoteLRS();

        lrs.setEndpoint("https://cloud.scorm.com/tc/conta");
        lrs.setVersion(TCAPIVersion.V101);
        lrs.setUsername("user name login email");
        lrs.setPassword("senha");

        Agent agent = new Agent();
        agent.setMbox("mailto:info@tincanapi.com");

        Verb verb = new Verb("http://adlnet.gov/expapi/verbs/attempted");

        Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava");

        Statement st = new Statement();
        st.setActor(agent);
        st.setVerb(verb);
        st.setObject(activity);

        StatementLRSResponse lrsRes = lrs.saveStatement(st);
        if (lrsRes.getSuccess()) {
            // success, use lrsRes.getContent() to get the statement back
            System.out.println("Enviado");
        }
        else {
            // failure, error information is available in lrsRes.getErrMsg()
        }
    }
}
