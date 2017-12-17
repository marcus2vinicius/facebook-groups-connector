package domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;
/**
 * Created by marcus on 22/08/2015.
 */
@Entity
public class User {
    @Id
    @GenericGenerator(name="gen_user", strategy="org.hibernate.id.IncrementGenerator")
    @GeneratedValue(generator="gen_user")
    private Integer Id;
    @Column(nullable = false)
    private String Email;
    private String Name;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Id.equals(user.Id);

    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
