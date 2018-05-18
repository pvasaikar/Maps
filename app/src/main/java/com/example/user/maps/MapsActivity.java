package com.example.user.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;

public class MapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMyLocationClickListener
        {

    private GoogleMap mMap;
    boolean mLocationPermissionGranted;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123;
    GoogleSignInClient mGoogleSignInClient;

    //private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_banana:
                    //mTextMessage.setText(R.string.title_home);
                    LatLng navBan = new LatLng(18.9921, 72.8466);
                    mMap.addMarker(new MarkerOptions().position(navBan).title("Prasad Vasaikar").snippet("Address:TEST Address"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(navBan));
                    return true;
                case R.id.navigation_grapes:
                    //mTextMessage.setText(R.string.title_dashboard);
                    LatLng navGrap = new LatLng(19.2462, 72.8509);
                    mMap.addMarker(new MarkerOptions().position(navGrap).title("Hardik Shah").snippet("Address:TEST Address"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(navGrap));
                    return true;
                case R.id.navigation_pumpkin:
                    //mTextMessage.setText(R.string.title_notifications);
                    LatLng navPum = new LatLng(19.0790, 72.9080);
                    mMap.addMarker(new MarkerOptions().position(navPum).title("Shailesh Shetty").snippet("Address:TEST Address"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(navPum));
                    return true;
                case R.id.navigation_carrot:
                    //mTextMessage.setText(R.string.title_notifications);
                    LatLng navCarr = new LatLng(22.2587, 71.1924);
                    mMap.addMarker(new MarkerOptions().position(navCarr).title("TEST 1").snippet("Address:TEST Address"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(navCarr));
                    return true;
                case R.id.navigation_rassberry:
                    //mTextMessage.setText(R.string.title_notifications);
                    LatLng navRas = new LatLng(15.2993, 74.1240);
                    mMap.addMarker(new MarkerOptions().position(navRas).title("TEST 2").snippet("Address:TEST Address"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(navRas));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        removeShiftMode(navigation);

        //Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Location Permission
        getLocationPermission();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateLocationUI();
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void getLocationPermission()
    {

    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //updateLocationUI();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getLocationPermission();
                    updateLocationUI();
                }
            }
        }
    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMyLocationClickListener(this);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            //Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_messages) {

        } else if (id == R.id.nav_news) {

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_conMap) {

        }
        else if (id == R.id.nav_supportLine) {

        }
        else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MapsActivity.this,
                    ProfileActivity.class);
            startActivity(intent); // startActivity allow you to move

        }
        else if (id == R.id.nav_logout) {

            //Firebase SignOut
            FirebaseAuth.getInstance().signOut();

            //Google SignOut
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

            if (mGoogleSignInClient!=null)
            {
                mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(MapsActivity.this,
                                SignInActivity.class);
                        startActivity(intent); // startActivity allow you to move
                        finish();
                    }
                });
            }
            //mGoogleSignInClient= (GoogleSignInClient) getIntent().getExtras().get("account");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

            @SuppressLint("RestrictedApi")
            public static void removeShiftMode(BottomNavigationView view) {
                BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
                try {
                    Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                    shiftingMode.setAccessible(true);
                    shiftingMode.setBoolean(menuView, false);
                    shiftingMode.setAccessible(false);
                    for (int i = 0; i < menuView.getChildCount(); i++) {
                        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                        //noinspection RestrictedApi
                        item.setShiftingMode(false);
                        // set once again checked value, so view will be updated
                        //noinspection RestrictedApi
                        item.setChecked(item.getItemData().isChecked());
                    }
                } catch (NoSuchFieldException e) {
                    //Log.e("BottomNav", "Unable to get shift mode field", e);
                } catch (IllegalAccessException e) {
                    //Log.e("BottomNav", "Unable to change value of shift mode", e);
                }
            }


}
