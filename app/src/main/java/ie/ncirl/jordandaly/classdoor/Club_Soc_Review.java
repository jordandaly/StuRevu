package ie.ncirl.jordandaly.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Club_Soc_Review")
public class Club_Soc_Review extends ParseObject {
    // ...
    /*
    // Associate each club/soc review with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    // Get the user for this club/soc review
    public ParseUser getOwner()  {
        return getParseUser("owner");
    }
    */

    // Ensure that your subclass has a public default constructor
    public Club_Soc_Review() {
        super();
    }

    // Add a constructor that contains core properties
    public Club_Soc_Review(String description) {
        super();
        setDescription(description);
    }


    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        put("description", description);
    }


    // Associate each club/soc review with a club/soc
    public void setClub_Soc(Club_Soc club_soc) {
        put("club_soc", club_soc);
    }

    // Get the club/soc for this club/soc review
    public Club_Soc getClub_Soc()  {
        return (Club_Soc) getParseObject("club_soc");
    }
}
