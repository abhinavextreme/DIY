package com.diy.travelers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.diy.travelers.Models.LoginResponseBean;
import com.diy.travelers.Utils.DataUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btn, googleLoginBtn;
    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    String userName, profileID, profilePicURL;
    public int REQUEST_CODE = 1;
    private AccessToken mAccessToken;
    private EditText email_id, password;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private TextView signUp;
    RadioButton rb1, rb2;
    ResponseCallBack responseCallBack;
    DataUtils dataUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        signUp = (TextView) findViewById(R.id.signUp_btn);
        login_btn = (Button) findViewById(R.id.login_btn);
        googleLoginBtn = (Button) findViewById(R.id.google_login_btn);
        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        email_id = (EditText) findViewById(R.id.email_id);
        password = (EditText) findViewById(R.id.password_id);
        signUp.setOnClickListener(this);
        googleLoginBtn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        fbLoginButton.setOnClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.selectChoice);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb1 = (RadioButton) group.findViewById(R.id.vendorRadio);
                rb2 = (RadioButton) group.findViewById(R.id.travellerRadio);
                radioGroup.check(rb1.getId());
                rb1.setChecked(true);
                if (rb1.isChecked()) {
                    rb1.setChecked(true);
                    rb2.setChecked(false);
                }

                if (rb2.isChecked()) {
                    rb1.setChecked(false);
                    rb2.setChecked(true);
                }


                /*if (null != rb1 && checkedId > -1) {
                    Toast.makeText(LoginActivity.this, rb1.getText() + "", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        dataUtils = new DataUtils(this);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.signUp_btn) {

            getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new SignupFragment()).addToBackStack(null).commit();
        }

        if (id == R.id.login_btn) {

            if (checkEmptyFields()) {
                JSONObject loginObj = new JSONObject();
                try {
                    loginObj.put("usrName", email_id.getText().toString().trim());
                    loginObj.put("password", password.getText().toString().trim());
                    loginObj.put("lkRolePk", rb1.isChecked() ? "2" : "3");

                    Log.v("JSON_SEND", loginObj.toString());
                    RequestQueue requestQueue = NetworkServices.getInstance(this).getRequestQueue();
                    dataUtils.showProgrssDialog();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApplicationUtils.LOGIN_API, loginObj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonResponse) {
                            //new DataUtils(getApplicationContext()).showProgrssDialog();
                            String response = String.valueOf(jsonResponse);
                            Toast.makeText(LoginActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                            Log.v("Response-->", response);
                            //new DataUtils().stopProgrssDialog();
                            if (response != null) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                LoginResponseBean loginResponseBean = gson.fromJson(response, LoginResponseBean.class);

                                SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOGIN_SHARED_PREFRENCE, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.USER_ID, loginResponseBean.getUserId());
                                editor.putString(Constants.USER_KEY, String.valueOf(loginResponseBean.getUserKey()));
                                editor.putString(Constants.DEVICE_TOKEN, String.valueOf(loginResponseBean.getDeviceToken()));
                                editor.putString(Constants.DB_ROLE, loginResponseBean.getDbRole());
                                editor.putString(Constants.FIRST_NAME, loginResponseBean.getFirstName());
                                editor.putString(Constants.IS_USER_VERIFIED, String.valueOf(loginResponseBean.getIsUserVerified()));
                                editor.putBoolean(Constants.IS_LOGGED_IN, true);
                                editor.commit();

                                Intent in = new Intent(getApplicationContext(), Dashboard.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(in);

                                dataUtils.stopProgrssDialog();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error", error.toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Error!!");
                            builder.setMessage("Your Mobile no./email/role is not registered with us");
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //if user select "No", just cancel this dialog and continue with app
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            dataUtils.stopProgrssDialog();
                            return;
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
                    };
                    requestQueue.add(jsonObjectRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

        if (id == R.id.google_login_btn) {
            //startActivity(new Intent(this, PayUMoneyActivity.class));
            NetworkServices networkServices1 = new NetworkServices(this);
            networkServices1.getRequest(ApplicationUtils.TEST_API_2);
        }
    }

    public boolean checkEmptyFields() {
        if (TextUtils.isEmpty(email_id.getText().toString())) {
            email_id.setError("Enter Email");
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Enter Password");
            return false;
        } else if (radioGroup.isSelected()) {
            return true;
        } else {
            return true;
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
                            in.putExtra("isfacebookLogin", true);
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
