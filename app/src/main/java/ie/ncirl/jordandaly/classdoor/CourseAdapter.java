package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 08/12/2015.
 */
public class CourseAdapter extends ParseQueryAdapter<Course> {


    public CourseAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Course>() {


            public ParseQuery<Course> create() {
                // Here we can configure a ParseQuery to display
                // only associated courses.
                ParseQuery innerQuery = new ParseQuery("College");
                String objectId = CourseListActivity.collegeId;

                Log.d("DEBUG", "collegeId3 is " + objectId);

                innerQuery.whereEqualTo("objectId", objectId);

                ParseQuery query = new ParseQuery("Course");
                query.whereMatchesQuery("College_Id", innerQuery);

                query.orderByAscending("Name");
                return query;
            }
        });
    }


    @Override
    public View getItemView(Course course, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_course, null);
        }

        super.getItemView(course, v, parent);


        TextView descriptionTextView = (TextView) v.findViewById(R.id.course_description);
        descriptionTextView.setText(course.getDescription());
        TextView modeOfStudyTextView = (TextView) v.findViewById(R.id.mode_of_study);
        modeOfStudyTextView.setText(course.getModeOfStudy());


        return v;
    }
}
