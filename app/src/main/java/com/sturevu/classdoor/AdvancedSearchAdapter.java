package com.sturevu.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 22/12/2016.
 */
public class AdvancedSearchAdapter extends ParseQueryAdapter<Course> {

    public AdvancedSearchAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Course>() {


            public ParseQuery<Course> create() {
                // Here we can configure a ParseQuery to search
                // based on course name starts with.
                ParseQuery query = new ParseQuery("Course");

                String mode_of_study = AdvancedSearchListActivity.modeOfStudy.getSelectedItem().toString();
                Log.d("DEBUG", "mode_of_study is " + mode_of_study);
                query.whereEqualTo("Mode_0f_Study", mode_of_study);

                String course_level = AdvancedSearchListActivity.courseLevel.getSelectedItem().toString();
                Log.d("DEBUG", "course_level is " + course_level);
                query.whereEqualTo("Course_Level", course_level);

//                String qualification_type = AdvancedSearchListActivity.qualificationType.getSelectedItem().toString();
//                Log.d("DEBUG", "qualification_type is " + qualification_type);
//                query.whereEqualTo("Qualification_Type", qualification_type);


                query.orderByAscending("Name");
                query.include("College_Id");
                query.setLimit(200);
                return query;
            }
        });
    }


    @Override
    public View getItemView(Course course, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_course_search, null);
        }

        super.getItemView(course, v, parent);


        TextView descriptionTextView = (TextView) v.findViewById(R.id.course_description);
        descriptionTextView.setText(course.getDescription());
        TextView collegeInitialsTextView = (TextView) v.findViewById(R.id.college_initials);
        collegeInitialsTextView.setText(course.getParseObject("College_Id").getString("Initials"));
        TextView modeOfStudyTextView = (TextView) v.findViewById(R.id.mode_of_study);
        modeOfStudyTextView.setText(course.getModeOfStudy());


        return v;
    }
}
