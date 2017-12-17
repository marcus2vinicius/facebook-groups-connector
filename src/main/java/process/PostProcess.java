package process;

import datasource.Receptor;
import datasource.ReceptorErro;
import domain.LogGroup;
import facebook.ConfigurationGroup;
import facebook.PostBean;
import facebook4j.*;
import util.Constants;
import util.SystemMessages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcus on 15/08/2015.
 */
public class PostProcess extends IProcess {


    public PostProcess(ConfigurationGroup configurationGroup) throws Exception {
        super(configurationGroup);
    }


    public List<Like> likes(Date since){
        return new ArrayList<Like>();
    }

    public boolean isUpdated(){
        return getPostBySince(configurationGroup.getLastUpdate()).size()>0;
    }

    public List<Post> getPostBySince(Date since){
        try {
            Reading readinPost = new Reading().fields
                    ("created_time,updated_time,from,type,description,message,name,to,actions,link")
                    .limit(Constants.sizeLimit)
                    .since(since);
            List<Post> postFull = new ArrayList<>();
            ResponseList<Post> posts = configurationGroup.getFacebook().getGroupFeed(configurationGroup.getGroupID(), readinPost);
            do{
                if(posts!=null && !posts.isEmpty()) {
                    postFull.addAll(posts);
                    Paging<Post> page = posts.getPaging();
                    posts = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (posts!=null && !posts.isEmpty());
            return postFull;
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Post>();
        }
    }

    public List<Post> getPostByCreated(Date created){
        try {
            Reading readinPost = new Reading().fields
                    ("created_time,updated_time,from,type,description,message,name,to,actions,link")/*.filter("updated_time")*/
                    .limit(Constants.sizeLimit)
                    .since(created);
            List<Post> postFull = new ArrayList<>();
            ResponseList<Post> posts = configurationGroup.getFacebook().getGroupFeed(configurationGroup.getGroupID(), readinPost);
            do{
                if(posts!=null && !posts.isEmpty()) {
                    postFull.addAll(posts);
                    Paging<Post> page = posts.getPaging();
                    posts = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (posts!=null && !posts.isEmpty());
            return postFull.stream()
                    .filter(p -> p.getCreatedTime().getTime() > created.getTime()).collect(Collectors.toList());
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Post>();
        }
    }

    public List<Post> getPostByCreated(Date created, Date until){
        try {
            Reading readinPost = new Reading().fields
                    ("created_time")
                    .limit(Constants.sizeLimit)
                    .since(created)
                    .until(until);
            List<Post> postFull = new ArrayList<>();
            ResponseList<Post> posts = configurationGroup.getFacebook().getGroupFeed(configurationGroup.getGroupID(), readinPost);
            do{
                if(posts!=null && !posts.isEmpty()) {
                    postFull.addAll(posts);
                    Paging<Post> page = posts.getPaging();
                    posts = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (posts!=null && !posts.isEmpty());
            return postFull.stream()
                    .filter(p -> p.getCreatedTime().getTime() > created.getTime()).collect(Collectors.toList());
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Post>();
        }
    }


    public boolean containPostNew(Date since){
        return getPostByCreated(since).size() > 0;
    }

    public List<Post> postsUpdated(){
        try {
            Reading readinPost = new Reading().fields
                    ("created_time,updated_time,from,type,description,message,name,to,actions,link")
                    .limit(Constants.sizeLimit)
                    .since(configurationGroup.getLastUpdate());
            List<Post> postFull = new ArrayList<>();
            ResponseList<Post> posts = configurationGroup.getFacebook().getGroupFeed(configurationGroup.getGroupID(), readinPost);
            do{
                if(posts!=null && !posts.isEmpty()) {
                    postFull.addAll(posts);
                    Paging<Post> page = posts.getPaging();
                    posts = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (posts!=null && !posts.isEmpty());
            return postFull;
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Post>();
        }

    }

    public void send() throws Exception {
        if(isUpdated()) {
            //Criados nesta data
            if(containPostNew(configurationGroup.getLastUpdate())){
                for (Post p : getPostByCreated(configurationGroup.getLastUpdate())){
                    PostBean pBean = new PostBean();
                    pBean.setGroupID(configurationGroup.getGroupID());
                    pBean.setPostID(p.getId());
                    pBean.setPost(p);
                    Receptor.add(configurationGroup, pBean);
                }
            }

            //Post ja enviados nao precisar enviar novamente
            for (Post p: getPostBySince(configurationGroup.getLastUpdate())){
                new CommentsProcess(configurationGroup,p).send();
            }
            for (Post p: getPostBySince(configurationGroup.getLastUpdate())){
                new LikeProcess(configurationGroup,p).send();
            }
        }
    }

}
