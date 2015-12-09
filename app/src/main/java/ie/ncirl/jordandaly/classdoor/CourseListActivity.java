package ie.ncirl.jordandaly.classdoor;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;


import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;


public class CourseListActivity extends ListActivity {


    public static String collegeId;
    ListView courseListView;
    private ParseQueryAdapter<Course> mainCourseAdapter;
    private CourseAdapter courseAdapter;
    //public String getCollegeId() { return collegeId; }


    //private customCustomAdapter customCourseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        //getListView().setClickable(false);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2 is " + collegeId);


        mainCourseAdapter = new ParseQueryAdapter<Course>(this, Course.class);

        mainCourseAdapter.setTextKey("Description");

        mainCourseAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        courseAdapter = new CourseAdapter(this);

        courseListView = (ListView) findViewById(android.R.id.list);
        courseListView.setAdapter(courseAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(courseAdapter);

        //getListView().setOnItemClickListener();

        setUpCourseItems();


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

//            case R.id.action_show_uni: {
//                showUnis();
//                break;
//            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCourseList() {
        courseAdapter.loadObjects();
        setListAdapter(courseAdapter);
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
        courseListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick courselist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject course = courseAdapter.getItem(position);
                ParseProxyObject course_ppo = new ParseProxyObject(course);

                Intent intent = new Intent(CourseListActivity.this, CourseSingleItem.class);
                intent.putExtra("course", course_ppo);
                intent.putExtra("courseID", course.getObjectId());
                intent.putExtra("courseDescription", course.getString("Description"));
                intent.putExtra("averageRating", course.getInt("Average_Rating"));
                intent.putExtra("reviewCount", course.getInt("Review_Count"));
                intent.putExtra("moduleCount", course.getInt("Module_Count"));
                startActivity(intent);

            }
        });
    }


}
