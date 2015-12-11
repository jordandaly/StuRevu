package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
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


    public String getRating() {
        return getString("Rating");
    }

    public void setRating(String rating) {
        put("Rating", rating);
    }


    public String getStudentType() {
        return getString("Student_Type");
    }

    public void setStudentType(String studentType) {
        put("Student_Type", studentType);
    }

    public String getPros() {
        return getString("Content_Pros");
    }

    public void setPros(String contentPros) {
        put("Content_Pros", contentPros);
    }


    public String getCons() {
        return getString("Content_Cons");
    }

    public void setCons(String contentCons) {
        put("Content_Cons", contentCons);
    }


    public String getAdvice() {
        return getString("Content_Advice");
    }

    public void setAdvice(String contentAdvice) {
        put("Content_Advice", contentAdvice);
    }


    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    // Get the college for this college review
    public String getCollege() {
        return getString("College_Id");
    }

    // Associate each college review with a college
    public void setCollege(String college) {
        put("College_Id", college);
    }


    // Get the college for this course review
    public String getCourse() {
        return getString("Course_Id");
    }

    // Associate each course review with a course
    public void setCourse(String course) {
        put("Course_Id", course);
    }
}
