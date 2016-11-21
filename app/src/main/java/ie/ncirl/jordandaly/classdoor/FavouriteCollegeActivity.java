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
public class FavouriteCollegeActivity extends ListActivity {

    ListView collegeListView;
    private ParseQueryAdapter<Favourite> mainFavouriteCollegeAdapter;
    private FavouriteCollegeAdapter favouriteCollegeAdapter;


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
        delegate.setContentView(R.layout.activity_favourite_college_list);

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
        delegate.getSupportActionBar().setTitle("Favourite College List");


        //mainFavouriteCollegeAdapter = new ParseQueryAdapter<Favourite>(this, Favourite.class);
        mainFavouriteCollegeAdapter = new ParseQueryAdapter<Favourite>(this, "Favourite");

        mainFavouriteCollegeAdapter.setTextKey("Name");
        ;
        mainFavouriteCollegeAdapter.loadObjects();


        favouriteCollegeAdapter = new FavouriteCollegeAdapter(this);

        collegeListView = (ListView) findViewById(android.R.id.list);
        collegeListView.setAdapter(favouriteCollegeAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(favouriteCollegeAdapter);

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
                        Intent intent = new Intent(FavouriteCollegeActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 2: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, FavouriteCourseActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, FavouriteCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 5: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, FavouriteCourseReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 6: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, MyCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 7: {
                        Intent intent = new Intent(FavouriteCollegeActivity.this, MyCourseReviewActivity.class);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_action_college_list, menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.action_show_map).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }


    /*
     * "Show Unis" and refreshing the "show all" list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


//            case R.id.action_show_map: {
//                Intent intent = new Intent(FavouriteCollegeActivity.this, MapActivity.class);
//                startActivity(intent);
//                break;
//            }


        }


        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpCollegeItems() {
        collegeListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick collegelist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject favourite = favouriteCollegeAdapter.getItem(position);
                ParseProxyObject favourite_ppo = new ParseProxyObject(favourite);

                Intent intent = new Intent(FavouriteCollegeActivity.this, CollegeSingleItem.class);
                intent.putExtra("college", favourite_ppo);
                intent.putExtra("collegeID", favourite.getParseObject("College_Id").getObjectId());
                intent.putExtra("collegeName", favourite.getParseObject("College_Id").getString("Name"));
                intent.putExtra("initials", favourite.getParseObject("College_Id").getString("Initials"));
                intent.putExtra("averageRating", favourite.getParseObject("College_Id").getInt("Average_Rating"));
                intent.putExtra("reviewCount", favourite.getParseObject("College_Id").getInt("Review_Count"));
                intent.putExtra("courseCount", favourite.getParseObject("College_Id").getInt("Course_Count"));
                intent.putExtra("clubSocCount", favourite.getParseObject("College_Id").getInt("Club_Soc_Count"));
                startActivity(intent);

            }
        });
    }

}
