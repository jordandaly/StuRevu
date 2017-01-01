package com.sturevu.classdoor;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 01/01/2017.
 */
public class SearchCollegeFilterOptionActivity extends ListActivity {

    public static Spinner collegeType;
    public static Spinner collegeCountry;


    //    protected EditText sCourse;
    protected Button mSearchButton;
    protected TextView basicSearch;

    ListView collegeListView;
    private ParseQueryAdapter<College> mainCollegeAdapter;
    private SearchCollegeFilterOptionAdapter searchCollegeFilterOptionAdapter;

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
        delegate.setContentView(R.layout.activity_filter_search);

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
        delegate.getSupportActionBar().setTitle("College Search Filter Options");


        mainCollegeAdapter = new ParseQueryAdapter<College>(this, "College");

        mainCollegeAdapter.setTextKey("Name");

        mainCollegeAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        searchCollegeFilterOptionAdapter = new SearchCollegeFilterOptionAdapter(this);

        collegeListView = (ListView) findViewById(android.R.id.list);
        collegeListView.setVisibility(View.INVISIBLE);
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
        String[] osArray = {"College List", "Search Courses", "Favourites", "My Reviews"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CollegeListActivity.this, "Navigation drawer!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(SearchCollegeFilterOptionActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(SearchCollegeFilterOptionActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(SearchCollegeFilterOptionActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(SearchCollegeFilterOptionActivity.this, MyReviewsActivity.class);
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


//        sCourse = (EditText) findViewById(R.id.searchCourse);
        mSearchButton = (Button) findViewById(R.id.searchButton);
        basicSearch = (TextView) findViewById(R.id.advancedSearch);

        collegeType = ((Spinner) findViewById(R.id.college_type_spinner));
        ArrayAdapter<CharSequence> collegeTypeSpinnerAdapter = ArrayAdapter
                .createFromResource(SearchCollegeFilterOptionActivity.this, R.array.college_type_array,
                        android.R.layout.simple_spinner_dropdown_item);
        collegeType.setAdapter(collegeTypeSpinnerAdapter);

        collegeCountry = ((Spinner) findViewById(R.id.college_country_spinner));
        ArrayAdapter<CharSequence> collegeCountrySpinnerAdapter = ArrayAdapter
                .createFromResource(SearchCollegeFilterOptionActivity.this, R.array.college_country_array,
                        android.R.layout.simple_spinner_dropdown_item);
        collegeCountry.setAdapter(collegeCountrySpinnerAdapter);



        basicSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basicSearchIntent = new Intent(SearchCollegeFilterOptionActivity.this, SearchCollegeActivity.class);
                startActivity(basicSearchIntent);

            }

        });



        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get text from each field in register





                String collegeTypeInput = collegeType.getSelectedItem().toString();

                String collegeCountryInput = collegeCountry.getSelectedItem().toString();



                //Check if fields not empty
                if (collegeTypeInput.equals("") || collegeCountryInput.equals("")) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchCollegeFilterOptionActivity.this);
                    builder.setMessage(R.string.inputValidation)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


//                    courseListView = (ListView) findViewById(android.R.id.list);
                    collegeListView.setAdapter(searchCollegeFilterOptionAdapter);

                    // Default view is collegeAdapter (all college sorted asc)
                    setListAdapter(searchCollegeFilterOptionAdapter);

                    //getListView().setOnItemClickListener();

                    setUpCollegeItems();


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

        getMenuInflater().inflate(R.menu.activity_action_list, menu);
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


        }

        //Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateCollegeList() {
        searchCollegeFilterOptionAdapter.loadObjects();
//        setListAdapter(collegeAdapter);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new college has been added, update
            // the list of colleges
            updateCollegeList();
        }
    }

    private void setUpCollegeItems() {
        collegeListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick collegelist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject college = searchCollegeFilterOptionAdapter.getItem(position);
                ParseProxyObject college_ppo = new ParseProxyObject(college);

                Intent intent = new Intent(SearchCollegeFilterOptionActivity.this, CollegeSingleItem.class);
                intent.putExtra("college", college_ppo);
                intent.putExtra("collegeID", college.getObjectId());
                intent.putExtra("collegeName", college.getString("Name"));
                intent.putExtra("initials", college.getString("Initials"));
                intent.putExtra("collegeType", college.getString("College_Type"));
                startActivity(intent);

            }
        });
    }
}
