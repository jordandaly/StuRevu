package com.sturevu.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by jdaly on 11/12/2015.
 */
public class NewCommentFragment extends Fragment {

    String reviewId = NewCommentActivity.reviewId;
    String collegeId = NewCommentActivity.collegeId;
    String courseId = NewCommentActivity.courseId;
    String clubsocId = NewCommentActivity.clubsocId;
    String moduleId = NewCommentActivity.moduleId;




    private Button saveButton;
    private Button cancelButton;
    private TextView commentTitle;
    private TextView commentContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_comment, parent, false);

        commentTitle = ((EditText) v.findViewById(R.id.comment_title));
        commentContent = ((EditText) v.findViewById(R.id.comment_content));


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Comment comment = ((NewCommentActivity) getActivity()).getCurrentComment();

                // When the user clicks "Save," upload the review to Parse
                // Add data to the review object:
                comment.setTitle(commentTitle.getText().toString());
                String commentTitleInput = commentTitle.getText().toString();

                comment.setContent(commentContent.getText().toString());
                String commentContentInput = commentContent.getText().toString();

                //Check if fields not empty
                if (commentTitleInput.equals("") || commentContentInput.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


                    // Associate the comment with the current user
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String userId = currentUser.getObjectId();
                    Log.d("DEBUG", "userId is " + userId);
                    comment.put("User_Id", ParseObject.createWithoutData("_User", userId));

                    // Associate the device with a user
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("user", ParseUser.getCurrentUser());
                    installation.saveInBackground();


                    // Associate the comment with the current review, college, course, clubsoc, module
                    if (reviewId != null) {
                        comment.put("Review_Id", ParseObject.createWithoutData("Review", reviewId));
                    }
                    else if (collegeId != null) {
                        comment.put("College_Id", ParseObject.createWithoutData("College", collegeId));
                    }
                    else if (courseId != null) {
                        comment.put("CourseId", ParseObject.createWithoutData("Course", courseId));
                    }
                    else if (clubsocId != null) {
                        comment.put("Club_Soc_Id", ParseObject.createWithoutData("Club_Soc", clubsocId));
                    }
                    else if (moduleId != null) {
                        comment.put("Module_Id", ParseObject.createWithoutData("Module", moduleId));
                    }
//                ParsePush push_fav = new ParsePush();
//                push_fav.setChannel(reviewId);
//                push_fav.setMessage("A new comment has been created for one your favourite Reviews");
//                push_fav.sendInBackground();

                    //send fav college/course review push notification and include course description and college name
                    ParseQuery<ParseObject> query_fav = new ParseQuery("Review");
                    query_fav.include("College_Id");
                    query_fav.include("Course_Id");
                    query_fav.include("Course_Id.College_Id");
                    query_fav.getInBackground(reviewId, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                // object will be your review
                                String reviewTitle = object.getString("Title");

                                if (object.getParseObject("Course_Id") != null) {
                                    String courseDesc = object.getParseObject("Course_Id").getString("Description");
                                    String collegeInitials = object.getParseObject("Course_Id").getParseObject("College_Id").getString("Initials");

                                    ParsePush push_fav_course = new ParsePush();
                                    push_fav_course.setChannel(reviewId);
                                    push_fav_course.setMessage("A new comment has been created for one your favourite Course Reviews, Title: " + reviewTitle + " for " + courseDesc + " at " + collegeInitials);
                                    push_fav_course.sendInBackground();
                                } else if (object.getParseObject("College_Id") != null) {
                                    String collegeInitials = object.getParseObject("College_Id").getString("Initials");


                                    ParsePush push_fav_college = new ParsePush();
                                    push_fav_college.setChannel(reviewId);
                                    push_fav_college.setMessage("A new comment has been created for one your favourite College Reviews, Title: " + reviewTitle + " at " + collegeInitials);
                                    push_fav_college.sendInBackground();
                                } else {
                                    // something went wrong
                                }
                            }
                        }
                    });

                    //send push notification to author of college/course review
                    ParseQuery<ParseObject> query_author = new ParseQuery("Review");
                    query_author.include("User_Id");
                    query_author.include("College_Id");
                    query_author.include("Course_Id");
                    query_author.include("Course_Id.College_Id");
                    query_author.getInBackground(reviewId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                String author = object.getParseObject("User_Id").getObjectId();
                                Log.d("DEBUG", "review UserId is " + author);
                                // object will be your review
                                String reviewTitle = object.getString("Title");
                                Log.d("DEBUG", "review title is " + reviewTitle);


                                if (object.getParseObject("Course_Id") != null) {
                                    String courseDesc = object.getParseObject("Course_Id").getString("Description");
                                    String collegeInitials = object.getParseObject("Course_Id").getParseObject("College_Id").getString("Initials");

                                    // Create our Installation query
                                    ParseQuery pushQuery = ParseInstallation.getQuery();
                                    ParseObject user_id = ParseObject.createWithoutData("_User", author);
                                    pushQuery.whereEqualTo("user", user_id);

                                    // Send push notification to query
                                    ParsePush push_author_course = new ParsePush();
                                    push_author_course.setQuery(pushQuery); // Set our Installation query
                                    push_author_course.setMessage("A new comment has been added to a Course Review that you created, Title: " + reviewTitle + " for " + courseDesc + " at " + collegeInitials);
                                    push_author_course.sendInBackground();

                                } else if (object.getParseObject("College_Id") != null) {
                                    String collegeInitials = object.getParseObject("College_Id").getString("Initials");

                                    // Create our Installation query
                                    ParseQuery pushQuery = ParseInstallation.getQuery();
                                    ParseObject user_id = ParseObject.createWithoutData("_User", author);
                                    pushQuery.whereEqualTo("user", user_id);

                                    // Send push notification to query
                                    ParsePush push_author_college = new ParsePush();
                                    push_author_college.setQuery(pushQuery); // Set our Installation query
                                    push_author_college.setMessage("A new comment has been added to a College Review that you created, Title: " + reviewTitle + " at " + collegeInitials);
                                    push_author_college.sendInBackground();

                                }

                            } else {
                                // something went wrong
                                Log.d("DEBUG", "something went wrong");
                            }
                        }
                    });


                    // Save the review and return
                    comment.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New Comment Saved!"
                                        , Toast.LENGTH_LONG).show();

                                getActivity().setResult(Activity.RESULT_OK);
                                getActivity().finish();

                            } else {
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                }
            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });


        return v;
    }


}

