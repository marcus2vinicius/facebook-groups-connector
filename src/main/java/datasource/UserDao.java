package datasource;

import domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Created by marcus on 22/08/2015.
 */
public class UserDao {

    public User save(User user){
        EntityManager em = PesistenceSingleton.getInstance();
        try {
            em.getTransaction().begin();
            if (user.getId() == null || user.getId() == 0)
                em.persist(user);
            else
                em.merge(user);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return user;
    }

    public User getUserByEmail(String email){
        User u;
        EntityManager em = PesistenceSingleton.getInstance();
        try {
            Query q = em.createQuery("select u from User u where u.Email = :email");
            q.setParameter("email", email);
            u = (User) q.getSingleResult();
        }catch (Exception e){
           u = null;
        }finally {
            em.close();
        }
        return u;
    }
}
