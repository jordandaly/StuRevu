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
public class CollegeSingleItem extends AppCompatActivity implements View.OnClickListener {
    // Declare Variables

    TextView tv_collegeName;
    TextView tv_initials;
    TextView tv_averageRating;
    TextView tv_reviewCount;
    TextView tv_courseCount;
    TextView tv_clubSocCount;

    private Button coursesButton;
    private Button reviewsButton;
    private Button clubsocsButton;
    private Button addNewReviewButton;


    private ParseProxyObject collegeObject = null;
    private String collegeID;
    private String collegeName;
    private String initials;
    //private String averageRating;
    private Object averageRating;

    private int reviewCount = 0;

    private int courseCount = 0;

    private int clubSocCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.college_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();

        getIntent().setAction("College created");


        collegeObject = (ParseProxyObject) intent
                .getSerializableExtra("college");
        collegeID = intent.getStringExtra("collegeID");
        collegeName = intent.getStringExtra("collegeName");
        initials = intent.getStringExtra("initials");
        //averageRating = (int) intent.getSerializableExtra("averageRating");
        //reviewCount = (int) intent.getSerializableExtra("reviewCount");
        //courseCount = (int) intent.getSerializableExtra("courseCount");
        //clubSocCount = (int) intent.getSerializableExtra("clubSocCount");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("College_Id", collegeID);
        ParseCloud.callFunctionInBackground("averageCollegeRating", params, new FunctionCallback<Object>() {
            public void done(Object rating, ParseException e) {
                if (e == null) {
                    System.out.println("Result:" + rating);
                    if (rating != null) {


                        averageRating = rating;
                        averageRating = String.format("%3.2f", averageRating);
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

        ParseCloud.callFunctionInBackground("countCollegeReviews", params, new FunctionCallback<Integer>() {
            public void done(Integer count, ParseException e) {
                if (e == null) {
                    System.out.println("college_review_count:" + count);
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

        ParseCloud.callFunctionInBackground("countCollegeCourses", params, new FunctionCallback<Integer>() {
            public void done(Integer count, ParseException e) {
                if (e == null) {
                    System.out.println("course_count:" + count);
                    if (count != null) {


                        courseCount = count;
                        System.out.println("count:" + courseCount);

                    } else {
                        courseCount = 0;


                    }
                    // Locate the TextView in singleitemview.xml
                    tv_courseCount = (TextView) findViewById(R.id.course_count);
                    // Load the text into the TextView
                    tv_courseCount.setText(Integer.toString(courseCount));


                }
            }
        });







        // Locate the TextView in singleitemview.xml
        tv_initials = (TextView) findViewById(R.id.init);
        // Load the text into the TextView
        tv_initials.setText(initials);

        // Locate the TextView in singleitemview.xml
        tv_collegeName = (TextView) findViewById(R.id.name);
        // Load the text into the TextView
        tv_collegeName.setText(collegeName);

//        // Locate the TextView in singleitemview.xml
//        tv_averageRating = (TextView) findViewById(R.id.avg_rating);
//        // Load the text into the TextView
//        tv_averageRating.setText(Double.toString(averageRating));

//        // Locate the TextView in singleitemview.xml
//        tv_reviewCount = (TextView) findViewById(R.id.rev_count);
//        // Load the text into the TextView
//        tv_reviewCount.setText(Integer.toString(reviewCount));

//        // Locate the TextView in singleitemview.xml
//        tv_courseCount = (TextView) findViewById(R.id.course_count);
//        // Load the text into the TextView
//        tv_courseCount.setText(Integer.toString(courseCount));

        // Locate the TextView in singleitemview.xml
        tv_clubSocCount = (TextView) findViewById(R.id.club_soc_count);
        // Load the text into the TextView
        tv_clubSocCount.setText(Integer.toString(clubSocCount));

        coursesButton = (Button) findViewById(R.id.coursesButtonId);
        reviewsButton = (Button) findViewById(R.id.reviewsButtonId);
        clubsocsButton = (Button) findViewById(R.id.clubsocsButtonId);
        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButtonId);

        coursesButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
        clubsocsButton.setOnClickListener(this);
        addNewReviewButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.coursesButtonId:
                Intent courseListIntent = new Intent(this, CourseListActivity.class);
                Log.d("DEBUG", "collegeID1c is " + collegeID);
                courseListIntent.putExtra("collegeId", collegeID);
                startActivity(courseListIntent);
                //startActivity(new Intent(CollegeSingleItem.this, CourseListActivity.class));

                break;
            case R.id.reviewsButtonId:
                Intent reviewListIntent = new Intent(this, CollegeReviewListActivity.class);
                Log.d("DEBUG", "collegeID1r is " + collegeID);
                reviewListIntent.putExtra("collegeId", collegeID);
                startActivity(reviewListIntent);
                //startActivity(new Intent(CollegeSingleItem.this, CollegeReviewListActivity.class));
                break;
            case R.id.clubsocsButtonId:
                startActivity(new Intent(CollegeSingleItem.this, ClubSocListActivity.class));
                break;
            case R.id.addNewReviewButtonId:
                Intent addNewReviewIntent = new Intent(this, NewReviewActivity.class);
                Log.d("DEBUG", "collegeID1ncr is " + collegeID);
                addNewReviewIntent.putExtra("collegeId", collegeID);
                startActivity(addNewReviewIntent);

        }
    }

    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("College created")) {
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

