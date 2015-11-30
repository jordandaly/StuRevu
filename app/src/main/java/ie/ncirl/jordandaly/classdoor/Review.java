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

    // Add a constructor that contains core properties
    public Review(String description) {
        super();
        setDescription(description);
    }


    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    // Get the college for this college review
    public College getCollege() {
        return (College) getParseObject("college");
    }

    // Associate each college review with a college
    public void setCollege(College college) {
        put("college", college);
    }
}
