package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by jdaly on 08/12/2015.
 */
public class CourseSingleItem extends AppCompatActivity implements View.OnClickListener {
    // Declare Variables
    TextView tv_courseDescription;
    TextView tv_modeOfStudy;
    TextView tv_averageRating;
    TextView tv_reviewCount;
    TextView tv_moduleCount;


    private Button modulesButton;
    private Button reviewsButton;


    private ParseProxyObject courseObject = null;
    private String courseID;
    private String courseDescription;
    private String modeOfStudy;

    private int averageRating = 0;
    private int reviewCount = 0;
    ;
    private int moduleCount = 0;
    ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.course_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();


        courseObject = (ParseProxyObject) intent
                .getSerializableExtra("course");
        courseID = intent.getStringExtra("courseID");
        courseDescription = intent.getStringExtra("courseDescription");
        modeOfStudy = intent.getStringExtra("modeOfStudy");
        averageRating = (int) intent.getSerializableExtra("averageRating");
        reviewCount = (int) intent.getSerializableExtra("reviewCount");
        moduleCount = (int) intent.getSerializableExtra("moduleCount");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));

        // Locate the TextView in singleitemview.xml
        tv_modeOfStudy = (TextView) findViewById(R.id.mode_of_study);
        // Load the text into the TextView
        tv_modeOfStudy.setText(modeOfStudy);

        // Locate the TextView in singleitemview.xml
        tv_courseDescription = (TextView) findViewById(R.id.course_description);
        // Load the text into the TextView
        tv_courseDescription.setText(courseDescription);

        // Locate the TextView in singleitemview.xml
        tv_averageRating = (TextView) findViewById(R.id.avg_rating);
        // Load the text into the TextView
        tv_averageRating.setText(Integer.toString(averageRating));

        // Locate the TextView in singleitemview.xml
        tv_reviewCount = (TextView) findViewById(R.id.rev_count);
        // Load the text into the TextView
        tv_reviewCount.setText(Integer.toString(reviewCount));

        // Locate the TextView in singleitemview.xml
        tv_moduleCount = (TextView) findViewById(R.id.course_count);
        // Load the text into the TextView
        tv_moduleCount.setText(Integer.toString(moduleCount));


        modulesButton = (Button) findViewById(R.id.modulesButtonId);
        reviewsButton = (Button) findViewById(R.id.reviewsButtonId);


        modulesButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.modulesButtonId:
                startActivity(new Intent(CourseSingleItem.this, ModuleListActivity.class));
                break;
            case R.id.reviewsButtonId:
                startActivity(new Intent(CourseSingleItem.this, ReviewListActivity.class));

        }
    }


}

