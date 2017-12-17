package test;

import java.io.File;
import java.util.Properties;

/**
 * Created by Marcus on 24/08/2015.
 */
/*
 Past in Persistence.xml
 attribute url
 */
public class UrlDataBaseH2 {
    public static void main(String[] args) {
        System.out.println(geturlFull());
    }

    public static String geturlFull(){
        String urlValue = "jdbc:h2:"+getPath()+getDirecotyDatabase();
        urlValue = urlValue.replace("\\","/");
        return urlValue;
    }

    private static String getPath(){
        return new File("").getAbsolutePath();
    }

    private static String getDirecotyDatabase(){
        return "/database/database";
    }
}
