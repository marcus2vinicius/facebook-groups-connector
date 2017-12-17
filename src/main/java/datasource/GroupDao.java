package datasource;

import domain.Group;
import domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 22/08/2015.
 */
public class GroupDao {

    public Group save(Group group){
        EntityManager em = PesistenceSingleton.getInstance();
        em.getTransaction().begin();
        if(group.getId()==null || group.getId()==0)
            em.persist(group);
        else
            em.merge(group);
        em.getTransaction().commit();
        em.close();
        return group;
    }

    public List<Group> byUser(User user){
        EntityManager em = PesistenceSingleton.getInstance();
        Query q = em.createQuery("select g from Group g where g.User.Id = :user");
        q.setParameter("user", user.getId());
        List<Group> l = (List<Group>) q.getResultList();
        em.close();
        return l == null ? new ArrayList<>() : l;
    }

    public Group byGroupID(String groupID){
        EntityManager em = PesistenceSingleton.getInstance();
        Query q = em.createQuery("select g from Group g where g.GroupId = :groupid");
        q.setParameter("groupid",groupID);
        List<Group> l = (List<Group>) q.getResultList();
        em.close();
        return l == null ? null : l.get(0);
    }

    public List<Group> getAll(){
        EntityManager em = PesistenceSingleton.getInstance();
        Query q = em.createQuery("select g from Group g");
        List<Group> l = (List<Group>) q.getResultList();
        em.close();
        return l == null ? new ArrayList<>() : l;
    }

    public boolean delete(Group group){
        try{
            EntityManager em = PesistenceSingleton.getInstance();
            em.getTransaction().begin();
            em.remove(em.getReference(Group.class,group.getId()));
            em.getTransaction().commit();
            em.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
