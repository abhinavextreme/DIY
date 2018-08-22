package com.diy.travelers;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class SignupFragment extends Fragment implements View.OnClickListener {

    private Object selectedObj, selectedPosition;

    public SignupFragment() {

    }

    public Context context;
    public View rootView;
    //public FloatingActionButton fbBtn;
    Button profile_image_btn;
    private ImageView profileImage;
    private String selectedImagePath;
    private static final int SELECT_PICTURE = 1;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button submitDetails;
    Spinner selectionDropDownSpinner;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public CustomSpinner customSpinner;
    EditText company_name_et, firstName, lastName, mobileNo, landLineNo, primaryEmail, secondaryEmail, pincode, cityName, dob, address1;
    android.support.design.widget.TextInputLayout company_name_ly;

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        context = this.getActivity();

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setTitle("Signup details");
        //int black= Color.BLACK;
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        profileImage = (ImageView) rootView.findViewById(R.id.profile_image);
        profile_image_btn = (Button) rootView.findViewById(R.id.profile_image_btn);
        //collapsingToolbarLayout = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsingToolbar);
        submitDetails = (Button) rootView.findViewById(R.id.submitDetails);
        selectionDropDownSpinner = (Spinner) rootView.findViewById(R.id.selection_dropdown_spinner);
        company_name_et = (EditText) rootView.findViewById(R.id.company_name_et);
        firstName = (EditText) rootView.findViewById(R.id.first_name);
        lastName = (EditText) rootView.findViewById(R.id.last_name_et);
        mobileNo = (EditText) rootView.findViewById(R.id.phone_no_et);
        landLineNo = (EditText) rootView.findViewById(R.id.landline_no_et);
        primaryEmail = (EditText) rootView.findViewById(R.id.primary_email_id_et);
        secondaryEmail = (EditText) rootView.findViewById(R.id.secondary_email_id_et);
        pincode = (EditText) rootView.findViewById(R.id.pincode_et);
        cityName = (EditText) rootView.findViewById(R.id.city_et);
        dob = (EditText) rootView.findViewById(R.id.dob_et);
        address1 = (EditText) rootView.findViewById(R.id.address_et_1);

        company_name_ly = (android.support.design.widget.TextInputLayout) rootView.findViewById(R.id.company_name_ly);
        //company_name_ly.setVisibility(View.GONE);

        profile_image_btn.setOnClickListener(this);
        submitDetails.setOnClickListener(this);

        ArrayList<String> selectionList = new ArrayList<>();
        selectionList.add(Constants.VENDOR);
        selectionList.add(Constants.TRAVELLER);

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, selectionList);
        Log.v("Adapter Value:", dataAdapter.toString());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner mySpinner = (Spinner) rootView.findViewById(R.id.selection_dropdown_spinner);
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedObj = parent.getItemAtPosition(position);
                Log.v("Selected Object: ", selectedObj.toString());

                selectedPosition = mySpinner.getSelectedItemPosition() + 2;
                Log.v("selectedPositionObj: ", selectedPosition.toString());

                if (selectedPosition.toString().equals("3")) {
                    company_name_ly.setVisibility(View.GONE);
                } else {
                    company_name_ly.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.profile_image_btn) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);
        }

        if (id == R.id.submitDetails) {
            checkEmptyFields();
            if (checkEmptyFields()) {
                signUp(context);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                if (EasyPermissions.hasPermissions(context, galleryPermissions)) {
                    profileImage.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                } else {
                    EasyPermissions.requestPermissions(this, "Access for storage",
                            101, galleryPermissions);
                }

            }
        }

    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            Log.v("path-->", path);

            return path;
        } else {
            // this is our fallback here
            Log.v("uri Path-->", uri.getPath());

            return uri.getPath();
        }
    }

    public void signUp(final Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lkRole", selectedPosition.toString());
            jsonObject.put("firstName", firstName.getText().toString().trim());
            jsonObject.put("lastName", lastName.getText().toString().trim());
            jsonObject.put("dob", dob.getText().toString().trim());
            jsonObject.put("companyName", selectedPosition.toString().equals("3") ? "" : company_name_et.getText().toString().trim());
            jsonObject.put("phone", mobileNo.getText().toString().trim());
            jsonObject.put("primaryEmail", primaryEmail.getText().toString().trim());
            jsonObject.put("secondaryEmail", secondaryEmail.getText().toString().trim());
            jsonObject.put("className", "EOUser");
            Log.v("JSON_SEND", jsonObject.toString());
            RequestQueue requestQueue = NetworkServices.getInstance(context).getRequestQueue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApplicationUtils.CREATE_OBJ, new JSONObject(String.valueOf(jsonObject)), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    String response = String.valueOf(jsonResponse);
                    Toast.makeText(context, "Success!!", Toast.LENGTH_SHORT).show();
                    Log.v("Response-->", response);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setTitle("Success!!");
                    builder.setMessage("Your email/role is registered with us");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user select "No", just cancel this dialog and continue with app
                            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY));
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("Error", error.toString());
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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

    public boolean checkEmptyFields() {
        if (TextUtils.isEmpty(firstName.getText().toString())) {
            firstName.setError("Enter First Name");
            return false;
        } else if (TextUtils.isEmpty(lastName.getText().toString())) {
            lastName.setError("Enter Last Name");
            return false;
        } else if (TextUtils.isEmpty(primaryEmail.getText().toString())) {
            primaryEmail.setError("Enter primary email");
            return false;
        } else if (TextUtils.isEmpty(mobileNo.getText().toString())) {
            mobileNo.setError("Enter phone no.");
            return false;
        } else if (TextUtils.isEmpty(dob.getText().toString())) {
            dob.setError("Enter DOB");
            return false;
        } else if (TextUtils.isEmpty(company_name_et.getText().toString()) && selectedPosition.toString().equals("2")) {
            company_name_et.setError("Enter Company Name");
            return false;
        } else {
            return true;
        }
    }

}
