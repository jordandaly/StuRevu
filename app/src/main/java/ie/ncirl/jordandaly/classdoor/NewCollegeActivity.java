package ie.ncirl.jordandaly.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jdaly on 15/10/2016.
 */
public class NewCollegeActivity extends Activity {


    private College college;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        college = new College();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);


        // Begin with main data entry view,
        // NewCollegeFragment
        setContentView(R.layout.activity_new_college);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewCollegeFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public College getCurrentCollege() {
        return college;
    }
}
