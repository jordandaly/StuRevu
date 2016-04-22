package ie.ncirl.jordandaly.classdoor;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    TextView tv_commentContent;
    int disableBtn = 1;
    ListView replyListView;
    private Button replyListButton;
    private Button addNewReplyButton;
    private ParseProxyObject commentObject = null;
    private String commentTitle;
    private String commentContent;
    private ParseQueryAdapter<Reply> mainReplyAdapter;
    private ReplyAdapter replyAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.comment_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();

        getIntent().setAction("Comment created");


        commentObject = (ParseProxyObject) intent
                .getSerializableExtra("comment");
        commentID = intent.getStringExtra("commentID");
        commentTitle = intent.getStringExtra("commentTitle");
        commentContent = intent.getStringExtra("commentContent");


        //Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));


        // Locate the TextView in singleitemview.xml
        tv_commentTitle = (TextView) findViewById(R.id.comment_title);
        // Load the text into the TextView
        tv_commentTitle.setText(commentTitle);


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
