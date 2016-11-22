package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 22/11/2016.
 */
public class ClubSocAdapter extends ParseQueryAdapter<Club_Soc> {

    public ClubSocAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Club_Soc>() {


            public ParseQuery<Club_Soc> create() {
                // Here we can configure a ParseQuery to display
                // only associated clubs & societies.
                ParseQuery innerQuery = new ParseQuery("College");
                String objectId = CourseListActivity.collegeId;

                Log.d("DEBUG", "collegeId3 is " + objectId);

                innerQuery.whereEqualTo("objectId", objectId);

                ParseQuery query = new ParseQuery("Club_Soc");
                query.whereMatchesQuery("College_Id", innerQuery);

                query.orderByAscending("Name");
                return query;
            }
        });
    }


    @Override
    public View getItemView(Club_Soc club_soc, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_clubsoc, null);
        }

        super.getItemView(club_soc, v, parent);

        TextView nameTextView = (TextView) v.findViewById(R.id.clubsoc_name);
        nameTextView.setText(club_soc.getName());
        TextView descriptionTextView = (TextView) v.findViewById(R.id.clubsoc_description);
        descriptionTextView.setText(club_soc.getDescription());
        TextView typeTextView = (TextView) v.findViewById(R.id.clubsoc_type);
        typeTextView.setText(club_soc.getType());


        return v;
    }
}
