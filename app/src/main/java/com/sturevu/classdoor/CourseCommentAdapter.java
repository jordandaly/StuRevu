package com.sturevu.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 20/12/2016.
 */
public class CourseCommentAdapter extends ParseQueryAdapter<Comment> {

    public CourseCommentAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Comment>() {


            public ParseQuery<Comment> create() {

                String course_objectId = CommentListActivity.courseId;

                // Here we can configure a ParseQuery to display
                // only comments associated to selected review.
                ParseQuery innerQuery = new ParseQuery("Course");


                Log.d("DEBUG", "courseId2 is " + course_objectId);

                innerQuery.whereEqualTo("objectId", course_objectId);

                ParseQuery query = new ParseQuery("Comment");
                query.whereMatchesQuery("Review_Id", innerQuery);
                query.include("User_Id");
                query.orderByAscending("createdAt");
                return query;


            }
        });
    }


    @Override
    public View getItemView(Comment comment, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_comment, null);
        }

        super.getItemView(comment, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(comment.getTitle());
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((comment.getCreatedAt().toString()));


        return v;
    }
}
