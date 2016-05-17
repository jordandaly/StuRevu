package ie.ncirl.jordandaly.classdoor;

/**
 * Created by jdaly on 16/04/2016.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@TargetApi(23)
public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123;
    /*
     * Define a request code to send to Google Play services This code is returned in
     * Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    /*
     * Constants for location update parameters
     */
    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // The update interval
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // A fast interval ceiling
    private static final int FAST_CEILING_IN_SECONDS = 1;
    // Update interval in milliseconds
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * UPDATE_INTERVAL_IN_SECONDS;
    // A fast ceiling of update intervals, used when the app is visible
    private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
            * FAST_CEILING_IN_SECONDS;
    /*
     * Constants for handling location results
     */
    // Conversion from feet to meters
    private static final float METERS_PER_FEET = 0.3048f;
    // Conversion from kilometers to meters
    private static final int METERS_PER_KILOMETER = 1000;
    // Initial offset for calculating the map bounds
    private static final double OFFSET_CALCULATION_INIT_DIFF = 1.0;

    // Maximum results returned from a Parse query
//    private static final int MAX_POST_SEARCH_RESULTS = 20;
    // Accuracy for calculating the map bounds
    private static final float OFFSET_CALCULATION_ACCURACY = 0.01f;
    // Maximum post search radius for map in kilometers
    private static final int MAX_SEARCH_DISTANCE = 100;
    // Fields for helping process map and location changes
    private final Map<String, Marker> mapMarkers = new HashMap<String, Marker>();
    GoogleMap Map;
    /*
     * Other class member variables
     */
    // Map fragment
    private SupportMapFragment mapFragment;
    // Represents the circle around a map
    private Circle mapCircle;
    // Fields for the map radius in feet
    private float radius;
    private float lastRadius;
    private int mostRecentMapUpdate;
    private boolean hasSetUpInitialLocation;
    private String selectedCollegeObjectId;
    private Location lastLocation;
    private Location currentLocation;

    // A request to connect to Location Services
    private LocationRequest locationRequest;

    // Stores the current instantiation of the location client in this object
    private GoogleApiClient locationClient;

    // Adapter for the Parse query
    private ParseQueryAdapter<College> collegesQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radius = ParseApplication.getSearchDistance();
        lastRadius = radius;
        setContentView(R.layout.map_activity);

        // Create a new global location parameters object
        locationRequest = LocationRequest.create();

        // Set the update interval
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Create a new location client, using the enclosing class to handle callbacks.
        locationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Set up a customized query
        ParseQueryAdapter.QueryFactory<College> factory =
                new ParseQueryAdapter.QueryFactory<College>() {
                    public ParseQuery<College> create() {
                        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
                        ParseQuery<College> query = College.getQuery();
                        query.orderByAscending("Name");
//                        query.whereWithinKilometers("Geolocation", geoPointFromLocation(myLoc), radius
//                                * METERS_PER_FEET / METERS_PER_KILOMETER);
//                        query.setLimit(MAX_POST_SEARCH_RESULTS);
                        return query;
                    }
                };

        // Set up the query adapter
        collegesQueryAdapter = new ParseQueryAdapter<College>(this, factory) {
            @Override
            public View getItemView(College college, View view, ViewGroup parent) {
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.map_college_item, null);
                }
                TextView nameView = (TextView) view.findViewById(R.id.name_view);
                TextView initialsView = (TextView) view.findViewById(R.id.initials_view);
                nameView.setText(college.getName());
                initialsView.setText(college.getInitials());
                return view;
            }
        };

        // Disable automatic loading when the adapter is attached to a view.
        collegesQueryAdapter.setAutoload(false);

        // Disable pagination, we'll manage the query limit ourselves
        collegesQueryAdapter.setPaginationEnabled(false);

        // Attach the query adapter to the view
        ListView collegesListView = (ListView) findViewById(R.id.list);
        collegesListView.setAdapter(collegesQueryAdapter);

        // Set up the handler for an item's selection
        collegesListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final College item = collegesQueryAdapter.getItem(position);
                selectedCollegeObjectId = item.getObjectId();
                mapFragment.getMap().animateCamera(
                        CameraUpdateFactory.newLatLng(new LatLng(item.getLocation().getLatitude(), item
                                .getLocation().getLongitude())), new CancelableCallback() {
                            public void onFinish() {
                                Marker marker = mapMarkers.get(item.getObjectId());
                                if (marker != null) {
                                    marker.showInfoWindow();
                                }
                            }

                            public void onCancel() {
                            }
                        });
                Marker marker = mapMarkers.get(item.getObjectId());
                if (marker != null) {
                    marker.showInfoWindow();
                }
            }
        });

        // Set up the map fragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);


