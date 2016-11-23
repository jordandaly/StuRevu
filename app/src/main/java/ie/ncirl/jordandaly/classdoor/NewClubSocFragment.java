package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * Created by jdaly on 23/11/2016.
 */
public class NewClubSocFragment extends Fragment {

    String collegeId = NewClubSocActivity.collegeId;


    TextView clubsocName;
    TextView clubsocDescription;
    private Button saveButton;
    private Button cancelButton;
    private Spinner clubsocType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_clubsoc, parent, false);

        clubsocDescription = ((EditText) v.findViewById(R.id.clubsoc_description));
        clubsocName = ((EditText) v.findViewById(R.id.clubsoc_name));

        clubsocType = ((Spinner) v.findViewById(R.id.clubsoc_type_spinner));
        ArrayAdapter<CharSequence> clubsocTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.clubsoc_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        clubsocType.setAdapter(clubsocTypeSpinnerAdapter);


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Club_Soc clubsoc = ((NewClubSocActivity) getActivity()).getCurrentClubSoc();


                // When the user clicks "Save," upload the course to Parse
                // Add data to the course object:
                String clubsocNameInput = clubsocName.getText().toString();
                String s1 = clubsocNameInput.substring(0, 1).toUpperCase();
                String clubsocNameCapitalized = s1 + clubsocNameInput.substring(1);
                clubsoc.setName(clubsocNameCapitalized);


                clubsoc.setDescription(clubsocDescription.getText().toString());
                String clubsocDescriptionInput = clubsocDescription.getText().toString();


                // Add the spinner type

                clubsoc.setType(clubsocType.getSelectedItem().toString());
                String clubsocTypeInput = clubsocType.getSelectedItem().toString();


                //Check if fields not empty
                if (clubsocName.equals("") || clubsocDescription.equals("") || clubsocType.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


                    // Associate the review with the current college
                    if (collegeId != null) {
                        clubsoc.put("College_Id", ParseObject.createWithoutData("College", collegeId));

                        //send push notification and include college name
                        ParseQuery<ParseObject> query = new ParseQuery("College");
                        query.getInBackground(collegeId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String collegeName = object.getString("Name");
                                    ParsePush push = new ParsePush();
                                    push.setChannel(collegeId);
                                    push.setMessage("A new club/society has been created for one of your favourite Colleges: " + collegeName);
                                    push.sendInBackground();
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }


                    // Save the review and return
                    clubsoc.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New Club/Society Saved!"
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
