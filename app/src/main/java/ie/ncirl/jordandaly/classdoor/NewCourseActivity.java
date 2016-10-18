package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jdaly on 16/10/2016.
 */
public class NewCourseActivity extends Activity {

    public static String collegeId;
    private Course course;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        course = new Course();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2 is " + collegeId);


        // Begin with main data entry view,
        // NewCourseFragment
        setContentView(R.layout.activity_new_course);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewCourseFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Course getCurrentCourse() {
        return course;
    }
}
