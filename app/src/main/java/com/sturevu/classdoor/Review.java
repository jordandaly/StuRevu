package com.sturevu.classdoor;

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


    public Number getRating() {
        return getNumber("Rating");
    }

    public void setRating(Number rating) {
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



    // Get the college for this college review
    public String getCollege() {
        return getString("College_Id");
    }

    // Associate each college review with a college
    public void setCollege(String college) {
        put("College_Id", college);
    }


    // Get the course for this course review
    public String getCourse() {
        return getString("Course_Id");
    }

    // Associate each course review with a course
    public void setCourse(String course) {
        put("Course_Id", course);
    }


    // Get the clubsoc for this clubsoc review
    public String getClubSoc() {
        return getString("Club_Soc_Id");
    }

    // Associate each clubsoc review with a clubsoc
    public void setClubSoc(String clubsoc) {
        put("Club_Soc_Id", clubsoc);
    }


    // Get the module for this module review
    public String getModule() {
        return getString("Module_Id");
    }

    // Associate each module review with a module
    public void setModule(String module) {
        put("Module_Id", module);
    }
}
