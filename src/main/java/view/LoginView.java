package view;

import datasource.DataStatic;
import datasource.UserDao;
import domain.User;
import facebook.FacebookConnection;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import util.Constants;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;


/**
 * Created by marcus on 16/08/2015.
 */
@ManagedBean
@SessionScoped
public class LoginView implements Serializable{
    private Facebook facebook;

    @PostConstruct
    public void init(){

    }

    public void login(){
        String nurl;
        facebook = new FacebookFactory().getInstance();
        if(util.PropertiesProjetct.getProject().get("mode.debub").equals("false")) {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url = req.getRequestURL().toString();
            url = url.substring(0, url.indexOf("/", 8));
            url = url + "/connect.xhtml";
            //facebook.setOAuthAppId("000000000000", "a0a0a0a0a00a0a0a0a0a0");
            facebook.setOAuthPermissions(Constants.permissions);
            nurl = facebook.getOAuthAuthorizationURL(url);
        }else {
            nurl = "/connect.xhtml?code=0";
        }
        DataStatic.getLogListt();
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.redirect(nurl);
        } catch (IOException ex) {

        }
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

}
