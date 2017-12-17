package facebook;

import facebook4j.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 20/08/2015.
 */
public class PostBean {
    private String PostID;
    private String GroupID;
    private Post Post;

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public facebook4j.Post getPost() {
        return Post;
    }

    public void setPost(facebook4j.Post post) {
        Post = post;
    }

    @Override
    public String toString() {
        return "Post{" +
                "PostID='" + PostID + '\'' +
                ", GroupID='" + GroupID + '\'' +
                '}';
    }
}
