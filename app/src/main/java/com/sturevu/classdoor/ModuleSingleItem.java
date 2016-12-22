package com.sturevu.classdoor;

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
 * Created by jdaly on 29/11/2016.
 */
public class ModuleSingleItem extends AppCompatActivity implements View.OnClickListener {

    /// Declare Variables
    TextView tv_moduleName;
    TextView tv_moduleDescription;
    TextView tv_moduleType;
    TextView tv_moduleCourseYear;
    TextView tv_averageRating;
    TextView tv_reviewCount;
    TextView tv_commentCount;


    private Button reviewsButton;
//    private Button addNewReviewButton;
    private Button commentsButton;


    private ParseProxyObject moduleObject = null;
    private String moduleID;
    private String moduleName;
    private String moduleDescription;
    private String moduleType;
    private String moduleCourseYear;
    private Object averageRating;
    private int reviewCount = 0;
    private int commentCount = 0;


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.module_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();

        getIntent().setAction("Module created");


        moduleObject = (ParseProxyObject) intent
                .getSerializableExtra("module");
        moduleID = intent.getStringExtra("moduleID");
        moduleName = intent.getStringExtra("moduleName");
        moduleDescription = intent.getStringExtra("moduleDescription");
        moduleType = intent.getStringExtra("moduleType");
        moduleCourseYear = intent.getStringExtra("moduleCourseYear");
        //averageRating = (int) intent.getSerializableExtra("averageRating");
        //reviewCount = (int) intent.getSerializableExtra("reviewCount");
        //moduleCount = (int) intent.getSerializableExtra("moduleCount");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("Module_Id", moduleID);
        ParseCloud.callFunctionInBackground("averageModuleRating", params, new FunctionCallback<Object>() {
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


        ParseCloud.callFunctionInBackground("countModuleReviews", params, new FunctionCallback<Integer>() {
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

        ParseCloud.callFunctionInBackground("countModuleComments", params, new FunctionCallback<Integer>() {
            public void done(Integer count, ParseException e) {
                if (e == null) {
                    System.out.println("module_comment_count:" + count);
                    if (count != null) {


                        commentCount = count;
                        System.out.println("module_comment_count:" + commentCount);

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
        tv_moduleName = (TextView) findViewById(R.id.module_name);
        // Load the text into the TextView
        tv_moduleName.setText(moduleName);

        // Locate the TextView in singleitemview.xml
        tv_moduleDescription = (TextView) findViewById(R.id.module_description);
        // Load the text into the TextView
        tv_moduleDescription.setText(moduleDescription);

        // Locate the TextView in singleitemview.xml
        tv_moduleType = (TextView) findViewById(R.id.module_type);
        // Load the text into the TextView
        tv_moduleType.setText(moduleType);

        // Locate the TextView in singleitemview.xml
        tv_moduleCourseYear = (TextView) findViewById(R.id.module_course_year);
        // Load the text into the TextView
        tv_moduleCourseYear.setText(moduleCourseYear);


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


        reviewsButton = (Button) findViewById(R.id.reviewsButtonId);
//        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButtonId);


        reviewsButton.setOnClickListener(this);
//        addNewReviewButton.setOnClickListener(this);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Module");

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
                Intent reviewListIntent = new Intent(this, ModuleReviewListActivity.class);
                Log.d("DEBUG", "moduleID1r is " + moduleID);
                reviewListIntent.putExtra("moduleId", moduleID);
                startActivity(reviewListIntent);
                //startActivity(new Intent(CourseSingleItem.this, CollegeReviewListActivity.class));
                break;
//            case R.id.addNewReviewButtonId:
//                Intent addNewReviewIntent = new Intent(this, NewReviewActivity.class);
//                Log.d("DEBUG", "moduleID1ncr is " + moduleID);
//                addNewReviewIntent.putExtra("moduleId", moduleID);
//                startActivity(addNewReviewIntent);

        }
    }

    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("Module created")) {
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
                        Intent intent = new Intent(ModuleSingleItem.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(ModuleSingleItem.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(ModuleSingleItem.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(ModuleSingleItem.this, MyReviewsActivity.class);
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
                Intent intent = new Intent(ModuleSingleItem.this, NewReportActivity.class);
                intent.putExtra("moduleId", moduleID);
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
        ParseObject module_id = ParseObject.createWithoutData("Module", moduleID);
        query.whereEqualTo("Module_Id", module_id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //object exists
                    object.deleteInBackground();
                    ParsePush.unsubscribeInBackground(moduleID);
                    Toast.makeText(getApplicationContext(), "Module Removed from Favourites!"
                            , Toast.LENGTH_LONG).show();
                } else {
                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        //object doesn't exist
                        ParseObject favourite = ParseObject.create("Favourite");
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        String userId = currentUser.getObjectId();
                        Log.d("DEBUG", "userId is " + userId);
                        favourite.put("User_Id", ParseObject.createWithoutData("_User", userId));
                        favourite.put("Module_Id", ParseObject.createWithoutData("Module", moduleID));
                        favourite.saveInBackground();
                        ParsePush.subscribeInBackground(moduleID);
                        Toast.makeText(getApplicationContext(), "Module Added to Favourites!"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        //unknown error, debug
                    }
                }
            }
        });


    }


}
