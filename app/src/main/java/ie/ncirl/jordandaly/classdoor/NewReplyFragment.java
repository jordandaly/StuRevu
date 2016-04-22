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
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by jdaly on 22/04/2016.
 */
public class NewReplyFragment extends Fragment {

    String commentId = NewReplyActivity.commentId;


    private Button saveButton;
    private Button cancelButton;
    private TextView replyContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_reply, parent, false);


        replyContent = ((EditText) v.findViewById(R.id.reply_content));


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Reply reply = ((NewReplyActivity) getActivity()).getCurrentReply();

                // When the user clicks "Save," upload the review to Parse
                // Add data to the review object:


                // Associate the review with the current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                String userId = currentUser.getObjectId();
                Log.d("DEBUG", "userId is " + userId);
                reply.put("User_Id", ParseObject.createWithoutData("_User", userId));


                // Associate the review with the current college

                reply.put("Comment_Id", ParseObject.createWithoutData("Comment", commentId));


                reply.setContent(replyContent.getText().toString());


                // Save the review and return
                reply.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getActivity().getApplicationContext(), "New Reply Saved!"
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
