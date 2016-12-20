package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 20/12/2016.
 */
public class ModuleCommentAdapter extends ParseQueryAdapter<Comment> {

    public ModuleCommentAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Comment>() {


            public ParseQuery<Comment> create() {

                String module_objectId = CommentListActivity.moduleId;

                // Here we can configure a ParseQuery to display
                // only comments associated to selected review.
                ParseQuery innerQuery = new ParseQuery("Module");


                Log.d("DEBUG", "moduleId2 is " + module_objectId);

                innerQuery.whereEqualTo("objectId", module_objectId);

                ParseQuery query = new ParseQuery("Comment");
                query.whereMatchesQuery("Module_Id", innerQuery);
                query.include("User_Id");
                query.orderByAscending("createdAt");
                return query;


            }
        });
    }


    @Override
    public View getItemView(Comment comment, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_comment, null);
        }

        super.getItemView(comment, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(comment.getTitle());
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((comment.getCreatedAt().toString()));


        return v;
    }
}
