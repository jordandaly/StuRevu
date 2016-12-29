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
 * Created by jdaly on 05/12/2016.
 */
public class FavouriteClubSocAdapter extends ParseQueryAdapter<Favourite> {

    public FavouriteClubSocAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Favourite>() {
            public ParseQuery<Favourite> create() {
                // Here we can configure a ParseQuery to display
                // favourite clubsocs
                ParseQuery query = new ParseQuery("Favourite");
                ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                query.whereEqualTo("User_Id", user_id);
                query.whereExists("Club_Soc_Id");
                query.include("Club_Soc_Id");
                query.include("Club_Soc_Id.College_Id");
                query.orderByDescending("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Favourite favourite, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_clubsoc_search, null);
        }

        super.getItemView(favourite, v, parent);


        TextView nameTextView = (TextView) v.findViewById(R.id.clubsoc_name);
        nameTextView.setText(favourite.getParseObject("Club_Soc_Id").getString("Name"));
        TextView collegeInitialsTextView = (TextView) v.findViewById(R.id.college_initials);
        collegeInitialsTextView.setText(favourite.getParseObject("Club_Soc_Id").getParseObject("College_Id").getString("Initials"));
        TextView typeTextView = (TextView) v.findViewById(R.id.clubsoc_type);
        typeTextView.setText(favourite.getParseObject("Club_Soc_Id").getString("Type"));

        return v;
    }
}
