package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 20/04/2016.
 */
public class SearchCourseListActivity extends ListActivity {

    public static String coursename;
    protected EditText sCourse;
    protected Button mSearchButton;
    ListView courseListView;
    private ParseQueryAdapter<Course> mainCourseAdapter;
    private SearchCourseAdapter searchCourseAdapter;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_course);
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
        delegate.setContentView(R.layout.activity_search_course);

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
        delegate.getSupportActionBar().setTitle("Search Courses");


        mainCourseAdapter = new ParseQueryAdapter<Course>(this, "Course");

        mainCourseAdapter.setTextKey("Description");

        mainCourseAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        searchCourseAdapter = new SearchCourseAdapter(this);

        courseListView = (ListView) findViewById(android.R.id.list);
        courseListView.setVisibility(View.INVISIBLE);
//        courseListView.setAdapter(searchCourseAdapter);
//
//        // Default view is collegeAdapter (all college sorted asc)
//        setListAdapter(searchCourseAdapter);
//
//        //getListView().setOnItemClickListener();
//
//        setUpCourseItems();

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }


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
                        Intent intent = new Intent(SearchCourseListActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(SearchCourseListActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(SearchCourseListActivity.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(SearchCourseListActivity.this, FavouriteCourseActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 4: {
                        Intent intent = new Intent(SearchCourseListActivity.this, FavouriteCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(SearchCourseListActivity.this, FavouriteCourseReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 6: {
                        Intent intent = new Intent(SearchCourseListActivity.this, MyCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 7: {
                        Intent intent = new Intent(SearchCourseListActivity.this, MyCourseReviewActivity.class);
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
        super.onResume();


        sCourse = (EditText) findViewById(R.id.searchCourse);
        mSearchButton = (Button) findViewById(R.id.searchButton);


        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get text from each field in register
                coursename = sCourse.getText().toString();
                //String password = mPassword.getText().toString();


                //Check if fields not empty
                if (coursename.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchCourseListActivity.this);
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    ///Remove white spaces from any field
                    /// and make sure they are not empty
                    coursename = coursename.trim();
                    //password = password.trim();

                    String s1 = coursename.substring(0, 1).toUpperCase();
                    coursename = s1 + coursename.substring(1);

//                    courseListView = (ListView) findViewById(android.R.id.list);
                    courseListView.setAdapter(searchCourseAdapter);

                    // Default view is collegeAdapter (all college sorted asc)
                    setListAdapter(searchCourseAdapter);

                    //getListView().setOnItemClickListener();

                    setUpCourseItems();


                }

            }
        });

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
                updateCourseList();
                //break;
            }


        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCourseList() {
        searchCourseAdapter.loadObjects();
        setListAdapter(searchCourseAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateCourseList();
        }
    }

    private void setUpCourseItems() {
        courseListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick courselist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject course = searchCourseAdapter.getItem(position);
                ParseProxyObject course_ppo = new ParseProxyObject(course);

                Intent intent = new Intent(SearchCourseListActivity.this, CourseSingleItem.class);
                intent.putExtra("course", course_ppo);
                intent.putExtra("courseID", course.getObjectId());
                intent.putExtra("courseDescription", course.getString("Description"));
                intent.putExtra("caoCode", course.getString("CAO_Code"));
                intent.putExtra("modeOfStudy", course.getString("Mode_0f_Study"));
                intent.putExtra("qualification", course.getString("Qualification_Type"));
                intent.putExtra("nfqLevel", course.getInt("Level"));
                intent.putExtra("duration", course.getString("Duration"));
                intent.putExtra("courseLevel", course.getString("Course_Level"));
                intent.putExtra("fees", course.getInt("Fees"));
                intent.putExtra("departmentFaculty", course.getString("Department_Faculty"));
                intent.putExtra("averageRating", course.getInt("Average_Rating"));
                intent.putExtra("reviewCount", course.getInt("Review_Count"));
                intent.putExtra("moduleCount", course.getInt("Module_Count"));
                startActivity(intent);

            }
        });
    }
}
