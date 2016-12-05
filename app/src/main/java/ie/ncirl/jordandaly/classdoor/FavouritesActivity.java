package ie.ncirl.jordandaly.classdoor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by jdaly on 05/12/2016.
 */
public class FavouritesActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables

    private Button favCollegesButton;
    private Button favCoursesButton;
    private Button favClubSocsButton;
    private Button favModulesButton;

    private Button favCollegeReviewsButton;
    private Button favCourseReviewsButton;
    private Button favClubSocReviewsButton;
    private Button favModuleReviewsButton;


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.activity_favourites);


        // Retrieve data from college list activity on item click event


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Favourites");


        favCollegesButton = (Button) findViewById(R.id.favCollegesButtonId);
        favCollegesButton.setOnClickListener(this);

        favCoursesButton = (Button) findViewById(R.id.favCoursesButtonId);
        favCoursesButton.setOnClickListener(this);

        favClubSocsButton = (Button) findViewById(R.id.favClubSocsButtonId);
        favClubSocsButton.setOnClickListener(this);

        favModulesButton = (Button) findViewById(R.id.favModulesButtonId);
        favModulesButton.setOnClickListener(this);


        favCollegeReviewsButton = (Button) findViewById(R.id.favCollegeReviewsButtonId);
        favCollegeReviewsButton.setOnClickListener(this);

        favCourseReviewsButton = (Button) findViewById(R.id.favCourseReviewsButtonId);
        favCourseReviewsButton.setOnClickListener(this);

        favClubSocReviewsButton = (Button) findViewById(R.id.favClubSocReviewsButtonId);
        favClubSocReviewsButton.setOnClickListener(this);

        favModuleReviewsButton = (Button) findViewById(R.id.favModuleReviewsButtonId);
        favModuleReviewsButton.setOnClickListener(this);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        addDrawerItems();
        setupDrawer();


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
                        Intent intent = new Intent(FavouritesActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(FavouritesActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(FavouritesActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(FavouritesActivity.this, MyReviewsActivity.class);
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
        getMenuInflater().inflate(R.menu.activity_action_review_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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


        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_action_comment_list, menu);
//        return true;
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.favCollegesButtonId:
                Intent favCollegesIntent = new Intent(this, FavouriteCollegeActivity.class);
                startActivity(favCollegesIntent);
                break;
            case R.id.favCoursesButtonId:
                Intent favCoursesIntent = new Intent(this, FavouriteCourseActivity.class);
                startActivity(favCoursesIntent);
                break;
            case R.id.favClubSocsButtonId:
                Intent favClubSocsIntent = new Intent(this, FavouriteClubSocActivity.class);
                startActivity(favClubSocsIntent);
                break;
            case R.id.favModulesButtonId:
                Intent favModulesIntent = new Intent(this, FavouriteModuleActivity.class);
                startActivity(favModulesIntent);
                break;
            case R.id.favCollegeReviewsButtonId:
                Intent favCollegeReviewsIntent = new Intent(this, FavouriteCollegeReviewActivity.class);
                startActivity(favCollegeReviewsIntent);
                break;
            case R.id.favCourseReviewsButtonId:
                Intent favCourseReviewsIntent = new Intent(this, FavouriteCourseReviewActivity.class);
                startActivity(favCourseReviewsIntent);
                break;
            case R.id.favClubSocReviewsButtonId:
                Intent favClubSocsReviewsIntent = new Intent(this, FavouriteClubSocReviewActivity.class);
                startActivity(favClubSocsReviewsIntent);
                break;
            case R.id.favModuleReviewsButtonId:
                Intent favModuleReviewsIntent = new Intent(this, FavouriteModuleReviewActivity.class);
                startActivity(favModuleReviewsIntent);


        }
    }
}
