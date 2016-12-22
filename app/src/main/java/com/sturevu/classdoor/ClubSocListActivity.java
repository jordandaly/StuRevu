package com.sturevu.classdoor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 09/12/2015.
 */
public class ClubSocListActivity extends ListActivity {

    public static String collegeId;
    ListView clubSocListView;
    private ParseQueryAdapter<Course> mainClubSocAdapter;
    private ClubSocAdapter clubSocAdapter;
    //public String getCollegeId() { return collegeId; }

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    //private customCustomAdapter customCourseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_course_list);
        //getListView().setClickable(false);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2 is " + collegeId);

        getIntent().setAction("New ClubSoc Added");

        AppCompatCallback callback = new AppCompatCallback() {

            @Nullable
            @Override
            public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
                return null;
            }

            @Override
            public void onSupportActionModeStarted(ActionMode actionMode) {
            }

            @Override
            public void onSupportActionModeFinished(ActionMode actionMode) {
            }
        };

        AppCompatDelegate delegate = AppCompatDelegate.create(this, callback);

        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_clubsoc_list);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        addDrawerItems();
        setupDrawer();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        delegate.setSupportActionBar(toolbar);

        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setHomeButtonEnabled(true);
        delegate.getSupportActionBar().setTitle("Clubs & Societies List");


        //mainCourseAdapter = new ParseQueryAdapter<Course>(this, Course.class);
        mainClubSocAdapter = new ParseQueryAdapter<Course>(this, "Club_Soc");

        mainClubSocAdapter.setTextKey("Description");

        mainClubSocAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        clubSocAdapter = new ClubSocAdapter(this);

        clubSocListView = (ListView) findViewById(android.R.id.list);
        clubSocListView.setAdapter(clubSocAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(clubSocAdapter);

        //getListView().setOnItemClickListener();

        setUpCourseItems();


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
                        Intent intent = new Intent(ClubSocListActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(ClubSocListActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(ClubSocListActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(ClubSocListActivity.this, MyReviewsActivity.class);
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
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("New ClubSoc Added")) {
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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_action_clubsoc_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_clubsoc).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


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


            case R.id.action_add_clubsoc: {
                Intent intent = new Intent(ClubSocListActivity.this, NewClubSocActivity.class);
                intent.putExtra("collegeId", collegeId);
                startActivity(intent);
            }

        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCourseList() {
        clubSocAdapter.loadObjects();
        setListAdapter(clubSocAdapter);
    }

//    private void showUnis() {
//        customAdapter.loadObjects();
//        setListAdapter(customAdapter);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateCourseList();
        }
    }

    private void setUpCourseItems() {
        clubSocListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick courselist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject clubsoc = clubSocAdapter.getItem(position);
                ParseProxyObject clubsoc_ppo = new ParseProxyObject(clubsoc);

                Intent intent = new Intent(ClubSocListActivity.this, ClubSocSingleItem.class);
                intent.putExtra("clubsoc", clubsoc_ppo);
                intent.putExtra("clubsocID", clubsoc.getObjectId());
                intent.putExtra("clubsocDescription", clubsoc.getString("Description"));
                intent.putExtra("clubsocName", clubsoc.getString("Name"));
                intent.putExtra("clubsocType", clubsoc.getString("Type"));
                startActivity(intent);

            }
        });
    }

}
