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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
    TextView tv_commentCount;


    private Button modulesButton;
    private Button reviewsButton;
//    private Button addNewReviewButton;
    private Button commentsButton;


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
    private int commentCount = 0;


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;



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

                        if (averageRating instanceof Double) {

                            averageRating = String.format("%3.1f", averageRating);
                        }

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

        ParseCloud.callFunctionInBackground("countCourseModules", params, new FunctionCallback<Integer>() {
            public void done(Integer count, ParseException e) {
                if (e == null) {
                    System.out.println("Result_count:" + count);
                    if (count != null) {


                        moduleCount = count;
                        System.out.println("count:" + moduleCount);

                    } else {
                        moduleCount = 0;


                    }
                    // Locate the TextView in singleitemview.xml
                    tv_moduleCount = (TextView) findViewById(R.id.mod_count);
                    // Load the text into the TextView
                    tv_moduleCount.setText(Integer.toString(moduleCount));


                }
            }
        });

        ParseCloud.callFunctionInBackground("countCourseComments", params, new FunctionCallback<Integer>() {
            public void done(Integer count, ParseException e) {
                if (e == null) {
                    System.out.println("course_comment_count:" + count);
                    if (count != null) {


                        commentCount = count;
                        System.out.println("course_comment_count:" + commentCount);

                    } else {
                        commentCount = 0;


                    }
                    // Locate the TextView in singleitemview.xml
                    tv_commentCount = (TextView) findViewById(R.id.comment_count);
                    // Load the text into the TextView
                    tv_commentCount.setText(Integer.toString(commentCount));


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
//        tv_moduleCount = (TextView) findViewById(R.id.module_count);
//        // Load the text into the TextView
//        tv_moduleCount.setText(Integer.toString(moduleCount));


        modulesButton = (Button) findViewById(R.id.modulesButtonId);
        reviewsButton = (Button) findViewById(R.id.reviewsButtonId);
//        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButtonId);
        commentsButton = (Button) findViewById(R.id.commentListButtonId);


        modulesButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
//        addNewReviewButton.setOnClickListener(this);
        commentsButton.setOnClickListener(this);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Course");

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        addDrawerItems();
        setupDrawer();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.modulesButtonId:
//                startActivity(new Intent(CourseSingleItem.this, ModuleListActivity.class));
//                break;
            case R.id.reviewsButtonId:
                Intent reviewListIntent = new Intent(this, CourseReviewListActivity.class);
                Log.d("DEBUG", "courseID1r is " + courseID);
                reviewListIntent.putExtra("courseId", courseID);
                startActivity(reviewListIntent);
                //startActivity(new Intent(CourseSingleItem.this, CollegeReviewListActivity.class));
                break;
            case R.id.modulesButtonId:
                Intent moduleListIntent = new Intent(this, ModuleListActivity.class);
                Log.d("DEBUG", "courseID1m is " + courseID);
                moduleListIntent.putExtra("courseId", courseID);
                startActivity(moduleListIntent);
                //startActivity(new Intent(CourseSingleItem.this, ModuleListActivity.class));
                break;
            case R.id.commentListButtonId:
                Intent commentListIntent = new Intent(this, CommentListActivity.class);
                Log.d("DEBUG", "courseIDc is " + courseID);
                commentListIntent.putExtra("courseId", courseID);
                startActivity(commentListIntent);
                break;
//            case R.id.addNewReviewButtonId:
//                Intent addNewReviewIntent = new Intent(this, NewReviewActivity.class);
//                Log.d("DEBUG", "courseID1ncr is " + courseID);
//                addNewReviewIntent.putExtra("courseId", courseID);
//                startActivity(addNewReviewIntent);

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

    private void addDrawerItems() {
        String[] osArray = {"College List", "Search Courses", "Favourites", "My Reviews"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CollegeListActivity.this, "Navigation drawer!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(CourseSingleItem.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(CourseSingleItem.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(CourseSingleItem.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(CourseSingleItem.this, MyReviewsActivity.class);
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
        menu.findItem(R.id.action_report).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
            case R.id.action_report: {
                Intent intent = new Intent(CourseSingleItem.this, NewReportActivity.class);
                intent.putExtra("courseId", courseID);
                startActivity(intent);
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
        ParseObject course_id = ParseObject.createWithoutData("Course", courseID);
        query.whereEqualTo("Course_Id", course_id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //object exists
                    object.deleteInBackground();
                    ParsePush.unsubscribeInBackground(courseID);
                    Toast.makeText(getApplicationContext(), "Course Removed from Favourites!"
                            , Toast.LENGTH_LONG).show();
                } else {
                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        //object doesn't exist
                        ParseObject favourite = ParseObject.create("Favourite");
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        String userId = currentUser.getObjectId();
                        Log.d("DEBUG", "userId is " + userId);
                        favourite.put("User_Id", ParseObject.createWithoutData("_User", userId));
                        favourite.put("Course_Id", ParseObject.createWithoutData("Course", courseID));
                        favourite.saveInBackground();
                        ParsePush.subscribeInBackground(courseID);
                        Toast.makeText(getApplicationContext(), "Course Added to Favourites!"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        //unknown error, debug
                    }
                }
            }
        });


    }


}



