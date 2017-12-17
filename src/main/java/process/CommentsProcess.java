package process;

import datasource.Receptor;
import datasource.ReceptorErro;
import domain.LogGroup;
import facebook.CommentBean;
import facebook.ConfigurationGroup;
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
public class CommentsProcess extends IProcess{
    private Post post;
    private Comment comment;


    public CommentsProcess(ConfigurationGroup configurationGroup, Post post) throws Exception {
        super(configurationGroup);
        this.post = post;
    }

    public List<Comment> comments(Date since){
        Reading reading = new Reading().limit(Constants.sizeLimit);
        try {
            List<Comment> commentFull = new ArrayList<>();
            ResponseList<Comment> comments = configurationGroup.getFacebook().getPostComments(post.getId(), reading);
            do{
                if(comments!=null && !comments.isEmpty()) {
                    commentFull.addAll(comments);
                    Paging<Comment> page = comments.getPaging();
                    comments = configurationGroup.getFacebook().fetchNext(page);
                }
            }while (comments!=null && !comments.isEmpty());
            return commentFull.stream()
                    .filter(c -> c.getCreatedTime().getTime() >= since.getTime()).collect(Collectors.toList());
        }catch (FacebookException e){
            LogGroup logGroup = new LogGroup();
            logGroup.setContent(e.getMessage());
            logGroup.setGroupID(configurationGroup.getGroupID());
            ReceptorErro.add(logGroup);
            return new ArrayList<Comment>();
        }
    }

    public boolean isUpdated(){
        return comments(configurationGroup.getLastUpdate()).size()>0;
    }

    public void send() throws Exception {
        if(isUpdated()){
            for (Comment c: comments(configurationGroup.getLastUpdate())){
                CommentBean cBean = new CommentBean();
                cBean.setPost(post);
                cBean.setGroupID(configurationGroup.getGroupID());
                cBean.setComment(c);
                Receptor.add(configurationGroup,cBean);
                new LikeProcess(configurationGroup,c, post.getActions().get(0).getLink()).send();
            }
        }

    }

}
