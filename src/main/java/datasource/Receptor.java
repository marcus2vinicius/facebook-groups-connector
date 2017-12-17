package datasource;

import domain.XapiAccount;
import facebook.*;
import facebook4j.Comment;
import facebook4j.Group;
import facebook4j.Like;
import facebook4j.Post;
import xapi.SenderXAPI;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcus on 21/08/2015.
 */
public class Receptor {

    public static void add(ConfigurationGroup cg, PostBean p){
        DataStatic.add(cg.getGroupID(),formtOutput(cg, p.toString()));
        System.out.println("GROUPID: "+cg.getGroupID()+" - "+formtOutput(cg,p.toString()));
        if(validade(cg.getXapiAccount()))
        try {
            new SenderXAPI(cg.getXapiAccount()).send(p);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void add(ConfigurationGroup cg, GroupBean g){
        DataStatic.add(cg.getGroupID(),formtOutput(cg, g.toString()));
        System.out.println("GROUPID: "+cg.getGroupID()+" - "+formtOutput(cg, g.toString()));
        if(validade(cg.getXapiAccount()))
            try {
                new SenderXAPI(cg.getXapiAccount()).send(g);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void add(ConfigurationGroup cg, LikeBean l){
        DataStatic.add(cg.getGroupID(), formtOutput(cg, l.toString()));
        System.out.println("GROUPID: "+cg.getGroupID()+" - "+formtOutput(cg,l.toString()));
        if(validade(cg.getXapiAccount()))
        try {
            new SenderXAPI(cg.getXapiAccount()).send(l);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static boolean validade(XapiAccount x){
        return x!=null && x.getId()!=null &&x.getId()>0;
    }
    public static void add(ConfigurationGroup cg, CommentBean c){
        DataStatic.add(cg.getGroupID(),formtOutput(cg,c.toString()));
        System.out.println("GROUPID: "+cg.getGroupID()+" - "+formtOutput(cg,c.toString()));
        if(validade(cg.getXapiAccount()))
        try {
            new SenderXAPI(cg.getXapiAccount()).send(c);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String dateFormated(Date d){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dt1.format(d);
    }
    private static String formtOutput(ConfigurationGroup c, String s){
        if(c.getXapiAccount()!=null)
            return "["+c.getLastUpdate()+"] - ["+new Date()+"] "+c.getXapiAccount().getUser().getName()+" - "+s;
        else return "["+new Date()+"] - "+s;
    }
}
