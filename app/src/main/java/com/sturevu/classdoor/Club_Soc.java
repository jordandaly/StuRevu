package com.sturevu.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 09/10/2015.
 */
@ParseClassName("Club_Soc")
public class Club_Soc extends ParseObject {
    // ...

    // Ensure that your subclass has a public default constructor
    public Club_Soc() {
        super();
    }


    public String getName() {
        return getString("Name");
    }

    public void setName(String name) {
        put("Name", name);
    }


    public String getDescription() {
        return getString("Description");
    }

    public void setDescription(String description) {
        put("Description", description);
    }


    public String getType() {
        return getString("Type");
    }

    public void setType(String type) {
        put("Type", type);
    }

    // Get the college for this club/soc
    public College getCollege()  {
        return (College) getParseObject("College_Id");
    }

    // Associate each club/soc with a college
    public void setCollege(College college) {
        put("College_Id", college);
    }
}
