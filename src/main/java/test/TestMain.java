package test;

import datasource.PesistenceSingleton;
import org.apache.commons.io.IOUtils;
import util.PropertiesProjetct;

import javax.persistence.EntityManager;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by marcus on 16/08/2015.
 */
public class TestMain {
    public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
        /*String urlBase = "www.facebook.com/groups/";
        String url = "https://www.facebook.com/groups/876058585821115/";
        URI uri = new URI(url);
        String path = uri.getPath(); // split whatever you need
        url = url.substring(urlBase.length());
        path = path.replace("groups","");
        path = path.replace("/","");
        InputStream in = new FileInputStream("src/main/resources/facebook4j.properties");
        Properties pro = new Properties();
        pro.load(in);

        Properties t = PropertiesProjetct.getFacebook();
*/
        Date d = new Date(2015-1900,1,1);
        d.setMonth(new Date().getMonth());
        System.out.println(d);

    }


}
