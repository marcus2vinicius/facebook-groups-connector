package datasource;

import domain.Group;
import domain.LogGroup;
import facebook.*;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcus on 21/08/2015.
 */
public class ReceptorErro {
    private static List<LogGroup> logGroupList;
    private static int sizeMax;

    static {
        logGroupList = new ArrayList<>();
        sizeMax = 100;
    }

    public static void add(LogGroup l){
        logGroupList.add(l);
        canSave();
    }

    private static void canSave() {
        if(logGroupList.size()>=100){
           saveBD();
        }
    }

    private static void saveBD(){
        for (LogGroup logGroup: logGroupList){
            GroupDao groupDao = new GroupDao();
            Group g = groupDao.byGroupID(logGroup.getGroupID());
            logGroup.setGroup(g);
        }
        LogGroupDao logGroupDao = new LogGroupDao();
        logGroupDao.save(logGroupList);
        logGroupList.clear();
    }
}
