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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
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


                // Associate the review with the current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                String userId = currentUser.getObjectId();
                Log.d("DEBUG", "userId is " + userId);
                comment.put("User_Id", ParseObject.createWithoutData("_User", userId));


                // Associate the review with the current college

                comment.put("Review_Id", ParseObject.createWithoutData("Review", reviewId));
                ParsePush push = new ParsePush();
                push.setChannel(reviewId);
                push.setMessage("A new comment has been created for one your favourite Reviews");
                push.sendInBackground();


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

