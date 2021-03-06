package com.sturevu.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.HashMap;

/**
 * Created by jdaly on 11/12/2015.
 */
public class NewReviewFragment extends Fragment {

    String collegeId = NewReviewActivity.collegeId;
    String courseId = NewReviewActivity.courseId;
    String clubsocId = NewReviewActivity.clubsocId;
    String moduleId = NewReviewActivity.moduleId;
    RatingBar ratingBar;
    TextView rateDisplay;
    private Button saveButton;
    private Button cancelButton;
    private TextView reviewTitle;
    private Spinner reviewRating;
    private Spinner studentType;
    private TextView contentPros;
    private TextView contentCons;
    private TextView contentAdvice;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_review, parent, false);

        reviewTitle = ((EditText) v.findViewById(R.id.review_title));

        // The spinner lets people assign ratings

//        reviewRating = ((Spinner) v.findViewById(R.id.rating_spinner));
//        ArrayAdapter<CharSequence> ratingSpinnerAdapter = ArrayAdapter
//                .createFromResource(getActivity(), R.array.ratings_array,
//                        android.R.layout.simple_spinner_dropdown_item);
//        reviewRating.setAdapter(ratingSpinnerAdapter);

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        rateDisplay = (TextView) v.findViewById(R.id.ratingDisplay);
        //rateDisplay.setText("Rate:");

        studentType = ((Spinner) v.findViewById(R.id.student_type_spinner));
        ArrayAdapter<CharSequence> studentTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.student_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        studentType.setAdapter(studentTypeSpinnerAdapter);

        contentPros = ((EditText) v.findViewById(R.id.pros));
        contentCons = ((EditText) v.findViewById(R.id.cons));
        contentAdvice = ((EditText) v.findViewById(R.id.advice));


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Review review = ((NewReviewActivity) getActivity()).getCurrentReview();


                // When the user clicks "Save," upload the review to Parse
                // Add data to the review object:
                review.setTitle(reviewTitle.getText().toString());
                String reviewTitleInput = reviewTitle.getText().toString();




                // Add the rating
                //review.setRating(reviewRating.getSelectedItem().toString());
                review.setRating(ratingBar.getRating());

                String ratingValue = String.valueOf(ratingBar.getRating());
                rateDisplay.setText("Rate: " + ratingValue);


                // Add the student type
                review.setStudentType(studentType.getSelectedItem().toString());
                String studentTypeInput = studentType.getSelectedItem().toString();

                //add content pros,cons,advice
                review.setPros(contentPros.getText().toString());
                review.setCons(contentCons.getText().toString());
                review.setAdvice(contentAdvice.getText().toString());

                String contentProsInput = contentPros.getText().toString();
                String contentConsInput = contentCons.getText().toString();
                String contentAdviceInput = contentAdvice.getText().toString();

                //Check if fields not empty
                if (reviewTitleInput.equals("") || ratingValue.equals("") || studentTypeInput.equals("") || contentProsInput.equals("") || contentConsInput.equals("") || contentAdviceInput.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    // Associate the review with the current user
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String userId = currentUser.getObjectId();
                    Log.d("DEBUG", "userId is " + userId);
                    review.put("User_Id", ParseObject.createWithoutData("_User", userId));


//                review.setAuthor(ParseUser.getCurrentUser());

                    // Associate the device with a user
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("user", ParseUser.getCurrentUser());
                    installation.saveInBackground();


                    // Associate the review with the current college
                    if (collegeId != null) {
                        review.put("College_Id", ParseObject.createWithoutData("College", collegeId));

                        //send push notification and include college name
                        ParseQuery<ParseObject> query = new ParseQuery("College");
                        query.getInBackground(collegeId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String collegeName = object.getString("Name");
                                    String message = "A new review has been created for one of your favourite Colleges: " + collegeName;
//                                    ParsePush push = new ParsePush();
//                                    push.setChannel(collegeId);
//                                    push.setMessage("A new review has been created for one of your favourite Colleges: " + collegeName);
//                                    push.sendInBackground();
                                    HashMap<String, Object> params = new HashMap<String, Object>();
                                    params.put("channelId", collegeId);
                                    params.put("message", message);
                                    params.put("useMasterKey", true);
                                    ParseCloud.callFunctionInBackground("sendPushToChannel", params, new FunctionCallback<String>() {
                                        public void done(String success, ParseException e) {
                                            if (e == null) {
                                                // Push sent successfully
                                            }
                                        }
                                    });
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }

                    // Associate the review with the current course
                    if (courseId != null) {
                        review.put("Course_Id", ParseObject.createWithoutData("Course", courseId));

                        //send push notification and include course description and college name
                        ParseQuery<ParseObject> query = new ParseQuery("Course");
                        query.include("College_Id");
                        query.getInBackground(courseId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String courseDesc = object.getString("Description");
                                    String collegeName = object.getParseObject("College_Id").getString("Name");
                                    String message = "A new review has been created for one of your favourite Courses: " + courseDesc + " at " + collegeName;

//                                    ParsePush push = new ParsePush();
//                                    push.setChannel(courseId);
//                                    push.setMessage("A new review has been created for one of your favourite Courses: " + courseDesc + " at " + collegeName);
//                                    push.sendInBackground();
                                    HashMap<String, Object> params = new HashMap<String, Object>();
                                    params.put("channelId", courseId);
                                    params.put("message", message);
                                    params.put("useMasterKey", true);
                                    ParseCloud.callFunctionInBackground("sendPushToChannel", params, new FunctionCallback<String>() {
                                        public void done(String success, ParseException e) {
                                            if (e == null) {
                                                // Push sent successfully
                                            }
                                        }
                                    });
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }

                    // Associate the review with the current clubsoc
                    if (clubsocId != null) {
                        review.put("Club_Soc_Id", ParseObject.createWithoutData("Club_Soc", clubsocId));

                        //send push notification and include course description and college name
                        ParseQuery<ParseObject> query = new ParseQuery("Club_Soc");
                        query.include("College_Id");
                        query.getInBackground(clubsocId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String clubsocName = object.getString("Name");
                                    String collegeName = object.getParseObject("College_Id").getString("Name");
                                    String message = "A new review has been created for one of your favourite Clubs/Societies: " + clubsocName + " at " + collegeName;
//                                    ParsePush push = new ParsePush();
//                                    push.setChannel(clubsocId);
//                                    push.setMessage("A new review has been created for one of your favourite Clubs/Societies: " + clubsocName + " at " + collegeName);
//                                    push.sendInBackground();
                                    HashMap<String, Object> params = new HashMap<String, Object>();
                                    params.put("channelId", clubsocId);
                                    params.put("message", message);
                                    params.put("useMasterKey", true);
                                    ParseCloud.callFunctionInBackground("sendPushToChannel", params, new FunctionCallback<String>() {
                                        public void done(String success, ParseException e) {
                                            if (e == null) {
                                                // Push sent successfully
                                            }
                                        }
                                    });
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }

                    // Associate the review with the current clubsoc
                    if (moduleId != null) {
                        review.put("Module_Id", ParseObject.createWithoutData("Module", moduleId));

                        //send push notification and include course description and college name
                        ParseQuery<ParseObject> query = new ParseQuery("Module");
                        query.include("Course_Id");
                        query.include("Course_Id.College_Id");
                        query.getInBackground(moduleId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String moduleName = object.getString("Name");
                                    String courseDesc = object.getParseObject("Course_Id").getString("Description");
                                    String collegeName = object.getParseObject("Course_Id").getParseObject("College_Id").getString("Name");
                                    String message = "A new review has been created for one of your favourite Modules: " + moduleName + " for " + courseDesc + " at " + collegeName;
//                                    ParsePush push = new ParsePush();
//                                    push.setChannel(moduleId);
//                                    push.setMessage("A new review has been created for one of your favourite Modules: " + moduleName + " for " + courseDesc + " at " + collegeName);
//                                    push.sendInBackground();
                                    HashMap<String, Object> params = new HashMap<String, Object>();
                                    params.put("channelId", moduleId);
                                    params.put("message", message);
                                    params.put("useMasterKey", true);
                                    ParseCloud.callFunctionInBackground("sendPushToChannel", params, new FunctionCallback<String>() {
                                        public void done(String success, ParseException e) {
                                            if (e == null) {
                                                // Push sent successfully
                                            }
                                        }
                                    });
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }


                    // Save the review and return
                    review.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New Review Saved!"
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
