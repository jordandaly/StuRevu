package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 22/04/2016.
 */
public class ReplyAdapter extends ParseQueryAdapter<Reply> {

    public ReplyAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Reply>() {


            public ParseQuery<Reply> create() {
                // Here we can configure a ParseQuery to display
                // only comments associated to selected review.
                ParseQuery innerQuery = new ParseQuery("Comment");
                String comment_objectId = CommentSingleItem.commentID;

                Log.d("DEBUG", "commentId is " + comment_objectId);

                innerQuery.whereEqualTo("objectId", comment_objectId);

                ParseQuery query = new ParseQuery("Reply");
                query.whereMatchesQuery("Comment_Id", innerQuery);

                query.orderByAscending("createdAt");
                return query;
            }
        });
    }


    @Override
    public View getItemView(Reply reply, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_reply, null);
        }

        super.getItemView(reply, v, parent);


        TextView titleTextView = (TextView) v.findViewById(R.id.reply_content);
        titleTextView.setText(reply.getContent());
        TextView createdAtTextView = (TextView) v.findViewById(R.id.created_at);
        createdAtTextView.setText((reply.getCreatedAt().toString()));


        return v;
    }
}
