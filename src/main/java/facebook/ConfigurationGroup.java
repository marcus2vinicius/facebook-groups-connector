package facebook;

import domain.XapiAccount;
import facebook4j.Facebook;

import java.util.Date;

/**
 * Created by NTI-Sistema on 17/08/2015.
 */
public class ConfigurationGroup {
    private Facebook facebook;
    private String accessToken;
    private String groupID;
    private Date lastUpdate;
    private int intervalPoll;
    private XapiAccount xapiAccount;

    public ConfigurationGroup(Facebook facebook, String accessToken, String groupID, Date lastUpdate) {
        this.facebook = facebook;
        this.accessToken = accessToken;
        this.groupID = groupID;
        this.lastUpdate = lastUpdate;
    }

    public ConfigurationGroup(String accessToken, String groupID) {
        this.accessToken = accessToken;
        this.groupID = groupID;
    }

    public ConfigurationGroup() {
    }

    public ConfigurationGroup(String accessToken){
        this.accessToken = accessToken;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public ConfigurationGroup setFacebook(Facebook facebook) {
        this.facebook = facebook;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationGroup that = (ConfigurationGroup) o;
        return !(groupID != null ? !groupID.equals(that.groupID) : that.groupID != null);
    }

    @Override
    public int hashCode() {
        return groupID != null ? groupID.hashCode() : 0;
    }

    public String getGroupID() {
        return groupID;
    }

    public ConfigurationGroup setGroupID(String groupID) {
        this.groupID = groupID;
        return this;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public ConfigurationGroup setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public ConfigurationGroup setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public int getIntervalPoll() {
        return intervalPoll;
    }

    public ConfigurationGroup setIntervalPoll(int intervalPoll) {
        this.intervalPoll = intervalPoll;
        return this;
    }

    public XapiAccount getXapiAccount() {
        return xapiAccount;
    }

    public ConfigurationGroup setXapiAccount(XapiAccount xapiAccount) {
        this.xapiAccount = xapiAccount;
        return this;
    }
}
