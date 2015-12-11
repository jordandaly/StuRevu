package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
 * Created by jdaly on 11/12/2015.
 */
public class NewReviewFragment extends Fragment {

    String collegeId = NewReviewActivity.collegeId;
    String courseId = NewReviewActivity.courseId;
    private Button saveButton;
    private Button cancelButton;
    private TextView reviewTitle;
    private Spinner reviewRating;
    private Spinner studentType;
    private TextView contentPros;
    private TextView contentCons;
    private TextView contentAdvice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_review, parent, false);

        reviewTitle = ((EditText) v.findViewById(R.id.review_title));

        // The spinner lets people assign ratings

        reviewRating = ((Spinner) v.findViewById(R.id.rating_spinner));
        ArrayAdapter<CharSequence> ratingSpinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.ratings_array,
                        android.R.layout.simple_spinner_dropdown_item);
        reviewRating.setAdapter(ratingSpinnerAdapter);

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

                // Associate the meal with the current user
                review.setAuthor(ParseUser.getCurrentUser());


                // Associate the meal with the current college
                if (collegeId != null) {
                    review.put("College_Id", ParseObject.createWithoutData("College", collegeId));
                }

                // Associate the meal with the current course
                if (courseId != null) {
                    review.put("Course_Id", ParseObject.createWithoutData("Course", courseId));
                }

                // Add the rating
                review.setRating(reviewRating.getSelectedItem().toString());

                // Add the rating
                review.setStudentType(studentType.getSelectedItem().toString());

                //add content pros,cons,advice
                review.setPros(contentPros.getText().toString());
                review.setCons(contentCons.getText().toString());
                review.setAdvice(contentAdvice.getText().toString());


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
