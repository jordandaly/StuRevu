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
 * Created by jdaly on 29/11/2016.
 */
public class ModuleReviewListActivity extends ListActivity {

    public static String moduleId;
    ListView reviewListView;
    private ParseQueryAdapter<Review> mainReviewAdapter;
    private ModuleReviewAdapter reviewAdapter;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_review_list);


        Intent intent = getIntent();
        moduleId = intent.getStringExtra("moduleId");
        Log.d("DEBUG", "moduleId2r is " + moduleId);

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
        delegate.setContentView(R.layout.activity_review_list);

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
        delegate.getSupportActionBar().setTitle("Module Review List");


        //mainReviewAdapter = new ParseQueryAdapter<Review>(this, Review.class);
        mainReviewAdapter = new ParseQueryAdapter<Review>(this, "Review");

        mainReviewAdapter.setTextKey("Title");

        mainReviewAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        reviewAdapter = new ModuleReviewAdapter(this);

        reviewListView = (ListView) findViewById(android.R.id.list);
        reviewListView.setAdapter(reviewAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(reviewAdapter);

        //getListView().setOnItemClickListener();

        setUpReviewItems();


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
                        Intent intent = new Intent(ModuleReviewListActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(ModuleReviewListActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(ModuleReviewListActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(ModuleReviewListActivity.this, MyReviewsActivity.class);
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
        getMenuInflater().inflate(R.menu.activity_action_review_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.action_add_review).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    /*
     * "Show Unis" and refreshing the "show all" list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateReviewList();
                break;
            }

            case R.id.action_add_review: {
                Intent addNewReviewIntent = new Intent(this, NewReviewActivity.class);
                Log.d("DEBUG", "moduleId1ncr is " + moduleId);
                addNewReviewIntent.putExtra("moduleId", moduleId);
                startActivity(addNewReviewIntent);
                break;
            }


        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateReviewList() {
        reviewAdapter.loadObjects();
        setListAdapter(reviewAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateReviewList();
        }
    }

    private void setUpReviewItems() {
        reviewListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick reviewlist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject review = reviewAdapter.getItem(position);
                ParseProxyObject review_ppo = new ParseProxyObject(review);

                Intent intent = new Intent(ModuleReviewListActivity.this, ReviewSingleItem.class);
                intent.putExtra("review", review_ppo);
                intent.putExtra("reviewID", review.getObjectId());
                intent.putExtra("reviewTitle", review.getString("Title"));
                intent.putExtra("rating", review.getDouble("Rating"));
                intent.putExtra("username", review.getParseObject("User_Id").getString("username"));
                intent.putExtra("studentType", review.getString("Student_Type"));
                intent.putExtra("contentPros", review.getString("Content_Pros"));
                intent.putExtra("contentCons", review.getString("Content_Cons"));
                intent.putExtra("contentAdvice", review.getString("Content_Advice"));
                intent.putExtra("helpfulVoteCount", review.getInt("Helpful_Vote_Count"));
                intent.putExtra("flaggedSpamCount", review.getInt("Flagged_Spam_Count"));
                startActivity(intent);

            }
        });
    }

}
