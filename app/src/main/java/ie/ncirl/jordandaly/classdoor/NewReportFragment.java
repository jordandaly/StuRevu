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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by jdaly on 19/12/2016.
 */
public class NewReportFragment extends Fragment {

    String collegeId = NewReportActivity.collegeId;
    String courseId = NewReportActivity.courseId;
    String clubsocId = NewReportActivity.clubsocId;
    String moduleId = NewReportActivity.moduleId;
    String reviewId = NewReportActivity.reviewId;
    String commentId = NewReportActivity.commentId;

    private Button saveButton;
    private Button cancelButton;

    private Spinner reportType;
    private TextView reportText;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_report, parent, false);

        reportText = ((EditText) v.findViewById(R.id.report_text));

        reportType = ((Spinner) v.findViewById(R.id.report_type_spinner));
        ArrayAdapter<CharSequence> reportTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.report_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        reportType.setAdapter(reportTypeSpinnerAdapter);


        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Report report = ((NewReportActivity) getActivity()).getCurrentReport();


                // When the user clicks "Save," upload the review to Parse
                // Add data to the report object:
                report.setText(reportText.getText().toString());
                String reportTextInput = reportText.getText().toString();

                // Add the  type
                report.setType(reportType.getSelectedItem().toString());
                String reportTypeInput = reportType.getSelectedItem().toString();

                //Check if fields not empty
                if (reportTextInput.equals("") || reportTypeInput.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    // Associate the report with the current user
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String userId = currentUser.getObjectId();
                    Log.d("DEBUG", "userId is " + userId);
                    report.put("User_Id", ParseObject.createWithoutData("_User", userId));


//                review.setAuthor(ParseUser.getCurrentUser());

                    // Associate the device with a user
//                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//                    installation.put("user", ParseUser.getCurrentUser());
//                    installation.saveInBackground();


                    // Associate the review with the current college
                    if (collegeId != null) {
                        report.put("College_Id", ParseObject.createWithoutData("College", collegeId));
                    }

                    // Associate the review with the current course
                    if (courseId != null) {
                        report.put("Course_Id", ParseObject.createWithoutData("Course", courseId));
                    }

                    // Associate the review with the current clubsoc
                    if (clubsocId != null) {
                        report.put("Club_Soc_Id", ParseObject.createWithoutData("Club_Soc", clubsocId));
                    }

                    // Associate the review with the current module
                    if (moduleId != null) {
                        report.put("Module_Id", ParseObject.createWithoutData("Module", moduleId));
                    }

                    // Associate the review with the current review
                    if (reviewId != null) {
                        report.put("Review_Id", ParseObject.createWithoutData("Review", reviewId));
                    }

                    // Associate the review with the current comment
                    if (commentId != null) {
                        report.put("Comment_Id", ParseObject.createWithoutData("Comment", commentId));
                    }


                    // Save the review and return
                    report.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity().getApplicationContext(), "New Report Saved!"
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
