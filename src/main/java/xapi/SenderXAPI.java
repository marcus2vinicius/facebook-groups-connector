package xapi;

import com.rusticisoftware.tincan.RemoteLRS;
import com.rusticisoftware.tincan.Statement;
import com.rusticisoftware.tincan.TCAPIVersion;
import com.rusticisoftware.tincan.lrsresponses.StatementLRSResponse;
import domain.XapiAccount;
import facebook.CommentBean;
import facebook.GroupBean;
import facebook.LikeBean;
import facebook.PostBean;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created by NTI-Sistema on 25/08/2015.
 */
public class SenderXAPI {
    private XapiAccount xapiAccount;

    public SenderXAPI(XapiAccount xapiAccount) throws Exception {
        if(validate(xapiAccount))
            this.xapiAccount = xapiAccount;
        else throw new Exception("XAPI Account Invalid");
    }

    private boolean validate(XapiAccount xapiAccount) {
        return xapiAccount.getPassword()!=null && xapiAccount.getServerUrl()!= null &&
                xapiAccount.getUserName()!= null;
    }

    public void send(PostBean postBean) throws URISyntaxException {
        Statement st = new PostAdapter().toStatement(postBean);
        System.out.println("Post LRS Send " +send(st));
    }

    public void send(GroupBean groupBean) throws URISyntaxException {
        Statement st = new GroupAdapter().toStatement(groupBean);
        System.out.println("Group LRS Send " +send(st));
    }


    public void send(LikeBean likeBean) throws URISyntaxException {
        Statement st = new LikeAdapter().toStatement(likeBean);
        System.out.println("Like LRS Send " + send(st));
    }

    public void send(CommentBean commentBean) throws URISyntaxException {
        Statement st = new CommentAdapter().toStatement(commentBean);
        System.out.println("Comment LRS Send " + send(st));
    }

    private boolean send(Statement st){
        boolean ret = false;
        try {
            RemoteLRS lrs = new RemoteLRS();
            lrs.setEndpoint(xapiAccount.getServerUrl());
            lrs.setVersion(TCAPIVersion.V101);
            lrs.setUsername(xapiAccount.getUserName());
            lrs.setPassword(xapiAccount.getPassword());
            StatementLRSResponse lrsRes = lrs.saveStatement(st);
            ret = lrsRes.getSuccess();
        } catch (MalformedURLException e){e.printStackTrace();}
        return ret;
    }
}
