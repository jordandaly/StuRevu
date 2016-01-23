package ie.ncirl.jordandaly.classdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;


/**
 * Created by jdaly on 08/12/2015.
 */
public class CourseSingleItem extends AppCompatActivity implements View.OnClickListener {
    // Declare Variables
    TextView tv_courseDescription;
    TextView tv_caoCode;
    TextView tv_modeOfStudy;
    TextView tv_qualification;
    TextView tv_nfqLevel;
    TextView tv_duration;
    TextView tv_courseLevel;
    TextView tv_fees;
    TextView tv_departmentFaculty;
    TextView tv_averageRating;
    TextView tv_reviewCount;
    TextView tv_moduleCount;


    private Button modulesButton;
    private Button reviewsButton;
    private Button addNewReviewButton;


    private ParseProxyObject courseObject = null;
    private String courseID;
    private String courseDescription;
    private String caoCode;
    private String modeOfStudy;
    private String qualification;
    private int nfqLevel = 0;
    private String duration;
    private String courseLevel;
    private int fees = 0;
    private String departmentFaculty;
    private Object averageRating;
    private int reviewCount = 0;
    private int moduleCount = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.course_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();

        getIntent().setAction("Course created");


        courseObject = (ParseProxyObject) intent
                .getSerializableExtra("course");
        courseID = intent.getStringExtra("courseID");
        courseDescription = intent.getStringExtra("courseDescription");
        caoCode = intent.getStringExtra("caoCode");
        modeOfStudy = intent.getStringExtra("modeOfStudy");
        qualification = intent.getStringExtra("qualification");
        nfqLevel = (int) intent.getSerializableExtra("nfqLevel");
        duration = intent.getStringExtra("duration");
        courseLevel = intent.getStringExtra("courseLevel");
        fees = (int) intent.getSerializableExtra("fees");
        departmentFaculty = intent.getStringExtra("departmentFaculty");
        //averageRating = (int) intent.getSerializableExtra("averageRating");
        //reviewCount = (int) intent.getSerializableExtra("reviewCount");
        //moduleCount = (int) intent.getSerializableExtra("moduleCount");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("Course_Id", courseID);
        ParseCloud.callFunctionInBackground("averageCourseRating", params, new FunctionCallback<Object>() {
            public void done(Object rating, ParseException e) {
                if (e == null) {
                    System.out.println("Result:" + rating);
                    if (rating != null) {


                        averageRating = rating;
                        System.out.println("avg:" + averageRating);

                    } else {
                        averageRating = 0.0;


                    }
                    // Locate the TextView in singleitemview.xml
                    tv_averageRating = (TextView) findViewById(R.id.avg_rating);
                    // Load the text into the TextView
                    tv_averageRating.setText(averageRating.toString());
                }
            }
        });


        ParseCloud.callFunctionInBackground("countCourseReviews", params, new FunctionCallback<Integer>() {
            public void done(Integer count, ParseException e) {
                if (e == null) {
                    System.out.println("Result_count:" + count);
                    if (count != null) {


                        reviewCount = count;
                        System.out.println("count:" + reviewCount);

                    } else {
                        reviewCount = 0;


                    }
                    // Locate the TextView in singleitemview.xml
                    tv_reviewCount = (TextView) findViewById(R.id.rev_count);
                    // Load the text into the TextView
                    tv_reviewCount.setText(Integer.toString(reviewCount));


                }
            }
        });

        // Locate the TextView in singleitemview.xml
        tv_courseDescription = (TextView) findViewById(R.id.course_description);
        // Load the text into the TextView
        tv_courseDescription.setText(courseDescription);

        // Locate the TextView in singleitemview.xml
        tv_modeOfStudy = (TextView) findViewById(R.id.mode_of_study);
        // Load the text into the TextView
        tv_modeOfStudy.setText(modeOfStudy);

        // Locate the TextView in singleitemview.xml
        tv_caoCode = (TextView) findViewById(R.id.cao_code);
        // Load the text into the TextView
        tv_caoCode.setText(caoCode);

        // Locate the TextView in singleitemview.xml
        tv_qualification = (TextView) findViewById(R.id.qualification);
        // Load the text into the TextView
        tv_qualification.setText(qualification);

        // Locate the TextView in singleitemview.xml
        tv_nfqLevel = (TextView) findViewById(R.id.nfq_level);
        // Load the text into the TextView
        tv_nfqLevel.setText(Integer.toString(nfqLevel));

        // Locate the TextView in singleitemview.xml
        tv_duration = (TextView) findViewById(R.id.duration);
        // Load the text into the TextView
        tv_duration.setText(duration);

        // Locate the TextView in singleitemview.xml
        tv_courseLevel = (TextView) findViewById(R.id.course_level);
        // Load the text into the TextView
        tv_courseLevel.setText(courseLevel);

        // Locate the TextView in singleitemview.xml
        tv_fees = (TextView) findViewById(R.id.fees);
        // Load the text into the TextView
        tv_fees.setText(Integer.toString(fees));

        // Locate the TextView in singleitemview.xml
        tv_departmentFaculty = (TextView) findViewById(R.id.department_faculty);
        // Load the text into the TextView
        tv_departmentFaculty.setText(departmentFaculty);

//        // Locate the TextView in singleitemview.xml
//        tv_averageRating = (TextView) findViewById(R.id.avg_rating);
//        // Load the text into the TextView
//        tv_averageRating.setText(Double.toString(averageRating));

//        // Locate the TextView in singleitemview.xml
//        tv_reviewCount = (TextView) findViewById(R.id.rev_count);
//        // Load the text into the TextView
//        tv_reviewCount.setText(Integer.toString(reviewCount));

        // Locate the TextView in singleitemview.xml
        tv_moduleCount = (TextView) findViewById(R.id.module_count);
        // Load the text into the TextView
        tv_moduleCount.setText(Integer.toString(moduleCount));


        modulesButton = (Button) findViewById(R.id.modulesButtonId);
        reviewsButton = (Button) findViewById(R.id.reviewsButtonId);
        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButtonId);


        modulesButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
        addNewReviewButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.modulesButtonId:
                startActivity(new Intent(CourseSingleItem.this, ModuleListActivity.class));
                break;
            case R.id.reviewsButtonId:
                Intent reviewListIntent = new Intent(this, CourseReviewListActivity.class);
                Log.d("DEBUG", "courseID1r is " + courseID);
                reviewListIntent.putExtra("courseId", courseID);
                startActivity(reviewListIntent);
                //startActivity(new Intent(CourseSingleItem.this, CollegeReviewListActivity.class));
                break;
            case R.id.addNewReviewButtonId:
                Intent addNewReviewIntent = new Intent(this, NewReviewActivity.class);
                Log.d("DEBUG", "courseID1ncr is " + courseID);
                addNewReviewIntent.putExtra("courseId", courseID);
                startActivity(addNewReviewIntent);

        }
    }

    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("Course created")) {
            Log.v("Example", "Force restart");
            Intent intent = getIntent();
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();
    }


}

