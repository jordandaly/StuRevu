package ie.ncirl.jordandaly.classdoor;

/**
 * Created by jdaly on 09/10/2015.
 */
import com.parse.ParseObject;
import com.parse.ParseClassName;


@ParseClassName("College")
public class College extends ParseObject {
    // Ensure that your subclass has a public default constructor
    public College() {
        super();
    }

    // Add a constructor that contains core properties
    public College(String body) {
        super();
        setBody(body);
    }

    // Use getString and others to access fields
    public String getBody() {
        return getString("body");
    }

    // Use put to modify field values
    public void setBody(String value) {
        put("body", value);
    }



}
