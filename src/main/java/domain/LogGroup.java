package domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by marcus on 22/08/2015.
 */
@Entity
public class LogGroup {
    @Id
    @GenericGenerator(name="gen_loggroup", strategy="org.hibernate.id.IncrementGenerator")
    @GeneratedValue(generator="gen_loggroup")
    private Integer Id;
    @Column(length = 4000)
    private String Content;
    @ManyToOne
    private Group Group;
    @Transient
    private String GroupID;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Group getGroup() {
        return Group;
    }

    public void setGroup(Group group) {
        Group = group;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogGroup facebook = (LogGroup) o;

        return Id.equals(facebook.Id);

    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
