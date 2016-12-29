package com.sturevu.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 29/11/2016.
 */
public class ModuleReviewAdapter extends ParseQueryAdapter<Review> {

    public ModuleReviewAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Review>() {


            public ParseQuery<Review> create() {
                // Here we can configure a ParseQuery to display
                // only reviews associated to selected course.
                ParseQuery innerQuery = new ParseQuery("Module");
                String module_objectId = ModuleReviewListActivity.moduleId;

                Log.d("DEBUG", "moduleId3 is " + module_objectId);

                innerQuery.whereEqualTo("objectId", module_objectId);

                ParseQuery query = new ParseQuery("Review");
                query.whereMatchesQuery("Module_Id", innerQuery);
                query.include("User_Id");
                query.orderByDescending("createdAt");
                return query;
            }
        });
    }


    @Override
    public View getItemView(Review review, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_review, null);
        }

        super.getItemView(review, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(review.getTitle());
        TextView ratingTextView = (TextView) v.findViewById(R.id.rating);
        ratingTextView.setText(review.getRating().toString());
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((review.getCreatedAt().toString()));


        return v;
    }
}
