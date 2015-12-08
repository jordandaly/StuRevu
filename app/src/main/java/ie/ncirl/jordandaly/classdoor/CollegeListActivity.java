package ie.ncirl.jordandaly.classdoor;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class CollegeListActivity extends ListActivity {

    private ParseQueryAdapter<College> mainAdapter;
    private CollegeAdapter collegeAdapter;
    private CustomAdapter customAdapter;
    //ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_college_list);
        getListView().setClickable(true);

        mainAdapter = new ParseQueryAdapter<College>(this, College.class);

        mainAdapter.setTextKey("Name");
        mainAdapter.setImageKey("ImageFile");
        mainAdapter.loadObjects();

        // Subclass of ParseQueryAdapter
        customAdapter = new CustomAdapter(this);

        // Subclass of ParseQueryAdapter
        collegeAdapter = new CollegeAdapter(this);

        //ListView listView = (ListView) findViewById(R.id.collegeList);
        //listView.setAdapter(mainAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(collegeAdapter);


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


}