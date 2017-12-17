package test;

import facebook4j.*;
import facebook4j.auth.AccessToken;
import util.PropertiesProjetct;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marcus on 27/08/2015.
 */
public class TestFacebook {
    public static void main(String[] args) throws FacebookException, URISyntaxException {
        AccessToken accessToken = new AccessToken
                ("seu token"/*,50000l*/);
        Facebook facebook = new FacebookFactory().getInstance();
        //facebook.setOAuthAppId("dasdasdas","gdhgdgf");
        facebook.setOAuthAccessToken(accessToken);
        //String idLang = "269194416489357";
        //List<Group> l = facebook.searchGroups("languagepascal");
        //Group g = facebook.getGroup("269194416489357");
        /*  PAGINATION
        Reading r = new Reading().fields("created_time,updated_time,from,type,description,message,name,to,actions,link")
                .limit(3000);
        List<Post> postFull = new ArrayList<>();
        ResponseList<Post> posts = facebook.getGroupFeed("2204806663",r);
        do{
            if(posts!=null)
                postFull.addAll(posts);
            Paging<Post> page = posts.getPaging();
            posts = facebook.fetchNext(page);
        }while (posts!=null && !posts.isEmpty());
        System.out.println("Qtd Posts: "+postFull.size());

        Reading r = new Reading().fields("cover,id,description,email,icon,link,member_request_count,name,owner,parent" +
                ",privacy,updated_time");
        //Group g = facebook.getGroup("876058585821115",r);
        Reading readinPost = new Reading().fields
                ("created_time,updated_time,from,type,description,message,name,to,actions,link");
    */
        //List<Post> posts = facebook.getGroupFeed("876058585821115",readinPost);
        //List<GroupDoc> docs = facebook.getGroupDocs("876058585821115");
        //Reading readinGroup = new Reading().fields("id,icon,link,description,cover,updated_time,name,owner,venue");
        //Group g = facebook.getGroup("409693242553683",readinGroup);
        AccessToken ac = facebook.getOAuthAccessToken();
        //URL
/*        URL url = facebook.getGroupPictureURL("876058585821115");
        URI u = url.toURI();*/

        String clientId = PropertiesProjetct.getFacebook().getProperty("oauth.appId");
        String clientSecret = PropertiesProjetct.getFacebook().getProperty("oauth.appSecret");
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "fb_exchange_token");
        params.put("fb_exchange_token", facebook.getOAuthAccessToken().getToken());
        RawAPIResponse apiResponse = facebook.callGetAPI("/oauth/access_token", params);
        String response = apiResponse.asString();
        AccessToken newAccessToken = new AccessToken(response);

    }

}

