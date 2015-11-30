package ie.ncirl.jordandaly.classdoor;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class Home extends Activity {

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CustomAdapter homeToggleAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "College");
        mainAdapter.setTextKey("Name");
        mainAdapter.setImageKey("ImageFile");

        // Initialize the subclass of ParseQueryAdapter
        homeToggleAdapter = new CustomAdapter(this);


        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.collegeList);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        // Initialize toggle button
        Button toggleButton = (Button) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listView.getAdapter() == mainAdapter) {
                    listView.setAdapter(homeToggleAdapter);
                    homeToggleAdapter.loadObjects();
                } else {
                    listView.setAdapter(mainAdapter);
                    mainAdapter.loadObjects();
                }
            }

        });
    }
}

