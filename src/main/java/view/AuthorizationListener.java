package view;

import domain.User;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by marcus on 06/09/2015.
 */
public class AuthorizationListener implements PhaseListener {


    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean isPagePublic = currentPage.contains("login.xhtml") || currentPage.contains("connect.xhtml");
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        ConnectView con = (ConnectView) session.getAttribute("connectView");
        boolean connected = false;
        if(con!=null&&con.isConneted())
            connected=true;

        if (!isPagePublic && !connected) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    public void beforePhase(PhaseEvent event) {

    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }


}

