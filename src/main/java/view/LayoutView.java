package view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


/**
 * Created by marcus on 18/08/2015.
 */
@ManagedBean
@SessionScoped
public class LayoutView implements Serializable{
    @PostConstruct
    public void init() {

    }

    public String click_logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public void click_groups() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/page/group/group.xhtml");
    }

    public void click_charts() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/page/charts.xhtml");
    }


    public void click_home() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/page/");
    }

}
