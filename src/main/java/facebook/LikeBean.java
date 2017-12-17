package facebook;

import facebook4j.Comment;
import facebook4j.Like;
import facebook4j.Post;

/**
 * Created by marcus on 20/08/2015.
 */
public class LikeBean {
    private String LikeID;//Id User
    private String GroupID;
    private Like Like;
    private Comment comment;
    private Post post;
    private String urlPost;

    public String getLikeID() {
        return LikeID;
    }

    public void setLikeID(String likeID) {
        LikeID = likeID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public facebook4j.Like getLike() {
        return Like;
    }

    public void setLike(facebook4j.Like like) {
        Like = like;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUrlPost() {
        return urlPost;
    }

    public void setUrlPost(String urlPost) {
        this.urlPost = urlPost;
    }

    @Override
    public String toString() {
        return "LikeBean{" +
                ", LikeID='" + LikeID + '\'' +
                ", GroupID='" + GroupID + '\'' +
                ", URL='" + urlPost + '\'' +
                '}';
    }
}
