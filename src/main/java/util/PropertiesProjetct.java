package util;

import java.io.*;
import java.util.Properties;

/**
 * Created by Marcus on 28/08/2015.
 */
public class PropertiesProjetct {

    public static Properties getFacebook(){
        return new PropertiesProjetct().get("facebook4j.properties");
    }

    public static Properties getProject(){
        return new PropertiesProjetct().get("project.properties");
    }

    private Properties get(String nameFile){
        ClassLoader classLoader = getClass().getClassLoader();
        FileInputStream file = null;
        try {
            String url = classLoader.getResource(nameFile).getFile();
            System.out.println();
            file = new FileInputStream(url);
            Properties p = new Properties();
            p.load(file);
            return p;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

