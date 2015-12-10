package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;
//import com.parse.ParseUser;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Review")
public class Review extends ParseObject {
    // ...
    /*
    // Associate each college review with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    // Get the user for this college review
    public ParseUser getOwner()  {
        return getParseUser("owner");
    }
    */

    // Ensure that your subclass has a public default constructor
    public Review() {
        super();
    }


    public String getTitle() {
        return getString("Title");
    }

    public void setTitle(String title) {
        put("Title", title);
    }

    public Integer getRating() {
        return getInt("Rating");
    }

    public void setRating(Integer rating) {
        put("Rating", rating);
    }

    // Get the college for this college review
    public College getCollege() {
        return (College) getParseObject("College_Id");
    }

    // Associate each college review with a college
    public void setCollege(College college) {
        put("College_Id", college);
    }
}
