package datasource;

import util.PropertiesProjetct;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.Properties;

/**
 * Created by marcus on 22/08/2015.
 */
public class PesistenceSingleton {
    private static EntityManagerFactory emf = null;
    private static Properties propertiesProject = PropertiesProjetct.getProject();

    public static EntityManager getInstance(){
        if(emf != null && emf.isOpen()){
            return emf.createEntityManager();
        }else {
           if(propertiesProject.getProperty("db").equals("h2")){
               emf = Persistence.createEntityManagerFactory("facebookH2");
            }else if(propertiesProject.getProperty("db").equals("postgres")) {
               Properties p = new Properties();
               p.put("hibernate.connection.url"
                       , "jdbc:postgresql://ec2-54-235-147-211.compute-1.amazonaws.com:5432/d7pgvo9pgmfp84?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
               emf = Persistence.createEntityManagerFactory("facebookPostgres", p);
           }
           return emf.createEntityManager();
        }
    }

}
