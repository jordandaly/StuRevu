package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 09/12/2015.
 */
public class CollegeReviewListActivity extends ListActivity {

    public static String collegeId;
    ListView reviewListView;
    private ParseQueryAdapter<Review> mainReviewAdapter;
    private CollegeReviewAdapter reviewAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);


        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2r is " + collegeId);


        mainReviewAdapter = new ParseQueryAdapter<Review>(this, Review.class);

        mainReviewAdapter.setTextKey("Title");

        mainReviewAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        reviewAdapter = new CollegeReviewAdapter(this);

        reviewListView = (ListView) findViewById(android.R.id.list);
        reviewListView.setAdapter(reviewAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(reviewAdapter);

        //getListView().setOnItemClickListener();

        setUpReviewItems();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_action_review_list, menu);
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
                //break;
            }

//            case R.id.action_show_uni: {
//                showUnis();
//                break;
//            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateReviewList() {
        reviewAdapter.loadObjects();
        setListAdapter(reviewAdapter);
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

                Intent intent = new Intent(CollegeReviewListActivity.this, ReviewSingleItem.class);
                intent.putExtra("review", review_ppo);
                intent.putExtra("reviewID", review.getObjectId());
                intent.putExtra("reviewTitle", review.getString("Title"));
                intent.putExtra("rating", review.getDouble("Rating"));
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
