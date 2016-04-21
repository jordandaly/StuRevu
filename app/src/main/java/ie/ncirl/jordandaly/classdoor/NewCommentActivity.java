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
 * Created by jdaly on 21/04/2016.
 */
public class NewCommentActivity extends Activity {

    public static String reviewId;
    private Comment comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        comment = new Comment();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        reviewId = intent.getStringExtra("reviewId");
        Log.d("DEBUG", "reviewId is " + reviewId);


        // Begin with main data entry view,
        // NewCommentFragment
        setContentView(R.layout.activity_new_comment);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewCommentFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Comment getCurrentComment() {
        return comment;
    }
}
