package ie.ncirl.jordandaly.classdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jdaly on 10/12/2015.
 */
public class ReviewSingleItem extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables

    TextView tv_reviewTitle;
    TextView tv_rating;
    TextView tv_studentType;
    TextView tv_contentPros;
    TextView tv_contentCons;
    TextView tv_contentAdvice;
    TextView tv_helpfulVoteCount;
    TextView tv_flaggedSpamCount;

    private Button helpfulVoteButton;
    private Button flaggedSpamButton;


    private ParseProxyObject reviewObject = null;
    private String reviewID;
    private String reviewTitle;
    private String rating;
    private String studentType;
    private String contentPros;
    private String contentCons;
    private String contentAdvice;



    private int helpfulVoteCount = 0;
    private int flaggedSpamCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.review_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();


        reviewObject = (ParseProxyObject) intent
                .getSerializableExtra("review");
        reviewID = intent.getStringExtra("reviewID");
        reviewTitle = intent.getStringExtra("reviewTitle");
        rating = intent.getStringExtra("rating");
        studentType = intent.getStringExtra("studentType");
        contentPros = intent.getStringExtra("contentPros");
        contentCons = intent.getStringExtra("contentCons");
        contentAdvice = intent.getStringExtra("contentAdvice");
        helpfulVoteCount = (int) intent.getSerializableExtra("helpfulVoteCount");
        flaggedSpamCount = (int) intent.getSerializableExtra("flaggedSpamCount");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));


        // Locate the TextView in singleitemview.xml
        tv_reviewTitle = (TextView) findViewById(R.id.review_title);
        // Load the text into the TextView
        tv_reviewTitle.setText(reviewTitle);

        // Locate the TextView in singleitemview.xml
        tv_studentType = (TextView) findViewById(R.id.student_type);
        // Load the text into the TextView
        tv_studentType.setText(studentType);

        // Locate the TextView in singleitemview.xml
        tv_rating = (TextView) findViewById(R.id.rating);
        // Load the text into the TextView
        tv_rating.setText(rating);

        // Locate the TextView in singleitemview.xml
        tv_contentPros = (TextView) findViewById(R.id.content_pros);
        // Load the text into the TextView
        tv_contentPros.setText(contentPros);

        // Locate the TextView in singleitemview.xml
        tv_contentPros = (TextView) findViewById(R.id.content_pros);
        // Load the text into the TextView
        tv_contentPros.setText(contentPros);

        // Locate the TextView in singleitemview.xml
        tv_contentCons = (TextView) findViewById(R.id.content_cons);
        // Load the text into the TextView
        tv_contentCons.setText(contentCons);

        // Locate the TextView in singleitemview.xml
        tv_contentAdvice = (TextView) findViewById(R.id.content_advice);
        // Load the text into the TextView
        tv_contentAdvice.setText(contentAdvice);

        // Locate the TextView in singleitemview.xml
        tv_helpfulVoteCount = (TextView) findViewById(R.id.helpful_vote);
        // Load the text into the TextView
        tv_helpfulVoteCount.setText(Integer.toString(helpfulVoteCount));

        // Locate the TextView in singleitemview.xml
        tv_flaggedSpamCount = (TextView) findViewById(R.id.flagged_spam);
        // Load the text into the TextView
        tv_flaggedSpamCount.setText(Integer.toString(flaggedSpamCount));


        helpfulVoteButton = (Button) findViewById(R.id.helpfulVoteButtonId);
        flaggedSpamButton = (Button) findViewById(R.id.flaggedSpamButtonId);


        helpfulVoteButton.setOnClickListener(this);
        flaggedSpamButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.helpfulVoteButtonId:
                //TODO: 11/12/2015


                break;
            case R.id.flaggedSpamButtonId:
                //// TODO: 11/12/2015


        }
    }

}
