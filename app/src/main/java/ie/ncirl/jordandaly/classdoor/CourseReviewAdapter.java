package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 10/12/2015.
 */
public class CourseReviewAdapter extends ParseQueryAdapter<Review> {

    public CourseReviewAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Review>() {


            public ParseQuery<Review> create() {
                // Here we can configure a ParseQuery to display
                // only reviews associated to selected course.
                ParseQuery innerQuery = new ParseQuery("Course");
                String course_objectId = CourseReviewListActivity.courseId;

                Log.d("DEBUG", "courseId3 is " + course_objectId);

                innerQuery.whereEqualTo("objectId", course_objectId);

                ParseQuery query = new ParseQuery("Review");
                query.whereMatchesQuery("Course_Id", innerQuery);

                query.orderByAscending("createdAt");
                return query;
            }
        });
    }


    @Override
    public View getItemView(Review review, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_review, null);
        }

        super.getItemView(review, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(review.getTitle());
        TextView ratingTextView = (TextView) v.findViewById(R.id.rating);
        ratingTextView.setText(review.getRating());
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((review.getCreatedAt().toString()));


        return v;
    }
}
