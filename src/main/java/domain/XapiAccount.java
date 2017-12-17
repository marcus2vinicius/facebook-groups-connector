package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by marcus on 22/08/2015.
 */
@Entity
public class XapiAccount {
    @Id
    @GenericGenerator(name="gen_xapiaccount", strategy="org.hibernate.id.IncrementGenerator")
    @GeneratedValue(generator="gen_xapiaccount")
    private Integer Id;
    private String ServerUrl;
    private String Version;
    private String UserName;
    private String Password;
    @OneToOne
    private User User;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public domain.User getUser() {
        return User;
    }

    public void setUser(domain.User user) {
        User = user;
    }

    public String getServerUrl() {
        return ServerUrl;
    }

    public void setServerUrl(String serverUrl) {
        ServerUrl = serverUrl;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XapiAccount facebook = (XapiAccount) o;

        return Id.equals(facebook.Id);

    }
    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
