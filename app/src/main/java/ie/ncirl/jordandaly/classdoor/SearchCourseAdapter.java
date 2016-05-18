package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 20/04/2016.
 */
public class SearchCourseAdapter extends ParseQueryAdapter<Course> {


    public SearchCourseAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Course>() {


            public ParseQuery<Course> create() {
                // Here we can configure a ParseQuery to search
                // based on course name starts with.
                ParseQuery query = new ParseQuery("Course");
                String course_name = SearchCourseListActivity.coursename;
                Log.d("DEBUG", "course_name is " + course_name);
                query.whereStartsWith("Name", course_name);
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
