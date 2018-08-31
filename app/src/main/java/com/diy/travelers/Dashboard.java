package com.diy.travelers;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.diy.travelers.Fragments.VendorsFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public CallbackManager callbackManager;
    public ProfileTracker profileTracker;
    public AccessTokenTracker accessTokenTracker;
    public AccessToken accessToken;
    public String profileID, profile_name, profilePicURL, picture, description, fb_token, fb_user_id, email, gender, userName;
    public String fb_login_response;
    ImageView userProfilePic;
    public TextView userNameText, userMailID;
    public Boolean isfacebookLogin = false;
    android.support.v4.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        userProfilePic = (ImageView) hView.findViewById(R.id.userProfilePic);
        userNameText = (TextView) hView.findViewById(R.id.userNameText);
        userMailID = (TextView) hView.findViewById(R.id.userMailID);
        fragmentManager= this.getSupportFragmentManager();

        normalLogin();
        /*if (isfacebookLogin) {
            loginThrougFacebook();
        } else {
            normalLogin();
        }*/

        sharedPref = getSharedPreferences(
                Constants.LOGIN_SHARED_PREFRENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();

        if (TextUtils.equals(sharedPref.getString(Constants.DB_ROLE, null), Constants.VENDOR)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.vendor_menu);
        } else if (TextUtils.equals(sharedPref.getString(Constants.DB_ROLE, null), Constants.TRAVELLER)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.traveller_menu);
        }
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //profileTracker.stopTracking();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id){
            case R.id.vendor_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new VendorsFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_logout:
                Intent in = new Intent(this, LoginActivity.class);
                in.setFlags( Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
                LoginManager.getInstance().logOut();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(Constants.IS_LOGGED_IN);
                editor.remove(Constants.DB_ROLE);
                editor.remove(Constants.DEVICE_TOKEN);
                editor.remove(Constants.USER_KEY);
                editor.remove(Constants.USER_ID);
                editor.apply();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean loginThrougFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null) {
            fb_login_response = intent.getStringExtra("fb_login_response");
            email = intent.getStringExtra("email");
            picture = intent.getStringExtra("picture");
            userName = intent.getStringExtra("name");
            isfacebookLogin = intent.getBooleanExtra("isfacebookLogin", true);

            try {
                JSONObject dataObj = new JSONObject(picture);
                String data = dataObj.getString("data");
                JSONObject profilePicObj = new JSONObject(data);
                profilePicURL = profilePicObj.getString("url");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            userNameText.setText(userName);
            userMailID.setText(email);
            Picasso.with(this).load(profilePicURL).into(userProfilePic);
        }
        return true;
    }

    public void normalLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOGIN_SHARED_PREFRENCE, MODE_PRIVATE);
        String name = sharedPreferences.getString(Constants.FIRST_NAME, Constants.FIRST_NAME);
        String userID = sharedPreferences.getString(Constants.USER_ID, Constants.USER_ID);
        userNameText.setText(name);
        userMailID.setText(userID);
        //Picasso.with(this).load(profilePicURL).into(userProfilePic);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
