package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by marcus on 22/08/2015.
 */
@Entity
public class Group {
    @Id
    @GenericGenerator(name="gen_group", strategy="org.hibernate.id.IncrementGenerator")
    @GeneratedValue(generator="gen_group")
    private Integer Id;
    private String Name;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private User User;
    @Column(nullable = false)
    private String GroupId;
    @Column(length = 2000)
    private String Description;
    private Date UpdatedTime;
    private Integer IntervalPoll;
    private Date Started;
    private Date LastVerify;
    private boolean Runned;
    private Date Created;

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

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        UpdatedTime = updatedTime;
    }

    public Integer getIntervalPoll() {
        return IntervalPoll;
    }

    public Date getStarted() {
        return Started;
    }

    public void setStarted(Date started) {
        Started = started;
    }

    public void setIntervalPoll(Integer intervalPoll) {
        IntervalPoll = intervalPoll;
    }

    public Date getLastVerify() {
        return LastVerify;
    }

    public void setLastVerify(Date lastVerify) {
        LastVerify = lastVerify;
    }

    public boolean isRunned() {
        return Runned;
    }

    public void setRunned(boolean runned) {
        Runned = runned;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date created) {
        Created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return Id.equals(group.Id);

    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }

    public boolean isSalved(){
        return Id != null && Id > 0;
    }
}
