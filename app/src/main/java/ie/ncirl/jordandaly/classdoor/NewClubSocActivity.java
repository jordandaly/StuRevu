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
 * Created by jdaly on 22/11/2016.
 */
public class NewClubSocActivity extends Activity {

    public static String collegeId;
    private Club_Soc club_soc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        club_soc = new Club_Soc();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");
        Log.d("DEBUG", "collegeId2 is " + collegeId);


        // Begin with main data entry view,
        // NewCourseFragment
        setContentView(R.layout.activity_new_clubsoc);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewClubSocFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Club_Soc getCurrentClubSoc() {
        return club_soc;
    }
}
