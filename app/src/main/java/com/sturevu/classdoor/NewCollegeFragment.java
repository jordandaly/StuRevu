package com.sturevu.classdoor;

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

import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.SaveCallback;

/**
 * Created by jdaly on 15/10/2016.
 */
public class NewCollegeFragment extends Fragment {


    private Button saveButton;
    private Button cancelButton;
    private TextView collegeName;
    private Spinner collegeType;
    private TextView collegeInitials;
    private TextView collegeLatitude;
    private TextView collegeLongitude;
    private ParseImageView collegePreview;
    private String latitudeString = "";
    private String longitudeString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_college, parent, false);

        collegeName = ((EditText) v.findViewById(R.id.college_name));


        collegeType = ((Spinner) v.findViewById(R.id.college_type_spinner));
        ArrayAdapter<CharSequence> studentTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.college_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        collegeType.setAdapter(studentTypeSpinnerAdapter);

        collegeInitials = ((EditText) v.findViewById(R.id.college_initials));

//        collegeLatitude = ((EditText) v.findViewById(R.id.college_latitude));
//        collegeLongitude = ((EditText) v.findViewById(R.id.college_longitude));


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                College college = ((NewCollegeActivity) getActivity()).getCurrentCollege();


                // When the user clicks "Save," upload the college to Parse
                // Add data to the college object:
                college.setName(collegeName.getText().toString());
                String collegeNameInput = collegeName.getText().toString();


                // Add the college type
                college.setCollege_Type(collegeType.getSelectedItem().toString());
                String collegeTypeInput = collegeType.getSelectedItem().toString();

                //add initials, latitude, longitude
                college.setInitials(collegeInitials.getText().toString());
                String collegeInitialsInput = collegeInitials.getText().toString();
//                latitudeString = collegeLatitude.getText().toString();
//                longitudeString = collegeLongitude.getText().toString();
//
//                double latitude = Double.parseDouble(latitudeString);
//                double longitude = Double.parseDouble(longitudeString);
//
//
//                ParseGeoPoint geoPoint = new ParseGeoPoint(latitude, longitude);
//                college.setLocation(geoPoint);
//
//                String collegeInitialsInput = collegeInitials.getText().toString();
//                String collegeLatitudeInput = collegeLatitude.getText().toString();
//                String collegeLongitudeInput = collegeLongitude.getText().toString();

                //Check if fields not empty
//                if (collegeNameInput.equals("") || collegeTypeInput.equals("") || collegeInitialsInput.equals("") || collegeLatitudeInput.equals("") || collegeLongitudeInput.equals(""))
                if (collegeNameInput.equals("") || collegeTypeInput.equals("") || collegeInitialsInput.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    // Save the review and return
                    college.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New College Saved!"
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
