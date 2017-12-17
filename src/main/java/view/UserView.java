package view;

import datasource.FacebookDao;
import domain.Facebook;
import domain.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marcus on 28/08/2015.
 */
@ManagedBean
@ViewScoped
public class UserView {
    @ManagedProperty(value = "#{connectView.user}")
    private User user;
    private Facebook facebook;


    public Facebook getFacebook() {
        if(facebook==null) {
            FacebookDao facebookDao = new FacebookDao();
            facebook = facebookDao.getFacebookByUser(user);
        }
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public Long LongTimeToDays(Long longTimeMilliseconds){
        return TimeUnit.MILLISECONDS.toDays(longTimeMilliseconds);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