//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            mapFragment.getMap().setMyLocationEnabled(true);
//        }
//        // Set up the camera change handler
//        mapFragment.getMap().setOnCameraChangeListener(new OnCameraChangeListener() {
//            public void onCameraChange(CameraPosition position) {
//                // When the camera changes, update the query
//                doMapQuery();
//            }
//        });

//        // Set up the handler for the button click
//        Button postButton = (Button) findViewById(R.id.college_button);
//        postButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                // Only allow if we have a location
//                Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
//                if (myLoc == null) {
//                    Toast.makeText(MapActivity.this,
//                            "Please try again after your location appears on the map.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                Intent intent = new Intent(MapActivity.this, CollegeSingleItem.class);
//                intent.putExtra(ParseApplication.INTENT_EXTRA_LOCATION, myLoc);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onMapReady(GoogleMap map) {

        final String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("To receive relevant location based map info you have to allow us access to your location.");
                builder.setTitle("Location Services");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MapActivity.this, permissions, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                });

                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }


//        int hasLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//            return;
//        }

//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//
//                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//            return;
//        }


        // Enable the current location "blue dot"
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        map.setMyLocationEnabled(true);
//        }

//         Set up the camera change handler
        map.setOnCameraChangeListener(new OnCameraChangeListener() {
            public void onCameraChange(CameraPosition position) {
                // When the camera changes, update the query
                doMapQuery();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    startActivity(getIntent());
                    finish();


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // Permission Denied
                    Toast.makeText(MapActivity.this, "Location permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /*
     * Called when the Activity is no longer visible at all. Stop updates and disconnect.
     */
    @Override
    public void onStop() {
        // If the client is connected
        if (locationClient.isConnected()) {
            stopPeriodicUpdates();
        }

        // After disconnect() is called, the client is considered "dead".
        locationClient.disconnect();

        super.onStop();
    }

    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();

        // Connect to the location services client
        locationClient.connect();
    }

    /*
     * Called when the Activity is resumed. Updates the view.
     */
    @Override
    protected void onResume() {
        super.onResume();

        ParseApplication.getConfigHelper().fetchConfigIfNeeded();

        // Get the latest search distance preference
        radius = ParseApplication.getSearchDistance();
        // Checks the last saved location to show cached data if it's available
        if (lastLocation != null) {
            LatLng myLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            // If the search distance preference has been changed, move
            // map to new bounds.
            if (lastRadius != radius) {
                updateZoom(myLatLng);
            }
            // Update the circle map
            updateCircle(myLatLng);
        }
        // Save the current radius
        lastRadius = radius;
        // Query for the latest data to update the views.
        doMapQuery();
        doListQuery();
    }

    /*
     * Handle results returned to this Activity by other Activities started with
     * startActivityForResult(). In particular, the method onConnectionFailed() in
     * LocationUpdateRemover and LocationUpdateRequester may call startResolutionForResult() to start
     * an Activity that handles Google Play services problems. The result of this call returns here,
     * to onActivityResult.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        if (ParseApplication.APPDEBUG) {
                            // Log the result
                            Log.d(ParseApplication.APPTAG, "Connected to Google Play services");
                        }

                        break;

                    // If any other result was returned by Google Play services
                    default:
                        if (ParseApplication.APPDEBUG) {
                            // Log the result
                            Log.d(ParseApplication.APPTAG, "Could not connect to Google Play services");
                        }
                        break;
                }

                // If any other request code was received
            default:
                if (ParseApplication.APPDEBUG) {
                    // Report that this Activity received an unknown requestCode
                    Log.d(ParseApplication.APPTAG, "Unknown request code received for the activity");
                }
                break;
        }
    }

    /*
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            if (ParseApplication.APPDEBUG) {
                // In debug mode, log the status
                Log.d(ParseApplication.APPTAG, "Google play services available");
            }
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), ParseApplication.APPTAG);
            }
            return false;
        }
    }

    /*
     * Called by Location Services when the request to connect the client finishes successfully. At
     * this point, you can request the current location or start periodic updates
     */
    public void onConnected(Bundle bundle) {
        if (ParseApplication.APPDEBUG) {
            Log.d(ParseApplication.APPTAG, "Connected to location services");
        }
        currentLocation = getLocation();
        startPeriodicUpdates();
    }

    /*
     * Called by Location Services if the connection to the location client drops because of an error.
     */
    public void onDisconnected() {
        if (ParseApplication.APPDEBUG) {
            Log.d(ParseApplication.APPTAG, "Disconnected from location services");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(ParseApplication.APPTAG, "GoogleApiClient connection has been suspend");
    }

    /*
     * Called by Location Services if the attempt to Location Services fails.
     */
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Google Play services can resolve some errors it detects. If the error has a resolution, try
        // sending an Intent to start a Google Play services activity that can resolve error.
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {

                if (ParseApplication.APPDEBUG) {
                    // Thrown if Google Play services canceled the original PendingIntent
                    Log.d(ParseApplication.APPTAG, "An error occurred when connecting to location services.", e);
                }
            }
        } else {
            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    /*
     * Report location updates to the UI.
     */
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if (lastLocation != null
                && geoPointFromLocation(location)
                .distanceInKilometersTo(geoPointFromLocation(lastLocation)) < 0.01) {
            // If the location hasn't changed by more than 10 meters, ignore it.
            return;
        }
        lastLocation = location;
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (!hasSetUpInitialLocation) {
            // Zoom to the current location.
            updateZoom(myLatLng);
            hasSetUpInitialLocation = true;
        }
        // Update map radius indicator
        updateCircle(myLatLng);
        doMapQuery();
        doListQuery();
    }

    /*
     * In response to a request to start updates, send a request to Location Services
     */
    private void startPeriodicUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(
                locationClient, locationRequest, this);

    }

    /*
     * In response to a request to stop updates, send a request to Location Services
     */
    private void stopPeriodicUpdates() {
        locationClient.disconnect();
    }

    /*
     * Get the current location
     */
    private Location getLocation() {
        // If Google Play Services is available
        if (servicesConnected() && (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // Get the current location

            return LocationServices.FusedLocationApi.getLastLocation(locationClient);

        } else {
            return null;
        }
    }


    /*
     * Set up a query to update the list view
     */
    private void doListQuery() {
        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
        // If location info is available, load the data
        if (myLoc != null) {
            // Refreshes the list view with new data based
            // usually on updated location data.
            collegesQueryAdapter.loadObjects();
        }
    }

    /*
     * Set up the query to update the map view
     */


    private void doMapQuery() {
        final int myUpdateNumber = ++mostRecentMapUpdate;
        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
        // If location info isn't available, clean up any existing markers
        if (myLoc == null) {
            cleanUpMarkers(new HashSet<String>());
            return;
        }
        final ParseGeoPoint myPoint = geoPointFromLocation(myLoc);
        // Create the map Parse query
        ParseQuery<College> mapQuery = College.getQuery();
        // Set up additional query filters
        mapQuery.whereWithinKilometers("Geolocation", myPoint, MAX_SEARCH_DISTANCE);
        mapQuery.orderByAscending("Name");
//        mapQuery.setLimit(MAX_SEARCH_DISTANCE);
        // Kick off the query in the background
        mapQuery.findInBackground(new FindCallback<College>() {
            @Override
            public void done(List<College> objects, ParseException e) {
                if (e != null) {
                    if (ParseApplication.APPDEBUG) {
                        Log.d(ParseApplication.APPTAG, "An error occurred while querying for map colleges.", e);
                    }
                    return;
                }
        /*
         * Make sure we're processing results from
         * the most recent update, in case there
         * may be more than one in progress.
         */
                if (myUpdateNumber != mostRecentMapUpdate) {
                    return;
                }
                // Colleges to show on the map
                Set<String> toKeep = new HashSet<String>();
                // Loop through the results of the search
                for (College college : objects) {
                    // Add this college to the list of map pins to keep
                    toKeep.add(college.getObjectId());
                    // Check for an existing marker for this college
                    Marker oldMarker = mapMarkers.get(college.getObjectId());
                    // Set up the map marker's location
                    MarkerOptions markerOpts =
                            new MarkerOptions().position(new LatLng(college.getLocation().getLatitude(), college
                                    .getLocation().getLongitude()));
                    // Set up the marker properties based on if it is within the search radius
                    if (college.getLocation().distanceInKilometersTo(myPoint) > radius * METERS_PER_FEET
                            / METERS_PER_KILOMETER) {
                        // Check for an existing out of range marker
                        if (oldMarker != null) {
                            if (oldMarker.getSnippet() == null) {
                                // Out of range marker already exists, skip adding it
                                continue;
                            } else {
                                // Marker now out of range, needs to be refreshed
                                oldMarker.remove();
                            }
                        }
                        // Display a green marker with the college information
                        markerOpts =
                                markerOpts.title(college.getName()).snippet(college.getInitials())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else {
                        // Check for an existing in range marker
                        if (oldMarker != null) {
                            if (oldMarker.getSnippet() != null) {
                                // In range marker already exists, skip adding it
                                continue;
                            } else {
                                // Marker now in range, needs to be refreshed
                                oldMarker.remove();
                            }
                        }
                        // Display a green marker with the college information
                        markerOpts =
                                markerOpts.title(college.getName()).snippet(college.getInitials())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                    // Add a new marker
                    Marker marker = mapFragment.getMap().addMarker(markerOpts);
                    mapMarkers.put(college.getObjectId(), marker);
                    if (college.getObjectId().equals(selectedCollegeObjectId)) {
                        marker.showInfoWindow();
                        selectedCollegeObjectId = null;
                    }
                }
                // Clean up old markers.
                cleanUpMarkers(toKeep);
            }
        });
    }

    /*
     * Helper method to clean up old markers
     */
    private void cleanUpMarkers(Set<String> markersToKeep) {
        for (String objId : new HashSet<String>(mapMarkers.keySet())) {
            if (!markersToKeep.contains(objId)) {
                Marker marker = mapMarkers.get(objId);
                marker.remove();
                mapMarkers.get(objId).remove();
                mapMarkers.remove(objId);
            }
        }
    }

    /*
     * Helper method to get the Parse GEO point representation of a location
     */
    private ParseGeoPoint geoPointFromLocation(Location loc) {
        return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
    }

    /*
     * Displays a circle on the map representing the search radius
     */
    private void updateCircle(LatLng myLatLng) {
        if (mapCircle == null) {
            mapCircle =
                    mapFragment.getMap().addCircle(
                            new CircleOptions().center(myLatLng).radius(radius * METERS_PER_FEET));
            int baseColor = Color.DKGRAY;
            mapCircle.setStrokeColor(baseColor);
            mapCircle.setStrokeWidth(2);
            mapCircle.setFillColor(Color.argb(50, Color.red(baseColor), Color.green(baseColor),
                    Color.blue(baseColor)));
        }
        mapCircle.setCenter(myLatLng);
        mapCircle.setRadius(radius * METERS_PER_FEET); // Convert radius in feet to meters.
    }

    /*
     * Zooms the map to show the area of interest based on the search radius
     */
    private void updateZoom(LatLng myLatLng) {
        // Get the bounds to zoom to
        LatLngBounds bounds = calculateBoundsWithCenter(myLatLng);
        // Zoom to the given bounds
        mapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
    }

    /*
     * Helper method to calculate the offset for the bounds used in map zooming
     */
    private double calculateLatLngOffset(LatLng myLatLng, boolean bLatOffset) {
        // The return offset, initialized to the default difference
        double latLngOffset = OFFSET_CALCULATION_INIT_DIFF;
        // Set up the desired offset distance in meters
        float desiredOffsetInMeters = radius * METERS_PER_FEET;
        // Variables for the distance calculation
        float[] distance = new float[1];
        boolean foundMax = false;
        double foundMinDiff = 0;
        // Loop through and get the offset
        do {
            // Calculate the distance between the point of interest
            // and the current offset in the latitude or longitude direction
            if (bLatOffset) {
                Location.distanceBetween(myLatLng.latitude, myLatLng.longitude, myLatLng.latitude
                        + latLngOffset, myLatLng.longitude, distance);
            } else {
                Location.distanceBetween(myLatLng.latitude, myLatLng.longitude, myLatLng.latitude,
                        myLatLng.longitude + latLngOffset, distance);
            }
            // Compare the current difference with the desired one
            float distanceDiff = distance[0] - desiredOffsetInMeters;
            if (distanceDiff < 0) {
                // Need to catch up to the desired distance
                if (!foundMax) {
                    foundMinDiff = latLngOffset;
                    // Increase the calculated offset
                    latLngOffset *= 2;
                } else {
                    double tmp = latLngOffset;
                    // Increase the calculated offset, at a slower pace
                    latLngOffset += (latLngOffset - foundMinDiff) / 2;
                    foundMinDiff = tmp;
                }
            } else {
                // Overshot the desired distance
                // Decrease the calculated offset
                latLngOffset -= (latLngOffset - foundMinDiff) / 2;
                foundMax = true;
            }
        } while (Math.abs(distance[0] - desiredOffsetInMeters) > OFFSET_CALCULATION_ACCURACY);
        return latLngOffset;
    }

    /*
     * Helper method to calculate the bounds for map zooming
     */
    LatLngBounds calculateBoundsWithCenter(LatLng myLatLng) {
        // Create a bounds
        LatLngBounds.Builder builder = LatLngBounds.builder();

        // Calculate east/west points that should to be included
        // in the bounds
        double lngDifference = calculateLatLngOffset(myLatLng, false);
        LatLng east = new LatLng(myLatLng.latitude, myLatLng.longitude + lngDifference);
        builder.include(east);
        LatLng west = new LatLng(myLatLng.latitude, myLatLng.longitude - lngDifference);
        builder.include(west);

        // Calculate north/south points that should to be included
        // in the bounds
        double latDifference = calculateLatLngOffset(myLatLng, true);
        LatLng north = new LatLng(myLatLng.latitude + latDifference, myLatLng.longitude);
        builder.include(north);
        LatLng south = new LatLng(myLatLng.latitude - latDifference, myLatLng.longitude);
        builder.include(south);

        return builder.build();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                return true;
//            }
//        });
//        return true;
//    }

    /*
     * Show a dialog returned by Google Play services for the connection error code
     */
    private void showErrorDialog(int errorCode) {
        // Get the error dialog from Google Play services
        Dialog errorDialog =
                GooglePlayServicesUtil.getErrorDialog(errorCode, this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), ParseApplication.APPTAG);
        }
    }

    /*
     * Define a DialogFragment to display the error dialog generated in showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /*
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}