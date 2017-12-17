package datasource;

import domain.Facebook;
import domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by marcus on 22/08/2015.
 */
public class FacebookDao {

    public Facebook save(Facebook facebook){
        EntityManager em = PesistenceSingleton.getInstance();
        try {
            em.getTransaction().begin();
            if (facebook.getId() == null || facebook.getId() == 0)
                em.persist(facebook);
            else
                em.merge(facebook);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        return facebook;
    }

    public Facebook getFacebookByUser(User user){
        Facebook f;
        EntityManager em = PesistenceSingleton.getInstance();
        try {
            Query q = em.createQuery("select f from Facebook f where f.User = :user");
            q.setParameter("user", user);
            f = (Facebook) q.getSingleResult();

        }catch (Exception e){
           f = null;
        }finally {
            em.close();
        }
        return f;
    }
}
