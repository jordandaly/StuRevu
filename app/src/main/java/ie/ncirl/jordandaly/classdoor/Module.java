package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Module")
public class Module extends ParseObject {
    // ...

    // Ensure that your subclass has a public default constructor
    public Module() {
        super();
    }

    // Add a constructor that contains core properties
    public Module(String body) {
        super();
        setBody(body);
    }


    public String getBody(){
        return getString("body");
    }

    public void setBody(String body){
        put("body", body);
    }


    // Associate each module with a course
    public void setCourse(Course course) {
        put("course", course);
    }

    // Get the course for this module
    public Course getCourse()  {
        return (Course) getParseObject("course");
    }
}
