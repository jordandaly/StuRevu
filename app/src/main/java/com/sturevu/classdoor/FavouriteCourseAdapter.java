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
 * Created by jdaly on 08/12/2015.
 */
public class FavouriteCourseAdapter extends ParseQueryAdapter<Favourite> {

    public FavouriteCourseAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Favourite>() {
            public ParseQuery<Favourite> create() {
                // Here we can configure a ParseQuery to display
                // favourite courses
                ParseQuery query = new ParseQuery("Favourite");
                ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                query.whereEqualTo("User_Id", user_id);
                query.whereExists("Course_Id");
                query.include("Course_Id");
                query.include("Course_Id.College_Id");
                query.orderByDescending("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Favourite favourite, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_course_search, null);
        }

        super.getItemView(favourite, v, parent);


        TextView descriptionTextView = (TextView) v.findViewById(R.id.course_description);
        descriptionTextView.setText(favourite.getParseObject("Course_Id").getString("Description"));
        TextView collegeInitialsTextView = (TextView) v.findViewById(R.id.college_initials);
        collegeInitialsTextView.setText(favourite.getParseObject("Course_Id").getParseObject("College_Id").getString("Initials"));
        TextView modeOfStudyTextView = (TextView) v.findViewById(R.id.mode_of_study);
        modeOfStudyTextView.setText(favourite.getParseObject("Course_Id").getString("Mode_0f_Study"));

        return v;
    }
}

