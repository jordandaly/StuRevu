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
 * Created by jdaly on 19/12/2016.
 */
public class NewReportActivity extends Activity {
    public static String collegeId;
    public static String courseId;
    public static String clubsocId;
    public static String moduleId;
    public static String reviewId;
    public static String commentId;

    private Report report;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        report = new Report();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2 is " + collegeId);

        courseId = intent.getStringExtra("courseId");
        Log.d("DEBUG", "courseId2 is " + courseId);

        clubsocId = intent.getStringExtra("clubsocId");
        Log.d("DEBUG", "clubsocId2 is " + clubsocId);

        moduleId = intent.getStringExtra("moduleId");
        Log.d("DEBUG", "moduleId2 is " + moduleId);

        reviewId = intent.getStringExtra("reviewId");
        Log.d("DEBUG", "reviewId2 is " + reviewId);

        commentId = intent.getStringExtra("commentId");
        Log.d("DEBUG", "commentId2 is " + commentId);

        // Begin with main data entry view,
        // NewReviewFragment
        setContentView(R.layout.activity_new_report);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewReportFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Report getCurrentReport() {
        return report;
    }
}
