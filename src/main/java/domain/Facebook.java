package domain;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.*;
/**
 * Created by marcus on 22/08/2015.
 */
@Entity
public class Facebook {
    @Id
    @GenericGenerator(name="gen_facebook", strategy="org.hibernate.id.IncrementGenerator")
    @GeneratedValue(generator="gen_facebook")
    private Integer Id;
    @Column(nullable = false)
    private String AccessToken;
    @ManyToOne
    private User User;
    private Long ExpireToken;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public domain.User getUser() {
        return User;
    }

    public void setUser(domain.User user) {
        User = user;
    }

    public Long getExpireToken() {
        return ExpireToken;
    }

    public void setExpireToken(Long expireToken) {
        ExpireToken = expireToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Facebook facebook = (Facebook) o;

        return Id.equals(facebook.Id);

    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
