package com.sturevu.classdoor;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite meals, including a
 * bigger preview image, the meal's rating, and a "favorite"
 * star.
 */

public class CustomAdapter extends ParseQueryAdapter<College> {

    public CustomAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<College>() {
            public ParseQuery<College> create() {
                // Here we can configure a ParseQuery to display
                // only universities.
                ParseQuery query = new ParseQuery("College");
                query.whereEqualTo("College_Type", "University");
                query.orderByAscending("Name");
                return query;
            }
        });
    }

    @Override
    public View getItemView(College college, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_uni, null);
        }

        super.getItemView(college, v, parent);

        ParseImageView collegeImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = college.getParseFile("ImageFile");
        if (photoFile != null) {
            collegeImage.setParseFile(photoFile);
            collegeImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView nameTextView = (TextView) v.findViewById(R.id.text1);
        nameTextView.setText(college.getName());
        TextView initialsTextView = (TextView) v
                .findViewById(R.id.initials);
        initialsTextView.setText(college.getInitials());
        return v;
    }

}