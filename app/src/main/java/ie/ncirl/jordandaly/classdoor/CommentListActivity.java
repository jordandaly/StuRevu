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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 21/04/2016.
 */
public class CommentListActivity extends ListActivity {

    public static String reviewId;
    public static String collegeId;
    public static String courseId;
    public static String moduleId;
    public static String clubsocId;
    ListView commentListView;
    private ParseQueryAdapter<Comment> mainCommentAdapter;
    private CommentAdapter commentAdapter;
    private CollegeCommentAdapter collegeCommentAdapter;
    private CourseCommentAdapter courseCommentAdapter;
    private ClubSocCommentAdapter clubsocCommentAdapter;
    private ModuleCommentAdapter moduleCommentAdapter;



    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_comment_list);


        Intent intent = getIntent();

        reviewId = intent.getStringExtra("reviewId");
        Log.d("DEBUG", "reviewId1 is " + reviewId);

        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId1 is " + collegeId);

        courseId = intent.getStringExtra("courseId");
        Log.d("DEBUG", "courseId1 is " + courseId);

        moduleId = intent.getStringExtra("moduleId");
        Log.d("DEBUG", "moduleId1 is " + moduleId);

        clubsocId = intent.getStringExtra("clubsocId");
        Log.d("DEBUG", "clubsocId1 is " + clubsocId);

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
        delegate.setContentView(R.layout.activity_comment_list);

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
        delegate.getSupportActionBar().setTitle("Comment List");


        //mainCommentAdapter = new ParseQueryAdapter<Comment>(this, Comment.class);
        mainCommentAdapter = new ParseQueryAdapter<Comment>(this, "Comment");

        mainCommentAdapter.setTextKey("Title");

        mainCommentAdapter.loadObjects();

        if (reviewId != null) {
            // Subclass of ParseQueryAdapter
            commentAdapter = new CommentAdapter(this);

            commentListView = (ListView) findViewById(android.R.id.list);
            commentListView.setAdapter(commentAdapter);

            // Default view is commentAdapter (review comments)
            setListAdapter(commentAdapter);
        } else if (collegeId != null) {
            // Subclass of ParseQueryAdapter
            collegeCommentAdapter = new CollegeCommentAdapter(this);

            commentListView = (ListView) findViewById(android.R.id.list);
            commentListView.setAdapter(collegeCommentAdapter);

            // Default view is commentAdapter (review comments)
            setListAdapter(collegeCommentAdapter);
        } else if (courseId != null) {
            // Subclass of ParseQueryAdapter
            courseCommentAdapter = new CourseCommentAdapter(this);

            commentListView = (ListView) findViewById(android.R.id.list);
            commentListView.setAdapter(courseCommentAdapter);

            // Default view is commentAdapter (review comments)
            setListAdapter(courseCommentAdapter);
        } else if (clubsocId != null) {
            // Subclass of ParseQueryAdapter
            clubsocCommentAdapter = new ClubSocCommentAdapter(this);

            commentListView = (ListView) findViewById(android.R.id.list);
            commentListView.setAdapter(clubsocCommentAdapter);

            // Default view is commentAdapter (review comments)
            setListAdapter(clubsocCommentAdapter);
        } else if (moduleId != null) {
            // Subclass of ParseQueryAdapter
            moduleCommentAdapter = new ModuleCommentAdapter(this);

            commentListView = (ListView) findViewById(android.R.id.list);
            commentListView.setAdapter(moduleCommentAdapter);

            // Default view is commentAdapter (review comments)
            setListAdapter(moduleCommentAdapter);
        }

        //getListView().setOnItemClickListener();

        setUpCommentItems();


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
                        Intent intent = new Intent(CommentListActivity.this, CollegeListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(CommentListActivity.this, SearchCourseListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(CommentListActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case 3: {
                        Intent intent = new Intent(CommentListActivity.this, MyReviewsActivity.class);
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
        getMenuInflater().inflate(R.menu.activity_action_comment_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.action_add_comment).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
                updateCommentList();
                break;
            }
            case R.id.action_add_comment: {
            Intent addNewCommentIntent = new Intent(this, NewCommentActivity.class);

                if (collegeId != null) {
                    Log.d("DEBUG", "collegeId2c is " + collegeId);
                    addNewCommentIntent.putExtra("collegeId", collegeId);
                }
                else if (courseId != null){
                    Log.d("DEBUG", "courseId2c is " + courseId);
                    addNewCommentIntent.putExtra("courseId", courseId);
                }
                else if (clubsocId != null){
                    Log.d("DEBUG", "clubsocId2c is " + clubsocId);
                    addNewCommentIntent.putExtra("clubsocId", clubsocId);
                }
                else if (moduleId != null){
                    Log.d("DEBUG", "moduleId2c is " + moduleId);
                    addNewCommentIntent.putExtra("moduleId", moduleId);
                }

                startActivity(addNewCommentIntent);
                break;
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



    private void updateCommentList() {
        if (commentAdapter != null) {
            commentAdapter.loadObjects();
            setListAdapter(commentAdapter);

        } else if (collegeCommentAdapter != null) {
            collegeCommentAdapter.loadObjects();
            setListAdapter(collegeCommentAdapter);

        } else if (courseCommentAdapter != null) {
            courseCommentAdapter.loadObjects();
            setListAdapter(courseCommentAdapter);

        } else if (clubsocCommentAdapter != null) {
            clubsocCommentAdapter.loadObjects();
            setListAdapter(clubsocCommentAdapter);

        } else if (moduleCommentAdapter != null) {
            moduleCommentAdapter.loadObjects();
            setListAdapter(moduleCommentAdapter);

        }

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

                if (commentAdapter != null) {
                    ParseObject comment = commentAdapter.getItem(position);
                    ParseProxyObject comment_ppo = new ParseProxyObject(comment);

                    Intent intent = new Intent(CommentListActivity.this, CommentSingleItem.class);
                    intent.putExtra("comment", comment_ppo);
                    intent.putExtra("commentID", comment.getObjectId());
                    intent.putExtra("commentTitle", comment.getString("Title"));
                    intent.putExtra("username", comment.getParseObject("User_Id").getString("username"));
                    intent.putExtra("commentContent", comment.getString("Content"));
                    startActivity(intent);
                } else if (collegeCommentAdapter != null) {
                    ParseObject comment = collegeCommentAdapter.getItem(position);
                    ParseProxyObject comment_ppo = new ParseProxyObject(comment);

                    Intent intent = new Intent(CommentListActivity.this, CommentSingleItem.class);
                    intent.putExtra("comment", comment_ppo);
                    intent.putExtra("commentID", comment.getObjectId());
                    intent.putExtra("commentTitle", comment.getString("Title"));
                    intent.putExtra("username", comment.getParseObject("User_Id").getString("username"));
                    intent.putExtra("commentContent", comment.getString("Content"));
                    startActivity(intent);
                } else if (courseCommentAdapter != null) {
                    ParseObject comment = courseCommentAdapter.getItem(position);
                    ParseProxyObject comment_ppo = new ParseProxyObject(comment);

                    Intent intent = new Intent(CommentListActivity.this, CommentSingleItem.class);
                    intent.putExtra("comment", comment_ppo);
                    intent.putExtra("commentID", comment.getObjectId());
                    intent.putExtra("commentTitle", comment.getString("Title"));
                    intent.putExtra("username", comment.getParseObject("User_Id").getString("username"));
                    intent.putExtra("commentContent", comment.getString("Content"));
                    startActivity(intent);
                } else if (clubsocCommentAdapter != null) {
                    ParseObject comment = clubsocCommentAdapter.getItem(position);
                    ParseProxyObject comment_ppo = new ParseProxyObject(comment);

                    Intent intent = new Intent(CommentListActivity.this, CommentSingleItem.class);
                    intent.putExtra("comment", comment_ppo);
                    intent.putExtra("commentID", comment.getObjectId());
                    intent.putExtra("commentTitle", comment.getString("Title"));
                    intent.putExtra("username", comment.getParseObject("User_Id").getString("username"));
                    intent.putExtra("commentContent", comment.getString("Content"));
                    startActivity(intent);
                } else if (moduleCommentAdapter != null) {
                    ParseObject comment = moduleCommentAdapter.getItem(position);
                    ParseProxyObject comment_ppo = new ParseProxyObject(comment);

                    Intent intent = new Intent(CommentListActivity.this, CommentSingleItem.class);
                    intent.putExtra("comment", comment_ppo);
                    intent.putExtra("commentID", comment.getObjectId());
                    intent.putExtra("commentTitle", comment.getString("Title"));
                    intent.putExtra("username", comment.getParseObject("User_Id").getString("username"));
                    intent.putExtra("commentContent", comment.getString("Content"));
                    startActivity(intent);
                }



            }
        });
    }

}
