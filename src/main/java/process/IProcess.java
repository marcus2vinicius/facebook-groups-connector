package process;

import datasource.DataStatic;
import facebook.ConfigurationGroup;
import facebook.FacebookConnection;
import facebook4j.Facebook;

import java.util.Date;

/**
 * Created by marcus on 15/08/2015.
 */
public abstract class IProcess {
    protected ConfigurationGroup configurationGroup;

    public IProcess(ConfigurationGroup configurationGroup) throws Exception {
        this.configurationGroup = configurationGroup;
/*        if(configurationGroup.getFacebook().oaut){
            DataStatic.add("0", "Exception: " + configurationGroup.getLastUpdate() + " [IProcess] ");
            throw new Exception("Expired AccessToken");
        }
*/
    }
/*
    public void send(){}
    public boolean isUpdated(){}
*/
}
