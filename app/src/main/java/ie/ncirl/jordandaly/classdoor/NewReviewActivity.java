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
 * Created by jdaly on 11/12/2015.
 */
public class NewReviewActivity extends Activity {

    public static String collegeId;
    public static String courseId;
    private Review review;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        review = new Review();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2 is " + collegeId);

        courseId = intent.getStringExtra("courseId");
        Log.d("DEBUG", "collegeId2 is " + courseId);

        // Begin with main data entry view,
        // NewReviewFragment
        setContentView(R.layout.activity_new_review);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewReviewFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Review getCurrentReview() {
        return review;
    }
}
