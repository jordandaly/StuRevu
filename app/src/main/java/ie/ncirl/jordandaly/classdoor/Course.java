package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Course")
public class Course extends ParseObject {
    // ...

    // Ensure that your subclass has a public default constructor
    public Course() {
        super();
    }

    // Add a constructor that contains core properties
    public Course(String body) {
        super();
        setBody(body);
    }


    public String getBody(){
        return getString("body");
    }

    public void setBody(String body){
        put("body", body);
    }


    // Associate each course with a college
    public void setCollege(College college) {
        put("college", college);
    }

    // Get the college for this course
    public College getCollege()  {
        return (College) getParseObject("college");
    }
}
