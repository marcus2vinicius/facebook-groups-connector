package facebook;

import facebook4j.Comment;
import facebook4j.Post;

import java.util.List;

/**
 * Created by marcus on 20/08/2015.
 */
public class CommentBean {
    private String GroupID;
    private facebook4j.Post Post;
    private Comment Comment;

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }


    public facebook4j.Comment getComment() {
        return Comment;
    }

    public void setComment(facebook4j.Comment comment) {
        Comment = comment;
    }

    public facebook4j.Post getPost() {
        return Post;
    }

    public void setPost(facebook4j.Post post) {
        Post = post;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                ", GroupID='" + GroupID + '\'' +
                ", CommentID='" + Comment.getId() + '\'' +
                '}';
    }
}
