package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 21/04/2016.
 */
@ParseClassName("Reply")
public class Reply extends ParseObject {

    // Ensure that your subclass has a public default constructor
    public Reply() {
        super();
    }


    public String getContent() {
        return getString("Content");
    }

    public void setContent(String content) {
        put("Content", content);
    }

    // Get the comment for this reply
    public String getComment() {
        return getString("Comment_Id");
    }

    // Associate each reply with a comment
    public void setComment(String comment) {
        put("Comment_Id", comment);
    }
}
