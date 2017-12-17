package test;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;
import datasource.GroupDao;
import datasource.UserDao;
import domain.Facebook;
import domain.Group;
import domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Properties;

/**
 * Created by marcus on 22/08/2015.
 */
public class TestJpa {
    public static void main(String[] args) {
/*        UserDao userDao = new UserDao();
        User u = new User();
        u.setEmail("dasdas");
        userDao.save(u);
        User u = userDao.getUserByEmail("42343asddas");
        userDao.save(u);*/

/*     UserDao userDao = new UserDao();
        User u = new User();
        u.setEmail("sdasdsdsadsaa");
        u.setName("marcus teste");
        Facebook f = new Facebook();
        //f.setUser(u);
        f.setAccessToken("34234312321");
        userDao.save(u);
/*
        GroupDao gd = new GroupDao();
        u.setId(1);
        List<Group> groups = gd.byUser(u);
        groups = gd.getAll();
*/
        Properties p = new Properties();
        p.put("hibernate.connection.url"
                , "jdbc:postgresql://dasdffdgdfgdf.compute-1.amazonaws.com:5432/fdsfsdfdsfsd?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("facebookPostgres",p);

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("facebookUnit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = new User();
        /*u.setEmail("masa");
        u.setEmail("sdasdsdsadsaa");
        em.persist(u);*/
        u = em.find(User.class,1);
        em.getTransaction().commit();
        emf.close();
    }
}
