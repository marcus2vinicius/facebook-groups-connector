package process;

import datasource.Receptor;
import datasource.ReceptorErro;
import domain.LogGroup;
import facebook.ConfigurationGroup;
import facebook.LikeBean;
import facebook4j.*;
import util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marcus on 15/08/2015.
 */
public class LikeProcess extends IProcess{
    private Post post;
    private Comment comment;
    private String url;

    public LikeProcess(ConfigurationGroup configurationGroup, Post post) throws Exception {
        super(configurationGroup);
        this.post = post;
        this.url = post.getActions().get(0).getLink();
    }

    public LikeProcess(ConfigurationGroup configurationGroup, Comment comment, String url) throws Exception {
        super(configurationGroup);
        this.comment = comment;
        this.url = url;
    }

    public List<Like> getlikesBySince(Date since){
        if(comment != null)
            return getLikesByCommentAndSince(since);
        else if(post != null)
            return getlikesByPostAndSince(since);
        else return new ArrayList<Like>();
    }

    private List<Like> getlikesByPostAndSince(Date since){
        Reading reading = new Reading().since(since).limit(Constants.sizeLimit);
        try {
            List<Like> likeFull = new ArrayList<>();
            ResponseList<Like> likes = configurationGroup.getFacebook().getPostLikes(post.getId(), reading);
            do{
                if(likes!=null && !likes.isEmpty()) {
                    likeFull.addAll(likes);
                    Paging<Like> page = likes.getPaging();
                    likes = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (likes!=null && !likes.isEmpty());
            return likeFull;
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Like>();
        }
    }
    private List<Like> getLikesByCommentAndSince(Date since){
        Reading reading = new Reading().since(since).limit(Constants.sizeLimit);
        try {
            List<Like> likeFull = new ArrayList<>();
            ResponseList<Like> likes = configurationGroup.getFacebook().getCommentLikes(comment.getId(), reading);
            do{
                if(likes!=null && !likes.isEmpty()) {
                    likeFull.addAll(likes);
                    Paging<Like> page = likes.getPaging();
                    likes = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (likes!=null && !likes.isEmpty());
            return likeFull;
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Like>();
        }
    }

    public boolean isUpdated(){
        return isUpdatedComment() || isUpdatedPost();
    }

    public boolean isUpdatedComment(){
        if(comment != null){
            return getLikesByCommentAndSince(configurationGroup.getLastUpdate()).size()>0;
        }
        return false;
    }

    public boolean isUpdatedPost(){
        if(post != null)
            return getlikesByPostAndSince(configurationGroup.getLastUpdate()).size()>0;
        return false;
    }

    public void send(){
        if(isUpdated()) {
            if(isUpdatedComment())
                for (Like l : getLikesByCommentAndSince(configurationGroup.getLastUpdate())) {
                    LikeBean lBean = new LikeBean();
                    lBean.setComment(comment);
                    lBean.setLikeID(l.getId());
                    lBean.setLike(l);
                    lBean.setUrlPost(url);
                    lBean.setGroupID(configurationGroup.getGroupID());
                    Receptor.add(configurationGroup,lBean);
                }
            if(isUpdatedPost())
                for (Like l : getlikesByPostAndSince(configurationGroup.getLastUpdate())) {
                    LikeBean lBean = new LikeBean();
                    lBean.setPost(post);
                    lBean.setLikeID(l.getId());
                    lBean.setLike(l);
                    lBean.setUrlPost(url);
                    lBean.setGroupID(configurationGroup.getGroupID());
                    Receptor.add(configurationGroup,lBean);
                }
        }

    }
}
