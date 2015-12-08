package ie.ncirl.jordandaly.classdoor;

/**
 * Created by jdaly on 09/10/2015.
 */
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("College")
public class College extends ParseObject {
    // Ensure that your subclass has a public default constructor
    public College() {
        super();
    }


    // Use getString and others to access fields
    public String getName() {
        return getString("Name");
    }

    // Use put to modify field values
    public void setName(String value) {
        put("Name", value);
    }


    public String getCollege_Type() {
        return getString("College_Type");
    }


    public void setCollege_Type(String value) {
        put("College_Type", value);
    }

    public String getInitials() {
        return getString("Initials");
    }


    public void setInitials(String value) {
        put("Initials", value);
    }

    public ParseFile getImageFile() {
        return getParseFile("ImageFile");
    }

    public void setImageFile(ParseFile file) {
        put("ImageFile", file);
    }



}
