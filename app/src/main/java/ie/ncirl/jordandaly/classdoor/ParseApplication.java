package ie.ncirl.jordandaly.classdoor;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by jdaly on 06/10/2015.
 */
public class ParseApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "XrA24jVj95M56K3IhahBvZpEOm2uTYH7gAWc3dEE";
    public static final String YOUR_CLIENT_KEY = "qhIrrZKuq0praxDmPSLfyRobWNez1ZI78Lf3BNI4";

    // Debugging switch
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "StuRevu";

    // Used to pass location from MapActivity to CollegeSingleItem
    public static final String INTENT_EXTRA_LOCATION = "Geolocation";

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

    private static SharedPreferences preferences;

    private static ConfigHelper configHelper;

    public ParseApplication() {
    }

    public static float getSearchDistance() {
        return preferences.getFloat(KEY_SEARCH_DISTANCE, DEFAULT_SEARCH_DISTANCE);
    }

    public static void setSearchDistance(float value) {
        preferences.edit().putFloat(KEY_SEARCH_DISTANCE, value).commit();
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        // Register your parse models
        ParseObject.registerSubclass(College.class);
        ParseObject.registerSubclass(Review.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Club_Soc.class);
        ParseObject.registerSubclass(Module.class);
        ParseObject.registerSubclass(Favourite.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Reply.class);
        ParseObject.registerSubclass(Report.class);

        preferences = getSharedPreferences("ie.ncirl.jordandaly.classdoor", Context.MODE_PRIVATE);

        configHelper = new ConfigHelper();
//        configHelper.fetchConfigIfNeeded();



        /*
        Enable Local Datastore.
        Add your initialization code here
        */
        Parse.enableLocalDatastore(this);

        //Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId(YOUR_APPLICATION_ID)
                        .clientKey(null)
                        .server("http://sturevu.herokuapp.com/parse/") // The trailing slash is important.
                                //.server("http://localhost:1337/parse/") // The trailing slash is important.


                        .build()
        );

        ParseFacebookUtils.initialize(this);

        // ...

        // Specify a Activity to handle all pushes by default.
//        PushService.setDefaultPushCallback(this, MainActivity.class);


        // Save the current installation.
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
