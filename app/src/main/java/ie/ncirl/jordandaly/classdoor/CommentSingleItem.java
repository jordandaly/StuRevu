package ie.ncirl.jordandaly.classdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jdaly on 21/04/2016.
 */
public class CommentSingleItem extends AppCompatActivity implements View.OnClickListener {

    // Declare Variables

    TextView tv_commentTitle;
    TextView tv_commentContent;

    int disableBtn = 1;
    private Button replyListButton;
    private Button addNewReplyButton;

    private ParseProxyObject commentObject = null;
    private String commentID;
    private String commentTitle;
    private String commentContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.comment_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();


        commentObject = (ParseProxyObject) intent
                .getSerializableExtra("comment");
        commentID = intent.getStringExtra("reviewID");
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


        replyListButton = (Button) findViewById(R.id.replyListButtonId);
        addNewReplyButton = (Button) findViewById(R.id.addNewReplyButtonId);


        replyListButton.setOnClickListener(this);
        addNewReplyButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.replyListButtonId:
//                Intent replyListIntent = new Intent(this, ReplyListActivity.class);
//                Log.d("DEBUG", "commentId is " + commentID);
//                replyListIntent.putExtra("commentId", commentID);
//                startActivity(replyListIntent);
                break;
            case R.id.addNewReplyButtonId:
//                Intent addNewReplyIntent = new Intent(this, NewReplyActivity.class);
//                Log.d("DEBUG", "commentId is " + commentID);
//                addNewReplyIntent.putExtra("commentId", commentID);
//                startActivity(addNewReplyIntent);

        }
    }

}
