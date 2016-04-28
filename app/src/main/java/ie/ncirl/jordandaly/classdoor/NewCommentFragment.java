package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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


                // Associate the comment with the current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                String userId = currentUser.getObjectId();
                Log.d("DEBUG", "userId is " + userId);
                comment.put("User_Id", ParseObject.createWithoutData("_User", userId));

                // Associate the device with a user
                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.put("user", ParseUser.getCurrentUser());
                installation.saveInBackground();


                // Associate the comment with the current review

                comment.put("Review_Id", ParseObject.createWithoutData("Review", reviewId));
                ParsePush push_fav = new ParsePush();
                push_fav.setChannel(reviewId);
                push_fav.setMessage("A new comment has been created for one your favourite Reviews");
                push_fav.sendInBackground();

                //set up query for author of review
                ParseQuery<ParseObject> query = new ParseQuery("Review");
                query.include("User_Id");
                query.getInBackground(reviewId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            String author = object.getParseObject("User_Id").getObjectId();
                            Log.d("DEBUG", "review UserId is " + author);

                            // Create our Installation query
                            ParseQuery pushQuery = ParseInstallation.getQuery();
                            ParseObject user_id = ParseObject.createWithoutData("_User", author);
                            pushQuery.whereEqualTo("user", user_id);

                            // Send push notification to query
                            ParsePush push_author = new ParsePush();
                            push_author.setQuery(pushQuery); // Set our Installation query
                            push_author.setMessage("A new comment has been added to a review that you created");
                            push_author.sendInBackground();

                        } else {
                            // something went wrong
                            Log.d("DEBUG", "something went wrong");
                        }
                    }
                });





                comment.setContent(commentContent.getText().toString());


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

