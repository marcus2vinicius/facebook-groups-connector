package datasource;

import domain.User;
import domain.XapiAccount;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by marcus on 22/08/2015.
 */
public class XapiAccountDao {

    public XapiAccount save(XapiAccount xapiAccount){
        EntityManager em = PesistenceSingleton.getInstance();
        try {
            em.getTransaction().begin();
            if (xapiAccount.getId() == null || xapiAccount.getId() == 0)
                em.persist(xapiAccount);
            else
                em.merge(xapiAccount);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }finally {
            em.close();
        }
        return xapiAccount;
    }

    public XapiAccount byUser(User user){
        XapiAccount x;
        EntityManager em = PesistenceSingleton.getInstance();
        try {
            Query q = em.createQuery("select x from XapiAccount x where x.User = :user");
            q.setParameter("user", user);
            x = (XapiAccount) q.getSingleResult();
        }catch (Exception e){
           x = null;
        }finally {
            em.close();
        }
        return x;
    }
}
