package view;

import datasource.FacebookDao;
import datasource.UserDao;
import domain.User;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.Reading;
import facebook4j.auth.AccessToken;
import util.PropertiesProjetct;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by marcus on 18/08/2015.
 */
@ManagedBean
@SessionScoped
public class ConnectView implements Serializable{
    private String code;
    @ManagedProperty(value = "#{loginView.facebook}")
    private Facebook facebook;
    private domain.User user;
    private boolean conneted;

    @PostConstruct
    public void init() {
        user = new User();
        conneted = false;

    }

    public void connect(){
        String token;
        try {
            if(util.PropertiesProjetct.getProject().get("mode.debub").equals("false")){
                token = facebook.getOAuthAccessToken(code).getToken();
            }else{
                token = (String) util.PropertiesProjetct.getProject().get("accesstoken");
                facebook.setOAuthAccessToken(new AccessToken(token));
            }
            facebook4j.User u = facebook.getMe(new Reading().fields("id,first_name,email,last_name,name"));
            if(u!=null)
                conneted = true;
            user.setEmail(u.getEmail());
            user.setName(u.getName());
            verifyUserOrCreateUser(token);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/page/home.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        connect();
    }

    public void verifyUserOrCreateUser(String token) throws Exception {
        UserDao userDao = new UserDao();
        domain.Facebook f = new domain.Facebook();
        AccessToken ac = refreshToken();
        facebook.setOAuthAccessToken(ac);
        User userTemp = userDao.getUserByEmail(user.getEmail());
        if(userTemp==null){
            createUser();
        }else {
            user = userTemp;
            FacebookDao facebookDao = new FacebookDao();
            if(facebookDao.getFacebookByUser(user)==null) {
                createFacebook();
            }else{
                updateTokenFacebook();
            }
        }
    }

    private void updateTokenFacebook() {
        FacebookDao facebookDao = new FacebookDao();
        domain.Facebook f = facebookDao.getFacebookByUser(user);
        AccessToken ac = facebook.getOAuthAccessToken();
        f.setExpireToken(ac.getExpires());
        f.setAccessToken(ac.getToken());
        facebookDao.save(f);
    }

    private void createUser() {
        UserDao userDao = new UserDao();
        userDao.save(user);
        createFacebook();
    }

    private void createFacebook() {
        FacebookDao facebookDao = new FacebookDao();
        domain.Facebook fk = new domain.Facebook();
        fk.setAccessToken(facebook.getOAuthAccessToken().getToken());
        fk.setExpireToken(facebook.getOAuthAccessToken().getExpires());
        fk.setUser(user);
        facebookDao.save(fk);
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private AccessToken refreshToken() throws Exception {
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
        return newAccessToken;
    }

    public boolean isConneted() {
        return conneted;
    }

    public void setConneted(boolean conneted) {
        this.conneted = conneted;
    }
}
