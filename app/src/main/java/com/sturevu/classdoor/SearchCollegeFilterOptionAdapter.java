package com.sturevu.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 01/01/2017.
 */
public class SearchCollegeFilterOptionAdapter extends ParseQueryAdapter<College>  {

    public SearchCollegeFilterOptionAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<College>() {
            public ParseQuery<College> create() {
                // Here we can configure a ParseQuery to display
                // colleges
                ParseQuery query = new ParseQuery("College");

                String college_type = SearchCollegeFilterOptionActivity.collegeType.getSelectedItem().toString();
                Log.d("DEBUG", "college_type is " + college_type);
                query.whereEqualTo("College_Type", college_type);

                String college_country = SearchCollegeFilterOptionActivity.collegeCountry.getSelectedItem().toString();
                Log.d("DEBUG", "course_level is " + college_country);
                query.whereEqualTo("Country", college_country);

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
