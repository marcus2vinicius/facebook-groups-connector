package util;

import java.io.File;

/**
 * Created by marcus on 01/09/2015.
 */
public class UrlDataBase {

    public static String geturlFullH2(){
        String urlValue = "jdbc:h2:"+getPath()+getDirecotyDatabaseH2();
        urlValue = urlValue.replace("\\","/");
        return urlValue;
    }

    private static String getPath(){
        return new File("").getAbsolutePath();
    }

    private static String getDirecotyDatabaseH2(){
        return "/database/database";
    }
}
