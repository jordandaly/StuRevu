package com.sturevu.classdoor;

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
 * Created by jdaly on 05/12/2016.
 */
public class FavouriteModuleActivity extends ListActivity {

    ListView moduleListView;
    private ParseQueryAdapter<Favourite> mainFavouriteModuleAdapter;
    private FavouriteModuleAdapter favouriteModuleAdapter;

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
        delegate.setContentView(R.layout.activity_favourite_module_list);

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
        delegate.getSupportActionBar().setTitle("Favourite Module List");


        //mainFavouriteCourseAdapter = new ParseQueryAdapter<Favourite>(this, Favourite.class);
        mainFavouriteModuleAdapter = new ParseQueryAdapter<Favourite>(this, "Favourite");

        mainFavouriteModuleAdapter.setTextKey("Name");
        ;
        mainFavouriteModuleAdapter.loadObjects();


        favouriteModuleAdapter = new FavouriteModuleAdapter(this);

        moduleListView = (ListView) findViewById(android.R.id.list);
        moduleListView.setAdapter(favouriteModuleAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(favouriteModuleAdapter);

        //getListView().setOnItemClickListener();

        setUpModuleItems();


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
                        Intent intent = new Intent(FavouriteModuleActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(FavouriteModuleActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(FavouriteModuleActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(FavouriteModuleActivity.this, MyReviewsActivity.class);
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
//        menu.findItem(R.id.action_search_clubsocs).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


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

//            case R.id.action_search_courses: {
//                Intent intent = new Intent(FavouriteCourseActivity.this, SearchClubSocListActivity.class);
//                startActivity(intent);
//                //break;
//            }


        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    private void showUnis() {
//        customAdapter.loadObjects();
//        setListAdapter(customAdapter);
//    }


    private void setUpModuleItems() {
        moduleListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick favmodulelist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject favourite = favouriteModuleAdapter.getItem(position);
                ParseProxyObject favourite_ppo = new ParseProxyObject(favourite);

                Intent intent = new Intent(FavouriteModuleActivity.this, ModuleSingleItem.class);
                intent.putExtra("module", favourite_ppo);
                intent.putExtra("moduleID", favourite.getParseObject("Module_Id").getObjectId());
                intent.putExtra("moduleDescription", favourite.getParseObject("Module_Id").getString("Description"));
                intent.putExtra("moduleName", favourite.getParseObject("Module_Id").getString("Name"));
                intent.putExtra("moduleType", favourite.getParseObject("Module_Id").getString("Type"));
                intent.putExtra("moduleCourseYear", favourite.getParseObject("Module_Id").getString("Course_Year"));
                startActivity(intent);

            }
        });
    }
}
