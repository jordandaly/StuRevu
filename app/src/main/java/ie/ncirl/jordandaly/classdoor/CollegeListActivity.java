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


public class CollegeListActivity extends ListActivity {


    ListView collegeListView;
    private ParseQueryAdapter<College> mainCollegeAdapter;
    private CollegeAdapter collegeAdapter;
    private CustomAdapter customAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        //getListView().setClickable(false);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_action_college_list, menu);
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
                updateCollegeList();
                break;
            }

            case R.id.action_show_uni: {
                showUnis();
                break;
            }

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