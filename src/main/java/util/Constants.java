package util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by marcus on 16/08/2015.
 */
public class Constants {
    public static String permissions = "public_profile,email,user_friends,user_managed_groups,user_about_me,user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,\n" +
            "user_education_history,user_events,user_games_activity,user_hometown,user_likes,user_location,user_managed_groups,user_photos,user_posts,user_relationships,\n" +
            "user_relationship_details,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history," +
            "read_custom_friendlists,read_insights,read_page_mailboxes,manage_pages,publish_pages,publish_actions,rsvp_event";
    public static int timePool = 1000;/**60*1;*/
    public static int sizeLimit = 1000;
}
