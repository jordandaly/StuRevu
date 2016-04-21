package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        //getListView().setClickable(false);


        mainCourseAdapter = new ParseQueryAdapter<Course>(this, Course.class);

        mainCourseAdapter.setTextKey("Description");

        mainCourseAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        searchCourseAdapter = new SearchCourseAdapter(this);

        courseListView = (ListView) findViewById(android.R.id.list);
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


                ///Remove white spaces from any field
                /// and make sure they are not empty
                coursename = coursename.trim();
                //password = password.trim();

                //Check if fields not empty
                if (coursename.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchCourseListActivity.this);
                    builder.setMessage(R.string.error)
                            .setTitle(R.string.error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_action_course_list, menu);
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
                updateCourseList();
                //break;
            }


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
