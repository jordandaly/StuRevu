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

import java.util.Arrays;

/**
 * Created by jdaly on 29/11/2016.
 */
public class NewModuleFragment extends Fragment {

    String courseId = NewModuleActivity.courseId;


    TextView moduleDescription;
    TextView moduleName;
    private Button saveButton;
    private Button cancelButton;
    private Spinner moduleCourseYear;
    private Spinner moduleType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_module, parent, false);

        moduleDescription = ((EditText) v.findViewById(R.id.module_description));
        moduleName = ((EditText) v.findViewById(R.id.module_name));

        moduleType = ((Spinner) v.findViewById(R.id.module_type_spinner));
        ArrayAdapter<CharSequence> moduleTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.module_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        moduleType.setAdapter(moduleTypeSpinnerAdapter);

        moduleCourseYear = ((Spinner) v.findViewById(R.id.module_course_year_spinner));
        ArrayAdapter<CharSequence> moduleCourseYearSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.module_course_year_array,
                        android.R.layout.simple_spinner_dropdown_item);
        moduleCourseYear.setAdapter(moduleCourseYearSpinnerAdapter);


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Module module = ((NewModuleActivity) getActivity()).getCurrentModule();


                // When the user clicks "Save," upload the course to Parse
                // Add data to the course object:
                String moduleNameInput = moduleName.getText().toString();
                String s1 = moduleNameInput.substring(0, 1).toUpperCase();
                String moduleNameCapitalized = s1 + moduleNameInput.substring(1);
                module.setName(moduleNameCapitalized);


                module.setDescription(moduleDescription.getText().toString());
                String moduleDescriptionInput = moduleDescription.getText().toString();


                // Add the spinner types

                module.setType(moduleType.getSelectedItem().toString());
                String moduleTypeInput = moduleType.getSelectedItem().toString();

                module.setCourseYear(moduleCourseYear.getSelectedItem().toString());
                String moduleCourseYearInput = moduleCourseYear.getSelectedItem().toString();


//                String coursefeesInput = coursefees.getText().toString();
//                String courseDeptFacInput = courseDeptFac.getText().toString();
//                String caoCodeInput = caoCode.getText().toString();

                //Check if fields not empty
                if (moduleName.equals("") || moduleDescription.equals("") || moduleType.equals("") || moduleCourseYear.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


                    // Associate the review with the current college
                    if (courseId != null) {
                        //ArrayList<ParseObject> course = new ArrayList<ParseObject>();
                        //course.add(courseId);
                        module.addAllUnique("Course_Id", Arrays.asList(courseId));
                        //module.put("Course_Id", course);
                        module.saveInBackground();

                        //send push notification and include college name
                        ParseQuery<ParseObject> query = new ParseQuery("Course");
                        query.getInBackground(courseId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String courseDesc = object.getString("Description");
                                    ParsePush push = new ParsePush();
                                    push.setChannel(courseId);
                                    push.setMessage("A new module has been created for one of your favourite Courses: " + courseDesc);
                                    push.sendInBackground();
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }


                    // Save the review and return
                    module.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New Module Saved!"
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
