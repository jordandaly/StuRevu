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
 * Created by jdaly on 24/04/2016.
 */
public class FavouriteCollegeReviewAdapter extends ParseQueryAdapter<Favourite> {

    public FavouriteCollegeReviewAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Favourite>() {
            public ParseQuery<Favourite> create() {
                // Here we can configure a ParseQuery to display
                // required data
                ParseQuery query = new ParseQuery("Favourite");
                ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                query.whereEqualTo("User_Id", user_id);
                query.whereExists("Review_Id");
                query.include("Review_Id");
                ParseQuery innerQuery = new ParseQuery("Review");
                innerQuery.whereExists("College_Id");
                query.whereMatchesQuery("Review_Id", innerQuery);
                query.include("Review_Id.College_Id");
                query.include("Review_Id.User_Id");
                query.orderByDescending("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Favourite favourite, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_fav_college_review, null);
        }

        super.getItemView(favourite, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(favourite.getParseObject("Review_Id").getString("Title"));
        TextView ratingTextView = (TextView) v.findViewById(R.id.rating);
        ratingTextView.setText(favourite.getParseObject("Review_Id").getNumber("Rating").toString());
        TextView collegeInitialsTextView = (TextView) v.findViewById(R.id.college_initials);
        collegeInitialsTextView.setText(favourite.getParseObject("Review_Id").getParseObject("College_Id").getString("Initials"));
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((favourite.getParseObject("Review_Id").getCreatedAt().toString()));

        return v;
    }
}

