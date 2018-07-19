package com.diy.travelers;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUp, login_btn;
    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    String userName, profileID, profilePicURL;
    private int REQUEST_CODE = 1;
    private AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        signUp = (Button) findViewById(R.id.signUp_btn);
        login_btn = (Button) findViewById(R.id.login_btn);
        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        signUp.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        fbLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.signUp_btn) {
            getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new SignupFragment()).addToBackStack(null).commit();
        }

        if (id == R.id.login_btn) {
            Intent in = new Intent(this, Dashboard.class);
            in.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(in);
        }

        if (id == R.id.fb_login_button) {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "com.diy.travelers",
                        PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            facebookLogin();
        }
    }

    public void facebookLogin() {
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                Log.v("mAccessToken-->", mAccessToken.toString());
                getUserProfile(mAccessToken);
            }

            @Override
            public void onCancel() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }


    @SuppressLint("RestrictedApi")
    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //You can fetch user info like this…
                            Intent in = new Intent(LoginActivity.this, Dashboard.class);
                            in.putExtra("fb_login_response", response.toString());
                            in.putExtra("picture", object.getJSONObject("picture").toString());
                            in.putExtra("name", String.valueOf(object.getString("name")));
                            in.putExtra("email", String.valueOf(object.getString("email")));
                            startActivity(in);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
