package com.sturevu.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 21/04/2016.
 */
public class CommentAdapter extends ParseQueryAdapter<Comment> {

    public CommentAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Comment>() {


            public ParseQuery<Comment> create() {

                String review_objectId = CommentListActivity.reviewId;

                // Here we can configure a ParseQuery to display
                // only comments associated to selected review.
                ParseQuery innerQuery = new ParseQuery("Review");
//                String review_objectId = CommentListActivity.reviewId;

                Log.d("DEBUG", "reviewId2 is " + review_objectId);

                innerQuery.whereEqualTo("objectId", review_objectId);

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
