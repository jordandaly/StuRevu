package com.sturevu.classdoor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by jdaly on 10/12/2016.
 */
public class MyClubSocReviewAdapter extends ParseQueryAdapter<Review> {

    public MyClubSocReviewAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Review>() {
            public ParseQuery<Review> create() {
                // Here we can configure a ParseQuery to display
                // required data
                ParseQuery query = new ParseQuery("Review");
                ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                query.whereEqualTo("User_Id", user_id);
                query.whereExists("Club_Soc_Id");
                query.include("Club_Soc_Id");
                query.include("User_Id");
                query.orderByAscending("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Review review, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_fav_clubsoc_review, null);
        }

        super.getItemView(review, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(review.getString("Title"));
        TextView ratingTextView = (TextView) v.findViewById(R.id.rating);
        ratingTextView.setText(review.getNumber("Rating").toString());
        TextView clubSocNameTextView = (TextView) v.findViewById(R.id.clubsoc_name);
        clubSocNameTextView.setText(review.getParseObject("Club_Soc_Id").getString("Name"));
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((review.getCreatedAt().toString()));

        return v;
    }
}
