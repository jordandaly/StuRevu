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

    // Use getString and others to access fields

    // ------------------
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
    public String getCourseLevel() {
        return getString("Course_Level");
    }

    public void setCourseLevel(String courseLevel) {
        put("Course_Level", courseLevel);
    }

    //--------------------
    public String getQualificationType() {
        return getString("Qualification_Type");
    }

    public void setQualificationType(String qualificationType) {
        put("Qualification_Type", qualificationType);
    }

    //--------------------
    public Number getNfqLevel() {
        return getNumber("Level");
    }

    public void setNfqLevel(Number level) {
        put("Level", level);
    }

    //--------------------
    public String getModeOfStudy() {
        return getString("Mode_0f_Study");
    }

    public void setModeOfStudy(String modeOfStudy) {
        put("Mode_0f_Study", modeOfStudy);
    }
    //--------------------

    public String getCourseDuration() {
        return getString("Duration");
    }

    public void setCourseDuration(String duration) {
        put("Duration", duration);
    }

    //--------------------
    public Number getCourseFees() {
        return getNumber("Fees");
    }

    public void setCourseFees(Number courseFees) {
        put("Fees", courseFees);
    }

    //--------------------
    public String getCourseDeptFac() {
        return getString("Department_Faculty");
    }

    public void setCourseDeptFac(String courseDeptFac) {
        put("Department_Faculty", courseDeptFac);
    }

    //--------------------
    public String getCaoCode() {
        return getString("CAO_Code");
    }

    public void setCaoCode(String caoCode) {
        put("CAO_Code", caoCode);
    }




    // Get the college for this course
    public ParseObject getCollegeId() {
        return getParseObject("College_Id");
    }

//    // Associate each course with a college
//    public void setCollegeId(College collegeId) {
//        put("College_Id", collegeId);
//    }
}
