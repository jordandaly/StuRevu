package com.sturevu.classdoor;

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


    // Debugging switch
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "StuRevu";


    private static SharedPreferences preferences;



    public ParseApplication() {
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

        preferences = getSharedPreferences("com.sturevu.classdoor", Context.MODE_PRIVATE);





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
