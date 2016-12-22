package com.sturevu.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 19/12/2016.
 */
@ParseClassName("Report")
public class Report extends ParseObject {

    // Ensure that your subclass has a public default constructor
    public Report() {
        super();
    }

    public String getText() {
        return getString("Text");
    }

    public void setText(String text) {
        put("Text", text);
    }


    public String getType() {
        return getString("Type");
    }

    public void setType(String type) {
        put("Type", type);
    }


    // Get the comment for this reply
    public String getAuthor() {
        return getParseObject("User_Id").getString("username");
    }
}
