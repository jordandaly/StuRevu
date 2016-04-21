package ie.ncirl.jordandaly.classdoor;

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
 * Created by jdaly on 21/04/2016.
 */
public class FavouriteCourseActivity extends ListActivity {

    ListView courseListView;
    private ParseQueryAdapter<Favourite> mainFavouriteCourseAdapter;
    private FavouriteCourseAdapter favouriteCourseAdapter;

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
        delegate.setContentView(R.layout.activity_course_list);

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


        mainFavouriteCourseAdapter = new ParseQueryAdapter<Favourite>(this, Favourite.class);

        mainFavouriteCourseAdapter.setTextKey("Name");
        ;
        mainFavouriteCourseAdapter.loadObjects();


        favouriteCourseAdapter = new FavouriteCourseAdapter(this);

        courseListView = (ListView) findViewById(android.R.id.list);
        courseListView.setAdapter(favouriteCourseAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(favouriteCourseAdapter);

        //getListView().setOnItemClickListener();

        setUpCourseItems();


    }

    private void addDrawerItems() {
        String[] osArray = {"College List", "Search Courses", "Favourite Colleges", "Favourite Courses", "Following"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CollegeListActivity.this, "Navigation drawer!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(FavouriteCourseActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(FavouriteCourseActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 2: {
                        Intent intent = new Intent(FavouriteCourseActivity.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(FavouriteCourseActivity.this, FavouriteCourseActivity.class);
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
        getMenuInflater().inflate(R.menu.activity_action_course_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search_courses).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


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

            case R.id.action_search_courses: {
                Intent intent = new Intent(FavouriteCourseActivity.this, SearchCourseListActivity.class);
                startActivity(intent);
                //break;
            }


        }
        return super.onOptionsItemSelected(item);
    }


//    private void showUnis() {
//        customAdapter.loadObjects();
//        setListAdapter(customAdapter);
//    }


    private void setUpCourseItems() {
        courseListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick courselist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject favourite = favouriteCourseAdapter.getItem(position);
                ParseProxyObject favourite_ppo = new ParseProxyObject(favourite);

                Intent intent = new Intent(FavouriteCourseActivity.this, CourseSingleItem.class);
                intent.putExtra("course", favourite_ppo);
                intent.putExtra("courseID", favourite.getParseObject("Course_Id").getObjectId());
                intent.putExtra("courseDescription", favourite.getParseObject("Course_Id").getString("Description"));
                intent.putExtra("caoCode", favourite.getParseObject("Course_Id").getString("CAO_Code"));
                intent.putExtra("modeOfStudy", favourite.getParseObject("Course_Id").getString("Mode_0f_Study"));
                intent.putExtra("qualification", favourite.getParseObject("Course_Id").getString("Qualification_Type"));
                intent.putExtra("nfqLevel", favourite.getParseObject("Course_Id").getInt("Level"));
                intent.putExtra("duration", favourite.getParseObject("Course_Id").getString("Duration"));
                intent.putExtra("courseLevel", favourite.getParseObject("Course_Id").getString("Course_Level"));
                intent.putExtra("fees", favourite.getParseObject("Course_Id").getInt("Fees"));
                intent.putExtra("departmentFaculty", favourite.getParseObject("Course_Id").getString("Department_Faculty"));
                intent.putExtra("averageRating", favourite.getParseObject("Course_Id").getInt("Average_Rating"));
                intent.putExtra("reviewCount", favourite.getParseObject("Course_Id").getInt("Review_Count"));
                intent.putExtra("moduleCount", favourite.getParseObject("Course_Id").getInt("Module_Count"));
                startActivity(intent);

            }
        });
    }


}
