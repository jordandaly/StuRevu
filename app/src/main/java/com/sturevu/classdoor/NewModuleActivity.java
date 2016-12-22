package com.sturevu.classdoor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jdaly on 29/11/2016.
 */
public class NewModuleActivity extends Activity {

    public static String courseId;
    private Module module;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        module = new Module();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        courseId = intent.getStringExtra("courseId");
        Log.d("DEBUG", "courseId2 is " + courseId);


        // Begin with main data entry view,
        // NewCourseFragment
        setContentView(R.layout.activity_new_module);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewModuleFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public Module getCurrentModule() {
        return module;
    }
}
