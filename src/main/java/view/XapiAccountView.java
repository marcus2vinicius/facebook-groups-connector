package view;

import datasource.GroupDao;
import datasource.XapiAccountDao;
import domain.Group;
import domain.User;
import domain.XapiAccount;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


/**
 * Created by marcus on 18/08/2015.
 */
@ManagedBean
@ViewScoped
public class XapiAccountView implements Serializable{
    @ManagedProperty(value = "#{connectView.user}")
    private User user;
    private XapiAccount xapiAccount;


    @PostConstruct
    public void init() {
        verifyAccountCreat();
    }

    public XapiAccountView(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public XapiAccount getXapiAccount() {
        return xapiAccount;
    }

    public void setXapiAccount(XapiAccount xapiAccount) {
        this.xapiAccount = xapiAccount;
    }

    public void verifyAccountCreat(){
        XapiAccountDao xdao = new XapiAccountDao();
        xapiAccount = xdao.byUser(user);
        if(xapiAccount==null)
            xapiAccount = new XapiAccount();
    }

    public void save(){
        XapiAccountDao xdao = new XapiAccountDao();
        xapiAccount.setUser(user);
        xdao.save(xapiAccount);
        FacesContext.getCurrentInstance().addMessage(null
                , new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", ""));

    }

}
