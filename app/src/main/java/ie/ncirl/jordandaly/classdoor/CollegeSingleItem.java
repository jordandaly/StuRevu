package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by jdaly on 08/12/2015.
 */
public class CollegeSingleItem extends Activity {
    // Declare Variables
    TextView textname;
    String name;

    private ParseProxyObject collegeObject = null;
    private String collegeID;
    private String collegeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from xml
        setContentView(R.layout.college_single_item);


        // Retrieve data from college list activity on item click event
        Intent intent = getIntent();


        collegeObject = (ParseProxyObject) intent
                .getSerializableExtra("college");
        collegeID = intent.getStringExtra("collegeID");
        collegeName = intent.getStringExtra("collegeName");


        Log.v("Test", String.format("Proxy object name: %s", collegeObject.getString("college")));


        // Get the name
        name = intent.getStringExtra("collegeName");

        // Locate the TextView in singleitemview.xml
        textname = (TextView) findViewById(R.id.name);

        // Load the text into the TextView
        textname.setText(name);



    }
}
