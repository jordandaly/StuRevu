package ie.ncirl.jordandaly.classdoor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jdaly on 29/11/2015.
 */
public class Login extends AppCompatActivity {

    public static final List<String> Permissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};
    ImageView mProfileImage;
    Button mBtnFb;
    TextView mUsername, mEmailID;
    Profile mFbProfile;
    ParseUser parseUser;
    String name = null, email = null;
    private Button signInButton;
    private EditText userName;
    private EditText password;

    public static Bitmap DownloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("IMAGE", "Error getting bitmap", e);
        }
        return bm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnFb = (Button) findViewById(R.id.btn_fb_login);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);

        mUsername = (TextView) findViewById(R.id.txt_name);
        mEmailID = (TextView) findViewById(R.id.txt_email);

        mFbProfile = Profile.getCurrentProfile();

        signInButton = (Button) findViewById(R.id.signInId);
        userName = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = userName.getText().toString();
                String pWord = password.getText().toString();

                if (!uName.equals("") || !pWord.equals("")) {
                    ParseUser.logInInBackground(uName, pWord, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {

                                Toast.makeText(getApplicationContext(), "Login Successful!"
                                        , Toast.LENGTH_LONG).show();

                                startActivity(new Intent(Login.this, CollegeListActivity.class));
//                                startActivity(new Intent(Login.this, MapActivity.class));

                            } else {

                                Toast.makeText(getApplicationContext(), "Not logged in",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter username and Password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mBtnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseFacebookUtils.logInWithReadPermissionsInBackground(Login.this, Permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {

                        if (user == null) {
                            Log.d("StuRevu", "Uh oh. The user cancelled the Facebook login.");
                            Toast.makeText(getApplicationContext(), err.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        } else if (user.isNew()) {
                            Log.d("StuRevu", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                            startActivity(new Intent(Login.this, CollegeListActivity.class));
                        } else {
                            Log.d("StuRevu", "User logged in through Facebook!");
                            getUserDetailsFromParse();
                            startActivity(new Intent(Login.this, CollegeListActivity.class));
                        }
                    }
                });

            }
        });

    }

    private void getUserDetailsFromParse() {
        parseUser = ParseUser.getCurrentUser();
//Fetch profile photo
        try {
            ParseFile parseFile = parseUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            mProfileImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEmailID.setText(parseUser.getEmail());
        mUsername.setText(parseUser.getUsername());
        Toast.makeText(Login.this, "Welcome back " + mUsername.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void getUserDetailsFromFB() {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,picture");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
             /* handle the result */
                        try {
                            email = response.getJSONObject().getString("email");
                            mEmailID.setText(email);
                            name = response.getJSONObject().getString("name");
                            mUsername.setText(name);
                            JSONObject picture = response.getJSONObject().getJSONObject("picture");
                            JSONObject data = picture.getJSONObject("data");
                            //  Returns a 50x50 profile picture
                            String pictureUrl = data.getString("url");
                            new ProfilePhotoAsync(pictureUrl).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    private void saveNewUser() {
        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(name);
        parseUser.setEmail(email);
        //        Saving profile photo as a ParseFile
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) mProfileImage.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] data = stream.toByteArray();
        String thumbName = parseUser.getUsername().replaceAll("\\s+", "");
        final ParseFile parseFile = new ParseFile(thumbName + "_thumb.jpg", data);
        parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                parseUser.put("profileThumb", parseFile);
                //Finally save all the user details
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(Login.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    class ProfilePhotoAsync extends AsyncTask<String, String, String> {
        public Bitmap bitmap;
        String url;

        public ProfilePhotoAsync(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {
            // Fetching data from URI and storing in bitmap
            bitmap = DownloadImageBitmap(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProfileImage.setImageBitmap(bitmap);
            saveNewUser();
        }
        }
}

