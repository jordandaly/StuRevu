package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by jdaly on 08/12/2015.
 */
public class CollegeSingleItem extends Activity {
    // Declare Variables
    TextView txtname;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.college_single_item);

        // Retrieve data from cocllege list activity on item click event
        Intent i = getIntent();

        // Get the name
        name = i.getStringExtra("name");

        // Locate the TextView in singleitemview.xml
        txtname = (TextView) findViewById(R.id.name);

        // Load the text into the TextView
        txtname.setText(name);

    }
}
