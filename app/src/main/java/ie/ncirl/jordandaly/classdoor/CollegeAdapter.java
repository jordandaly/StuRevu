package ie.ncirl.jordandaly.classdoor;

import java.util.Arrays;

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

/**
 * Created by jdaly on 08/12/2015.
 */
public class CollegeAdapter extends ParseQueryAdapter<College> {

    public CollegeAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<College>() {
            public ParseQuery<College> create() {
                // Here we can configure a ParseQuery to display
                // only universities.
                ParseQuery query = new ParseQuery("College");
                query.orderByAscending("Name");
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

        TextView nameTextView = (TextView) v.findViewById(R.id.college_name);
        nameTextView.setText(college.getName());
        TextView collegeTypeTextView = (TextView) v.findViewById(R.id.college_type);
        collegeTypeTextView.setText(college.getCollege_Type());

        return v;
    }
}
