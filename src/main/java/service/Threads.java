package service;

import java.util.HashMap;

/**
 * Created by NTI-Sistema on 27/08/2015.
 */
public class Threads {
    private static HashMap<Integer,Executor> threads = new HashMap<>();

    public static void add(Integer id_Group, Executor executor){
        threads.put(id_Group,executor);
        executeThread(threads.get(id_Group));
    }

    private static void executeThread(Executor e) {
        Thread t = new Thread(e);
        t.start();
        t.setName("GroupID: "+e.getConfigurationGroup().getGroupID());
        System.out.println("Name_Thread: "+t.getName());
        System.out.println("Id_Thread  : " + t.getId());
    }

    public static void stop(Integer id_Group){
        Executor e = threads.get(id_Group);
        e.setRunning(false);
        threads.remove(id_Group);
    }

    public static boolean contain(Integer id_Group){
        return threads.get(id_Group) != null;
    }
}
