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

    /// ------------------
    public String getName() {
        return getString("Name");
    }

    public void setName(String name) {
        put("Name", name);
    }

    //--------------------
    public String getDescription() {
        return getString("Description");
    }

    public void setDescription(String description) {
        put("Description", description);
    }

    //--------------------
    public String getType() {
        return getString("Type");
    }

    public void setType(String type) {
        put("Type", type);
    }

    //--------------------
    public String getCourseYear() {
        return getString("Course_Year");
    }

    public void setCourseYear(String courseyear) {
        put("Course_Year", courseyear);
    }

    // Get the course for this module
    public Course getCourse()  {
        return (Course) getParseObject("Course_Id");
    }

    // Associate each module with a course
    public void setCourse(Course course) {
        put("Course_Id", course);
    }
}
