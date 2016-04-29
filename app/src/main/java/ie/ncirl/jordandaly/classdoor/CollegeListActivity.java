package ie.ncirl.jordandaly.classdoor;


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


public class CollegeListActivity extends ListActivity {


    ListView collegeListView;
    private ParseQueryAdapter<College> mainCollegeAdapter;
    private CollegeAdapter collegeAdapter;
    private CustomAdapter customAdapter;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_college_list);
        //getListView().setClickable(false);


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
        delegate.setContentView(R.layout.activity_college_list);

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


        mainCollegeAdapter = new ParseQueryAdapter<College>(this, College.class);

        mainCollegeAdapter.setTextKey("Name");
        mainCollegeAdapter.setImageKey("ImageFile");
        mainCollegeAdapter.loadObjects();

        // Subclass of ParseQueryAdapter
        customAdapter = new CustomAdapter(this);

        // Subclass of ParseQueryAdapter
        collegeAdapter = new CollegeAdapter(this);

        collegeListView = (ListView) findViewById(android.R.id.list);
        collegeListView.setAdapter(collegeAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(collegeAdapter);

        //getListView().setOnItemClickListener();

        setUpCollegeItems();






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
                        Intent intent = new Intent(CollegeListActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(CollegeListActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(CollegeListActivity.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(CollegeListActivity.this, FavouriteCourseActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 4: {
                        Intent intent = new Intent(CollegeListActivity.this, FavouriteCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(CollegeListActivity.this, FavouriteCourseReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 6: {
                        Intent intent = new Intent(CollegeListActivity.this, MyCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 7: {
                        Intent intent = new Intent(CollegeListActivity.this, MyCourseReviewActivity.class);
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

        getMenuInflater().inflate(R.menu.activity_action_college_list, menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.action_show_uni).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.action_show_map).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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

            case R.id.action_refresh: {
                updateCollegeList();
                break;
            }

            case R.id.action_show_uni: {
                showUnis();
                break;
            }

            case R.id.action_show_map: {
                Intent intent = new Intent(CollegeListActivity.this, MapActivity.class);
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

    private void updateCollegeList() {
        collegeAdapter.loadObjects();
        setListAdapter(collegeAdapter);
    }

    private void showUnis() {
        customAdapter.loadObjects();
        setListAdapter(customAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateCollegeList();
        }
    }

    private void setUpCollegeItems() {
        collegeListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick collegelist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject college = collegeAdapter.getItem(position);
                ParseProxyObject college_ppo = new ParseProxyObject(college);

                Intent intent = new Intent(CollegeListActivity.this, CollegeSingleItem.class);
                intent.putExtra("college", college_ppo);
                intent.putExtra("collegeID", college.getObjectId());
                intent.putExtra("collegeName", college.getString("Name"));
                intent.putExtra("initials", college.getString("Initials"));
                intent.putExtra("averageRating", college.getInt("Average_Rating"));
                intent.putExtra("reviewCount", college.getInt("Review_Count"));
                intent.putExtra("courseCount", college.getInt("Course_Count"));
                intent.putExtra("clubSocCount", college.getInt("Club_Soc_Count"));
                startActivity(intent);

            }
        });
    }


}