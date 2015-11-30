package ie.ncirl.jordandaly.classdoor;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by jdaly on 06/10/2015.
 */
public class ParseApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "XrA24jVj95M56K3IhahBvZpEOm2uTYH7gAWc3dEE";
    public static final String YOUR_CLIENT_KEY = "qhIrrZKuq0praxDmPSLfyRobWNez1ZI78Lf3BNI4";




    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(College.class);
        ParseObject.registerSubclass(Review.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Club_Soc.class);
        ParseObject.registerSubclass(Module.class);



        /*
        Enable Local Datastore.
        Add your initialization code here
        */
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        // ...
    }
}
