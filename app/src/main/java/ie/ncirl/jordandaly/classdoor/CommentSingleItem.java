package ie.ncirl.jordandaly.classdoor;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 21/04/2016.
 */
public class CommentSingleItem extends ListActivity implements View.OnClickListener {

    // Declare Variables

    public static String commentID;
    TextView tv_commentTitle;
    TextView tv_authorComment;
    TextView tv_commentContent;
    int disableBtn = 1;
    ListView replyListView;
    private Button replyListButton;
    private Button addNewReplyButton;
    private ParseProxyObject commentObject = null;
    private String commentTitle;
    private String authorComment;
    private String commentContent;
    private ParseQueryAdapter<Reply> mainReplyAdapter;
    private ReplyAdapter replyAdapter;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
//        setContentView(R.layout.comment_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();

        getIntent().setAction("Comment created");


        commentObject = (ParseProxyObject) intent
                .getSerializableExtra("comment");
        commentID = intent.getStringExtra("commentID");
        commentTitle = intent.getStringExtra("commentTitle");
        authorComment = intent.getStringExtra("username");
        commentContent = intent.getStringExtra("commentContent");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));

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
        delegate.setContentView(R.layout.comment_single_item);



        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        delegate.setSupportActionBar(toolbar);

        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setHomeButtonEnabled(true);


        // Locate the TextView in singleitemview.xml
        tv_commentTitle = (TextView) findViewById(R.id.comment_title);
        // Load the text into the TextView
        tv_commentTitle.setText(commentTitle);

        // Locate the TextView in singleitemview.xml
        tv_authorComment = (TextView) findViewById(R.id.author_comment);
        // Load the text into the TextView
        tv_authorComment.setText(authorComment);


        // Locate the TextView in singleitemview.xml
        tv_commentContent = (TextView) findViewById(R.id.comment_content);
        // Load the text into the TextView
        tv_commentContent.setText(commentContent);


//        replyListButton = (Button) findViewById(R.id.replyListButtonId);
        addNewReplyButton = (Button) findViewById(R.id.addNewReplyButtonId);


//        replyListButton.setOnClickListener(this);
        addNewReplyButton.setOnClickListener(this);


        mainReplyAdapter = new ParseQueryAdapter<Reply>(this, Reply.class);

        mainReplyAdapter.setTextKey("Content");

        mainReplyAdapter.loadObjects();


        // Subclass of ParseQueryAdapter
        replyAdapter = new ReplyAdapter(this);

        replyListView = (ListView) findViewById(android.R.id.list);
        replyListView.setAdapter(replyAdapter);

        // Default view is collegeAdapter (all college sorted asc)
        setListAdapter(replyAdapter);


        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        addDrawerItems();
        setupDrawer();



    }

    @Override
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("Comment created")) {
            Log.v("Example", "Force restart");
            Intent intent = getIntent();
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();
    }

    private void addDrawerItems() {
        String[] osArray = {"College List", "Search Courses", "Favourite Colleges", "Favourite Courses", "Favourite College Reviews", "Favourite Course Reviews"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CollegeListActivity.this, "Navigation drawer!", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(CommentSingleItem.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(CommentSingleItem.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(CommentSingleItem.this, FavouriteCollegeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(CommentSingleItem.this, FavouriteCourseActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(CommentSingleItem.this, FavouriteCollegeReviewActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(CommentSingleItem.this, FavouriteCourseReviewActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_action_review_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
                updateReplyList();
                //break;
            }


        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_action_comment_list, menu);
//        return true;
//    }


    private void updateReplyList() {
        replyAdapter.loadObjects();
        setListAdapter(replyAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateReplyList();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.replyListButtonId:
//                Intent replyListIntent = new Intent(this, ReplyListActivity.class);
//                Log.d("DEBUG", "commentId is " + commentID);
//                replyListIntent.putExtra("commentId", commentID);
//                startActivity(replyListIntent);
//                break;
            case R.id.addNewReplyButtonId:
                Intent addNewReplyIntent = new Intent(this, NewReplyActivity.class);
                Log.d("DEBUG", "commentId is " + commentID);
                addNewReplyIntent.putExtra("commentId", commentID);
                startActivity(addNewReplyIntent);

        }
    }

}
