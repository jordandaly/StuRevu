package ie.ncirl.jordandaly.classdoor;

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
 * Created by jdaly on 16/10/2016.
 */
public class NewCourseFragment extends Fragment {

    String collegeId = NewCourseActivity.collegeId;

    TextView courseDescription;
    TextView courseName;
    private Button saveButton;
    private Button cancelButton;
    private Spinner courseLevel;
    private Spinner qualificationType;
    private Spinner modeOfStudy;
    private Spinner courseDuration;
    private TextView coursefees;
    private TextView courseDeptFac;
    private TextView caoCode;
    private Integer level;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_course, parent, false);

        courseDescription = ((EditText) v.findViewById(R.id.course_description));
        courseName = ((EditText) v.findViewById(R.id.course_name));

        courseLevel = ((Spinner) v.findViewById(R.id.course_level_spinner));
        ArrayAdapter<CharSequence> courseLevelSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.course_level_array,
                        android.R.layout.simple_spinner_dropdown_item);
        courseLevel.setAdapter(courseLevelSpinnerAdapter);

        qualificationType = ((Spinner) v.findViewById(R.id.qualification_type_spinner));
        ArrayAdapter<CharSequence> qualificationTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.qualification_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        qualificationType.setAdapter(qualificationTypeSpinnerAdapter);

        modeOfStudy = ((Spinner) v.findViewById(R.id.mode_of_study_spinner));
        ArrayAdapter<CharSequence> modeOfStudySpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.mode_of_study_array,
                        android.R.layout.simple_spinner_dropdown_item);
        modeOfStudy.setAdapter(modeOfStudySpinnerAdapter);

        courseDuration = ((Spinner) v.findViewById(R.id.course_duration_spinner));
        ArrayAdapter<CharSequence> courseDurationSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.course_duration_array,
                        android.R.layout.simple_spinner_dropdown_item);
        courseDuration.setAdapter(courseDurationSpinnerAdapter);


        coursefees = ((EditText) v.findViewById(R.id.course_fees));
        courseDeptFac = ((EditText) v.findViewById(R.id.department_faculty));
        caoCode = ((EditText) v.findViewById(R.id.cao_code));


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Course course = ((NewCourseActivity) getActivity()).getCurrentCourse();


                // When the user clicks "Save," upload the course to Parse
                // Add data to the course object:
                String courseNameInput = courseName.getText().toString();
                String s1 = courseNameInput.substring(0, 1).toUpperCase();
                String courseNameCapitalized = s1 + courseNameInput.substring(1);
                course.setName(courseNameCapitalized);


                course.setDescription(courseDescription.getText().toString());
                String courseDescriptionInput = courseDescription.getText().toString();


                // Add the spinner types
                course.setQualificationType(qualificationType.getSelectedItem().toString());
                String qualificationTypeInput = qualificationType.getSelectedItem().toString();
                Log.d("DEBUG", "qualificationTypeInput is " + qualificationTypeInput);

                switch (qualificationTypeInput) {
                    case "Higher Certificate":
                        level = 6;
                        break;
                    case "Ordinary Bachelor Degree":
                        level = 7;
                        break;
                    case "Honours Bachelor Degree":
                        level = 8;
                        break;
                    case "Higher Diploma":
                        level = 8;
                        break;
                    case "Postgraduate Diploma":
                        level = 9;
                        break;
                    case "Masters Degree":
                        level = 9;
                        break;
                    default:
                        level = 0;
                }
                course.setNfqLevel(level);

                course.setCourseLevel(courseLevel.getSelectedItem().toString());
                String courseLevelInput = courseLevel.getSelectedItem().toString();

                course.setModeOfStudy(modeOfStudy.getSelectedItem().toString());
                String modeOfStudyInput = modeOfStudy.getSelectedItem().toString();

                course.setCourseDuration(courseDuration.getSelectedItem().toString());
                String courseDurationInput = courseDuration.getSelectedItem().toString();

                //add optional fees, deptfac & cao code
                String string_fees = coursefees.getText().toString();
                Log.d("DEBUG", "string_fees  is " + string_fees);
                if (string_fees != null && !string_fees.isEmpty() && !string_fees.equals("null")) {
                    int int_fees = Integer.parseInt(string_fees);
                    course.setCourseFees(int_fees);
                }

                course.setCourseDeptFac(courseDeptFac.getText().toString());
                course.setCaoCode(caoCode.getText().toString());

//                String coursefeesInput = coursefees.getText().toString();
//                String courseDeptFacInput = courseDeptFac.getText().toString();
//                String caoCodeInput = caoCode.getText().toString();

                //Check if fields not empty
                if (courseName.equals("") || courseDescription.equals("") || qualificationType.equals("") || courseLevel.equals("") || modeOfStudy.equals("") || courseDuration.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


                    // Associate the review with the current college
                    if (collegeId != null) {
                        course.put("College_Id", ParseObject.createWithoutData("College", collegeId));

                        //send push notification and include college name
                        ParseQuery<ParseObject> query = new ParseQuery("College");
                        query.getInBackground(collegeId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    // object will be your college
                                    String collegeName = object.getString("Name");
                                    ParsePush push = new ParsePush();
                                    push.setChannel(collegeId);
                                    push.setMessage("A new course has been created for one of your favourite Colleges: " + collegeName);
                                    push.sendInBackground();
                                } else {
                                    // something went wrong
                                }
                            }
                        });

                    }


                    // Save the review and return
                    course.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New Course Saved!"
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
