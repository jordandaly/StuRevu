package com.sturevu.classdoor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jdaly on 21/04/2016.
 */
@ParseClassName("Comment")
public class Comment extends ParseObject {

    // Ensure that your subclass has a public default constructor
    public Comment() {
        super();
    }

    public String getTitle() {
        return getString("Title");
    }

    public void setTitle(String title) {
        put("Title", title);
    }

    public String getContent() {
        return getString("Content");
    }

    public void setContent(String content) {
        put("Content", content);
    }

    // Get the review for this comment
    public String getReview() {
        return getString("Review_Id");
    }

    // Associate each comment with a review
    public void setReview(String review) {
        put("Review_Id", review);
    }
}
