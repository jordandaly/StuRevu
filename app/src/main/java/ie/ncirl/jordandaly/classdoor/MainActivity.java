package ie.ncirl.jordandaly.classdoor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* testing
        College college = new College("Test Educational Institution");
        college.saveInBackground();

        College_Review college_review = new College_Review("worth the time effort and expense eventually");
        college_review.setCollege(college);
        //College_Review.setOwner(currentUser);
        college_review.saveInBackground();

        Course course = new Course("Test education course");
        // Add a relation between the College with objectId "1zEcyElZ80" and the course
        course.put("parent", ParseObject.createWithoutData("College", "c56QqJ9wGc"));
        course.saveInBackground();

        Course_Review course_review = new Course_Review("learned valuable skills, especially recommend module x");
        course_review.setCourse(course);
        //College_Review.setOwner(currentUser);
        course_review.saveInBackground();


        Club_Soc club_soc = new Club_Soc("Test club/soc");
        // Add a relation between the College with objectId "c56QqJ9wGc" and the club/soc
        club_soc.put("parent", ParseObject.createWithoutData("College", "c56QqJ9wGc"));
        club_soc.saveInBackground();

        Club_Soc_Review club_soc_review = new Club_Soc_Review("great events and people for socialising and fun");
        club_soc_review.setClub_Soc(club_soc);
        //College_Review.setOwner(currentUser);
        club_soc_review.saveInBackground();

        Module module = new Module("Test module");
        // Add a relation between the Course with objectId "aLKnZlQMoU" and the module
        module.put("parent", ParseObject.createWithoutData("Course", "aLKnZlQMoU"));
        module.saveInBackground();

        Module_Review module_review = new Module_Review("excellent project work and fair exam");
        module_review.setModule(module);
        //College_Review.setOwner(currentUser);
        module_review.saveInBackground();
        testing */



        // Set the current user, assuming a user is signed in
        //<object>.setOwner(ParseUser.getCurrentUser());
        // Immediately save the data asynchronously

        // or for a more robust offline save
        // <object>.saveEventually();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
