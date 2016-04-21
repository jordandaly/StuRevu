package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 21/04/2016.
 */
public class CommentListActivity extends ListActivity {

    public static String reviewId;
    ListView commentListView;
    private ParseQueryAdapter<Comment> mainCommentAdapter;
    private CommentAdapter commentAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);


        Intent intent = getIntent();
        reviewId = intent.getStringExtra("reviewId");
        Log.d("DEBUG", "reviewId1 is " + reviewId);


        mainCommentAdapter = new ParseQueryAdapter<Comment>(this, Comment.class);

        mainCommentAdapter.setTextKey("Title");

        mainCommentAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        commentAdapter = new CommentAdapter(this);

        commentListView = (ListView) findViewById(android.R.id.list);
        commentListView.setAdapter(commentAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(commentAdapter);

        //getListView().setOnItemClickListener();

        setUpCommentItems();


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_action_comment_list, menu);
//        return true;
//    }

    /*
     * "Show Unis" and refreshing the "show all" list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateCommentList();
                //break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCommentList() {
        commentAdapter.loadObjects();
        setListAdapter(commentAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateCommentList();
        }
    }

    private void setUpCommentItems() {
        commentListView.setOnItemClickListener(new OnItemClickListener() {
            public static final String TAG = "buttonClick commentlist";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");

                ParseObject comment = commentAdapter.getItem(position);
                ParseProxyObject comment_ppo = new ParseProxyObject(comment);

                Intent intent = new Intent(CommentListActivity.this, CommentSingleItem.class);
                intent.putExtra("comment", comment_ppo);
                intent.putExtra("commentID", comment.getObjectId());
                intent.putExtra("commentTitle", comment.getString("Title"));
                intent.putExtra("commentContent", comment.getString("Content"));
                startActivity(intent);

            }
        });
    }

}
