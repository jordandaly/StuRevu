package ie.ncirl.jordandaly.classdoor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by jdaly on 29/11/2016.
 */
public class ModuleAdapter extends ParseQueryAdapter<Module> {

    public ModuleAdapter(Context context) {


        super(context, new ParseQueryAdapter.QueryFactory<Module>() {


            public ParseQuery<Module> create() {
                // Here we can configure a ParseQuery to display
                // only associated modules.
//                ParseQuery innerQuery = new ParseQuery("Course");
//                String objectId = ModuleListActivity.courseId;
//
//                Log.d("DEBUG", "courseId3 is " + objectId);
//
//                innerQuery.whereEqualTo("objectId", objectId);
//
//                ParseQuery query = new ParseQuery("Module");
//                query.whereMatchesQuery("Course_Id", innerQuery);
//                query.include("Course_Id");
//
//                query.orderByAscending("Name");
//                return query;
                ParseQuery query = new ParseQuery("Module");
                String objectId = ModuleListActivity.courseId;
                query.whereEqualTo("Course_Id", objectId);
                query.include("Course_Id");
                query.orderByAscending("Name");
                return query;


//                // set up our query for the Book object
//                ParseQuery moduleQuery = ParseQuery.getQuery("Module");
//
//                String objectId = ModuleListActivity.courseId;
//
//                // configure any constraints on your query...
//                moduleQuery.whereEqualTo("Course_Id", objectId);
//
//                // tell the query to fetch all of the Author objects along with the Book
//                moduleQuery.include("Course_Id");
//
//                // execute the query
//                moduleQuery.findInBackground(newFindCallback<ParseObject>() {
//                    public void done(List<ParseObject> moduleList, ParseException e) {
//
//                    }
//                });
            }
        });
    }


    @Override
    public View getItemView(Module module, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_module, null);
        }

        super.getItemView(module, v, parent);

        TextView nameTextView = (TextView) v.findViewById(R.id.module_name);
        nameTextView.setText(module.getName());
        TextView descriptionTextView = (TextView) v.findViewById(R.id.module_description);
        descriptionTextView.setText(module.getDescription());
        TextView typeTextView = (TextView) v.findViewById(R.id.module_type);
        typeTextView.setText(module.getType());


        return v;
    }
}
