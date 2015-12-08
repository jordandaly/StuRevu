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


    ListView listView;
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CollegeAdapter collegeAdapter;
    private CustomAdapter customAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);
        //getListView().setClickable(false);


        mainAdapter = new ParseQueryAdapter<ParseObject>(this, College.class);

        mainAdapter.setTextKey("Name");
        mainAdapter.setImageKey("ImageFile");
        mainAdapter.loadObjects();

        // Subclass of ParseQueryAdapter
        customAdapter = new CustomAdapter(this);

        // Subclass of ParseQueryAdapter
        collegeAdapter = new CollegeAdapter(this);

        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(collegeAdapter);

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
        listView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick log";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject college = collegeAdapter.getItem(position);
                ParseProxyObject ppo = new ParseProxyObject(college);

                Intent intent = new Intent(CollegeListActivity.this, CollegeSingleItem.class);
                intent.putExtra("college", ppo);
                intent.putExtra("collegeID", college.getObjectId());
                intent.putExtra("collegeName", college.getString("Name"));
                startActivity(intent);

            }
        });
    }


}