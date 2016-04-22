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
 * Created by jdaly on 22/04/2016.
 */
public class NewReplyActivity extends Activity {

    public static String commentId;
    private Reply reply;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        reply = new Reply();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        commentId = intent.getStringExtra("commentId");
        Log.d("DEBUG", "commentId is " + commentId);


        // Begin with main data entry view,
        // NewReplyFragment
        setContentView(R.layout.activity_new_reply);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewReplyFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Reply getCurrentReply() {
        return reply;
    }
}
