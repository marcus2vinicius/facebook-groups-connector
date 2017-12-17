package view;

import facebook.FacebookConnection;
import facebook4j.Facebook;
import process.GroupProcess;
import util.SystemMessages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Created by marcus on 18/08/2015.
 */
@ManagedBean
@ViewScoped
public class LogView implements Serializable{
    private List<String> dataTableMessages;;
    @PostConstruct
    public void init() {

    }

    public List<String> getDataTableMessages() {
        return dataTableMessages;
    }

    public void setDataTableMessages(List<String> dataTableMessages) {
        this.dataTableMessages = dataTableMessages;
    }

    public void updateTable(){
//        GroupProcess groupProcess = new GroupProcess(FacebookConnection.getInstance(cg));
//        if(groupProcess.isUpdated()){
//            groupProcess.send();
//            dataTableMessages = SystemMessages.getMessages();
//            cg = FacebookConnection.getInstance(cg);
//            cg.setLastUpdate(new Date());
//        }

    }

}
