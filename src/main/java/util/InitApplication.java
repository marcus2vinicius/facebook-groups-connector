package util;
import datasource.PesistenceSingleton;

import javax.servlet.annotation.WebListener;

/**
 * Created by Marcus on 27/08/2015.
 */
@WebListener
public class InitApplication {

    static {
        init();
    }

    //add aqui
    public static void init(){

        Runnable run = new Runnable() {
            @Override
            public void run() {
                PesistenceSingleton.getInstance();
                try {
                    UpListenerGroups.init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(run);
        t.start();
    }
}
