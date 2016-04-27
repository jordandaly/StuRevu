package ie.ncirl.jordandaly.classdoor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by jdaly on 10/12/2015.
 */
public class ReviewSingleItem extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables

    TextView tv_reviewTitle;
    RatingBar tv_rating;
    TextView tv_author;
    TextView tv_studentType;
    TextView tv_contentPros;
    TextView tv_contentCons;
    TextView tv_contentAdvice;
    TextView tv_helpfulVoteCount;
    TextView tv_flaggedSpamCount;
    int disableBtn = 1;
    private Button helpfulVoteButton;
    private Button flaggedSpamButton;
    private Button commentListButton;
    private Button addNewCommentButton;
    private ParseProxyObject reviewObject = null;
    private String reviewID;
    private String reviewTitle;
    private double rating;
    private String author;
    private String studentType;
    private String contentPros;
    private String contentCons;
    private String contentAdvice;
    private int helpfulVoteCount = 0;
    private int flaggedSpamCount = 0;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

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
        author = intent.getStringExtra("username");
        rating = (double) intent.getSerializableExtra("rating");
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
        tv_author = (TextView) findViewById(R.id.author);
        // Load the text into the TextView
        tv_author.setText(author);

        // Locate the TextView in singleitemview.xml
        tv_studentType = (TextView) findViewById(R.id.student_type);
        // Load the text into the TextView
        tv_studentType.setText(studentType);

        // Locate the TextView in singleitemview.xml
//        tv_rating = (TextView) findViewById(R.id.rating);
//        // Load the text into the TextView
//        tv_rating.setText(Double.toString(rating));

        tv_rating = (RatingBar) findViewById(R.id.rating);
        // Load the value into the ratingbar
        float r = (float) rating;
        Log.v("Test", String.format("rating float value: " + r));
        tv_rating.setRating(r);



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
        commentListButton = (Button) findViewById(R.id.commentListButtonId);
        addNewCommentButton = (Button) findViewById(R.id.addNewCommentButtonId);


        helpfulVoteButton.setOnClickListener(this);
        flaggedSpamButton.setOnClickListener(this);
        commentListButton.setOnClickListener(this);
        addNewCommentButton.setOnClickListener(this);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        addDrawerItems();
        setupDrawer();


    }

    @Override
    public void onClick(View v) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");

        if (disableBtn == 1) {
            helpfulVoteButton.setEnabled(false);
            flaggedSpamButton.setEnabled(false);
            Log.d("disableBtn", "called");
        }
        disableBtn = 0;

        switch (v.getId()) {
            case R.id.helpfulVoteButtonId:



// Retrieve the object by id
                query.getInBackground(reviewID, new GetCallback<ParseObject>() {
                    public void done(ParseObject review, ParseException e) {
                        if (e == null) {

                            review.increment("Helpful_Vote_Count");
                            review.saveInBackground();

                            helpfulVoteCount++;
                            tv_helpfulVoteCount.setText(Integer.toString(helpfulVoteCount));
                            Toast.makeText(getApplicationContext(), "Review marked as helpful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




                break;
            case R.id.flaggedSpamButtonId:

                query.getInBackground(reviewID, new GetCallback<ParseObject>() {
                    public void done(ParseObject review, ParseException e) {
                        if (e == null) {

                            review.increment("Flagged_Spam_Count");
                            review.saveInBackground();

                            flaggedSpamCount++;
                            tv_flaggedSpamCount.setText(Integer.toString(flaggedSpamCount));
                            Toast.makeText(getApplicationContext(), "Review flagged as Spam!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.commentListButtonId:
                Intent commentListIntent = new Intent(this, CommentListActivity.class);
                Log.d("DEBUG", "reviewId is " + reviewID);
                commentListIntent.putExtra("reviewId", reviewID);
                startActivity(commentListIntent);
                break;
            case R.id.addNewCommentButtonId:
                Intent addnewCommentIntent = new Intent(this, NewCommentActivity.class);
                Log.d("DEBUG", "reviewId is " + reviewID);
                addnewCommentIntent.putExtra("reviewId", reviewID);
                startActivity(addnewCommentIntent);


        }
    }

    private void addDrawerItems() {
        String[] osArray = {"College List", "Search Courses", "Favourite Colleges", "Favourite Courses", "Favourite College Reviews", "Favourite Course Reviews"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CollegeListActivity.this, "Navigation drawer!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(ReviewSingleItem.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(ReviewSingleItem.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(ReviewSingleItem.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(ReviewSingleItem.this, FavouriteCourseActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(ReviewSingleItem.this, FavouriteCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(ReviewSingleItem.this, FavouriteCourseReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {


            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_action_single_item, menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_favourite).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    /*
     * "Show Unis" and refreshing the "show all" list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_favourite: {
                saveFavourite();
                break;
            }


        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFavourite() {
        ParseQuery query = new ParseQuery("Favourite");
        ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo("User_Id", user_id);
        ParseObject review_id = ParseObject.createWithoutData("Review", reviewID);
        query.whereEqualTo("Review_Id", review_id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //object exists
                    object.deleteInBackground();
                    ParsePush.unsubscribeInBackground(reviewID);
                    Toast.makeText(getApplicationContext(), "Review Removed from Favourites!"
                            , Toast.LENGTH_LONG).show();
                } else {
                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        //object doesn't exist
                        ParseObject favourite = ParseObject.create("Favourite");
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        String userId = currentUser.getObjectId();
                        Log.d("DEBUG", "userId is " + userId);
                        favourite.put("User_Id", ParseObject.createWithoutData("_User", userId));
                        favourite.put("Review_Id", ParseObject.createWithoutData("Review", reviewID));
                        favourite.saveInBackground();
                        ParsePush.subscribeInBackground(reviewID);
                        Toast.makeText(getApplicationContext(), "Review Added to Favourites!"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        //unknown error, debug
                    }
                }
            }
        });


    }


}
