package com.sturevu.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 31/12/2016.
 */
public class SearchCollegeAdapter extends ParseQueryAdapter<College> {

    public SearchCollegeAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<College>() {
            public ParseQuery<College> create() {
                // Here we can configure a ParseQuery to display
                // colleges
                ParseQuery query = new ParseQuery("College");
                String college_name = SearchCollegeActivity.collegename;
                Log.d("DEBUG", "college_name is " + college_name);
                query.whereStartsWith("Name", college_name);
                query.orderByAscending("Name");
                query.setLimit(200);
                return query;
            }
        });
    }

    @Override
    public View getItemView(College college, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_college, null);
        }

        super.getItemView(college, v, parent);


        TextView initialsTextView = (TextView) v.findViewById(R.id.college_initials);
        initialsTextView.setText(college.getInitials());
        TextView nameTextView = (TextView) v.findViewById(R.id.college_name);
        nameTextView.setText(college.getName());
        TextView collegeTypeTextView = (TextView) v.findViewById(R.id.college_type);
        collegeTypeTextView.setText(college.getCollege_Type());

        return v;
    }
}
