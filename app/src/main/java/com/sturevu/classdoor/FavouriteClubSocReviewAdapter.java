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
 * Created by jdaly on 06/12/2016.
 */
public class FavouriteClubSocReviewAdapter extends ParseQueryAdapter<Favourite> {

    public FavouriteClubSocReviewAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Favourite>() {
            public ParseQuery<Favourite> create() {
                // Here we can configure a ParseQuery to display
                // Favourite Club Soc reviews
                ParseQuery query = new ParseQuery("Favourite");
                ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                query.whereEqualTo("User_Id", user_id);
                query.whereExists("Review_Id");
                query.include("Review_Id");
                ParseQuery innerQuery = new ParseQuery("Review");
                innerQuery.whereExists("Club_Soc_Id");
                query.whereMatchesQuery("Review_Id", innerQuery);
                query.include("Review_Id.Club_Soc_Id");
                query.include("Review_Id.User_Id");
                query.orderByAscending("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Favourite favourite, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_fav_clubsoc_review, null);
        }

        super.getItemView(favourite, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(favourite.getParseObject("Review_Id").getString("Title"));
        TextView ratingTextView = (TextView) v.findViewById(R.id.rating);
        ratingTextView.setText(favourite.getParseObject("Review_Id").getNumber("Rating").toString());
        TextView clubSocNameTextView = (TextView) v.findViewById(R.id.clubsoc_name);
        clubSocNameTextView.setText(favourite.getParseObject("Review_Id").getParseObject("Club_Soc_Id").getString("Name"));
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((favourite.getParseObject("Review_Id").getCreatedAt().toString()));

        return v;
    }
}
