package datasource;

import domain.Group;
import domain.LogGroup;
import domain.User;
import sun.rmi.runtime.Log;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 22/08/2015.
 */
public class LogGroupDao {

    public LogGroup save(LogGroup logGroup){
        EntityManager em = PesistenceSingleton.getInstance();
        em.getTransaction().begin();
        if(logGroup.getId()==null || logGroup.getId()==0)
            em.persist(logGroup);
        else
            em.merge(logGroup);
        em.getTransaction().commit();
        em.close();
        return logGroup;
    }

    public void save(List<LogGroup> logGroupList){
        EntityManager em = PesistenceSingleton.getInstance();
        em.getTransaction().begin();
        for (LogGroup l : logGroupList) {
            if (l.getId() == null || l.getId() == 0)
                em.persist(l);
            else
                em.merge(l);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<LogGroup> byGroup(Group group){
        EntityManager em = PesistenceSingleton.getInstance();
        Query q = em.createQuery("select l from LogGroup l where l.Group = :group");
        q.setParameter("group",group);
        List<LogGroup> l = (List<LogGroup>) q.getResultList();
        em.close();
        return l == null ? new ArrayList<>() : l;
    }
}
