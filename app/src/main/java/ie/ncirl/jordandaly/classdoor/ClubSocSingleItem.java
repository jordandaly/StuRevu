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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
/**
 * Created by jdaly on 22/11/2016.
 */
public class ClubSocSingleItem extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables
    TextView tv_clubsocName;
    TextView tv_clubsocDescription;
    TextView tv_clubsocType;
    TextView tv_averageRating;
    TextView tv_reviewCount;

    private Button reviewsButton;
    private Button addNewReviewButton;


    private ParseProxyObject clubsocObject = null;
    private String clubsocID;
    private String clubsocName;
    private String clubsocDescription;
    private String clubsocType;

    private Object averageRating;
    private int reviewCount = 0;


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.clubsoc_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();

        getIntent().setAction("ClubSoc created");


        clubsocObject = (ParseProxyObject) intent
                .getSerializableExtra("clubsoc");
        clubsocID = intent.getStringExtra("clubsocID");
        clubsocName = intent.getStringExtra("clubsocName");
        clubsocDescription = intent.getStringExtra("clubsocDescription");
        clubsocType = intent.getStringExtra("clubsocType");
        //averageRating = (int) intent.getSerializableExtra("averageRating");
        //reviewCount = (int) intent.getSerializableExtra("reviewCount");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));

//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("Club_Soc_Id", clubsocID);
//        ParseCloud.callFunctionInBackground("averageClubSocRating", params, new FunctionCallback<Object>() {
//            public void done(Object rating, ParseException e) {
//                if (e == null) {
//                    System.out.println("Result:" + rating);
//                    if (rating != null) {
//
//
//                        averageRating = rating;
//
//                        if (averageRating instanceof Double) {
//
//                            averageRating = String.format("%3.1f", averageRating);
//                        }
//
//                        System.out.println("avg:" + averageRating);
//
//                    } else {
//                        averageRating = 0.0;
//
//
//                    }
//                    // Locate the TextView in singleitemview.xml
//                    tv_averageRating = (TextView) findViewById(R.id.avg_rating);
//                    // Load the text into the TextView
//                    tv_averageRating.setText(averageRating.toString());
//                }
//            }
//        });


//        ParseCloud.callFunctionInBackground("countClubSocReviews", params, new FunctionCallback<Integer>() {
//            public void done(Integer count, ParseException e) {
//                if (e == null) {
//                    System.out.println("Result_count:" + count);
//                    if (count != null) {
//
//
//                        reviewCount = count;
//                        System.out.println("count:" + reviewCount);
//
//                    } else {
//                        reviewCount = 0;
//
//
//                    }
//                    // Locate the TextView in singleitemview.xml
//                    tv_reviewCount = (TextView) findViewById(R.id.rev_count);
//                    // Load the text into the TextView
//                    tv_reviewCount.setText(Integer.toString(reviewCount));
//
//
//                }
//            }
//        });

        // Locate the TextView in singleitemview.xml
        tv_clubsocName = (TextView) findViewById(R.id.clubsoc_name);
        // Load the text into the TextView
        tv_clubsocName.setText(clubsocName);

        // Locate the TextView in singleitemview.xml
        tv_clubsocDescription = (TextView) findViewById(R.id.clubsoc_description);
        // Load the text into the TextView
        tv_clubsocDescription.setText(clubsocDescription);

        // Locate the TextView in singleitemview.xml
        tv_clubsocType = (TextView) findViewById(R.id.clubsoc_type);
        // Load the text into the TextView
        tv_clubsocType.setText(clubsocType);


//        // Locate the TextView in singleitemview.xml
//        tv_averageRating = (TextView) findViewById(R.id.avg_rating);
//        // Load the text into the TextView
//        tv_averageRating.setText(Double.toString(averageRating));

//        // Locate the TextView in singleitemview.xml
//        tv_reviewCount = (TextView) findViewById(R.id.rev_count);
//        // Load the text into the TextView
//        tv_reviewCount.setText(Integer.toString(reviewCount));


//        modulesButton = (Button) findViewById(R.id.modulesButtonId);
        reviewsButton = (Button) findViewById(R.id.reviewsButtonId);
        addNewReviewButton = (Button) findViewById(R.id.addNewReviewButtonId);


//        modulesButton.setOnClickListener(this);
        reviewsButton.setOnClickListener(this);
        addNewReviewButton.setOnClickListener(this);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Club/Society");

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
                Intent reviewListIntent = new Intent(this, ClubSocReviewListActivity.class);
                Log.d("DEBUG", "clubsocID1r is " + clubsocID);
                reviewListIntent.putExtra("clubsocId", clubsocID);
                startActivity(reviewListIntent);
                //startActivity(new Intent(CourseSingleItem.this, CollegeReviewListActivity.class));
                break;
            case R.id.addNewReviewButtonId:
                Intent addNewReviewIntent = new Intent(this, NewReviewActivity.class);
                Log.d("DEBUG", "clubsocID1ncr is " + clubsocID);
                addNewReviewIntent.putExtra("clubsocId", clubsocID);
                startActivity(addNewReviewIntent);

        }
    }

    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("ClubSoc created")) {
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
        String[] osArray = {"College List", "Search Courses", "Favourite Colleges", "Favourite Courses", "Favourite College Reviews", "Favourite Course Reviews", "My College Reviews", "My Course Reviews"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CollegeListActivity.this, "Navigation drawer!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(ClubSocSingleItem.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(ClubSocSingleItem.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(ClubSocSingleItem.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(ClubSocSingleItem.this, FavouriteCourseActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(ClubSocSingleItem.this, FavouriteCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(ClubSocSingleItem.this, FavouriteCourseReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 6: {
                        Intent intent = new Intent(ClubSocSingleItem.this, MyCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 7: {
                        Intent intent = new Intent(ClubSocSingleItem.this, MyCourseReviewActivity.class);
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
        ParseObject clubsoc_id = ParseObject.createWithoutData("Club_Soc", clubsocID);
        query.whereEqualTo("Club_Soc_Id", clubsoc_id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //object exists
                    object.deleteInBackground();
                    ParsePush.unsubscribeInBackground(clubsocID);
                    Toast.makeText(getApplicationContext(), "Club/Society Removed from Favourites!"
                            , Toast.LENGTH_LONG).show();
                } else {
                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        //object doesn't exist
                        ParseObject favourite = ParseObject.create("Favourite");
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        String userId = currentUser.getObjectId();
                        Log.d("DEBUG", "userId is " + userId);
                        favourite.put("User_Id", ParseObject.createWithoutData("_User", userId));
                        favourite.put("Club_Soc_Id", ParseObject.createWithoutData("Club_Soc", clubsocID));
                        favourite.saveInBackground();
                        ParsePush.subscribeInBackground(clubsocID);
                        Toast.makeText(getApplicationContext(), "Club/Society Added to Favourites!"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        //unknown error, debug
                    }
                }
            }
        });


    }


}
