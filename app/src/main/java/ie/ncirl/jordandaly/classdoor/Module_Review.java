package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Module_Review")
public class Module_Review extends ParseObject {
    // ...
    /*
    // Associate each module review with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    // Get the user for this module review
    public ParseUser getOwner()  {
        return getParseUser("owner");
    }
    */

    // Ensure that your subclass has a public default constructor
    public Module_Review() {
        super();
    }

    // Add a constructor that contains core properties
    public Module_Review(String description) {
        super();
        setDescription(description);
    }


    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }


    // Associate each module review with a module
    public void setModule(Module module) {
        put("module", module);
    }

    // Get the module for this module review
    public Module getModule() {
        return (Module) getParseObject("module");
    }
}
