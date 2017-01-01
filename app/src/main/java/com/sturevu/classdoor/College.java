package com.sturevu.classdoor;

/**
 * Created by jdaly on 09/10/2015.
 */

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;


@ParseClassName("College")
public class College extends ParseObject {
    // Ensure that your subclass has a public default constructor
    public College() {
        super();
    }

    public static ParseQuery<College> getQuery() {
        return ParseQuery.getQuery(College.class);
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

    public void setCollegeType(String value) {
        put("College_Type", value);
    }

    public String getInitials() {
        return getString("Initials");
    }

    public void setInitials(String value) {
        put("Initials", value);
    }

    public String getCountry() {
        return getString("Country");
    }

    public void setCountry(String value) {
        put("Country", value);
    }

    public ParseFile getImageFile() {
        return getParseFile("ImageFile");
    }

    public void setImageFile(ParseFile file) {
        put("ImageFile", file);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("Geolocation");
    }

    public void setLocation(ParseGeoPoint value) {
        put("Geolocation", value);
    }



}
