package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by jdaly on 08/12/2015.
 */
public class FavouriteCollegeAdapter extends ParseQueryAdapter<Favourite> {

    public FavouriteCollegeAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Favourite>() {
            public ParseQuery<Favourite> create() {
                // Here we can configure a ParseQuery to display
                // only universities.
                ParseQuery query = new ParseQuery("Favourite");
                ParseObject user_id = ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId());
                query.whereEqualTo("User_Id", user_id);
                query.whereExists("College_Id");
                query.include("College_Id");
                query.orderByAscending("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Favourite favourite, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_college, null);
        }

        super.getItemView(favourite, v, parent);

        ParseImageView collegeImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = favourite.getParseObject("College_Id").getParseFile("ImageFile");
        if (photoFile != null) {
            collegeImage.setParseFile(photoFile);
            collegeImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView nameTextView = (TextView) v.findViewById(R.id.college_name);
        nameTextView.setText(favourite.getParseObject("College_Id").getString("Name"));
        TextView collegeTypeTextView = (TextView) v.findViewById(R.id.college_type);
        collegeTypeTextView.setText(favourite.getParseObject("College_Id").getString("College_Type"));

        return v;
    }
}

