package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;
//import com.parse.ParseUser;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Course_Review")
public class Course_Review extends ParseObject {
    // ...
    /*
    // Associate each course review with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    // Get the user for this course review
    public ParseUser getOwner()  {
        return getParseUser("owner");
    }
    */

    // Ensure that your subclass has a public default constructor
    public Course_Review() {
        super();
    }

    // Add a constructor that contains core properties
    public Course_Review(String description) {
        super();
        setDescription(description);
    }


    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        put("description", description);
    }


    // Associate each course review with a course
    public void setCourse(Course course) {
        put("course", course);
    }

    // Get the course for this course review
    public Course getCourse()  {
        return (Course) getParseObject("course");
    }
}
