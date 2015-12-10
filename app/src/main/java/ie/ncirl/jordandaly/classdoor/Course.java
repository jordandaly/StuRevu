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


    public String getDescription() {
        return getString("Description");
    }

//    public void setDescription(String description){
//        put("Description", description);
//    }

    public String getModeOfStudy() {
        return getString("Mode_0f_Study");
    }

//    public void setModeOfStudy(String modeOfStudy){
//        put("Mode_Of_Study", modeOfStudy);
//    }


    // Get the college for this course
    public ParseObject getCollegeId() {
        return getParseObject("College_Id");
    }

//    // Associate each course with a college
//    public void setCollegeId(College collegeId) {
//        put("College_Id", collegeId);
//    }
}
