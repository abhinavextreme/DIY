package com.diy.travelers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Abhinav on 7/31/2018.
 */

public class NetworkServices {

    private static NetworkServices networkServices;
    public Context context;
    public  String getResponse, postResponse;
    public RequestQueue requestQueue;
    ProgressDialog progressDialog;


    public NetworkServices(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkServices getInstance(Context context) {
        if (networkServices == null) {
            networkServices = new NetworkServices(context.getApplicationContext());
        }
        return networkServices;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }


    public String getRequest(final String actionID) {
        if (requestQueue != null) {
            NetworkCall networkCall = new NetworkCall(context);
            if (networkCall.isNetworkAvailable()) {
                requestQueue = NetworkServices.getInstance(context).getRequestQueue();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, actionID, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getResponse = response;
                        Log.v("RESPONSE", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                        Log.v("ERROR", "ERROR");
                    }
                });
                getRequestQueue().add(stringRequest);
            } else {
                Toast.makeText(context, "Please connect to network", Toast.LENGTH_SHORT).show();
                return getResponse;
            }
        }
        return getResponse;
    }

    public String postRequest(final JSONObject jsonObject, final String actionID) {
        if (requestQueue != null) {
            NetworkCall networkCall = new NetworkCall(context);
            if (networkCall.isNetworkAvailable()) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait..");
                progressDialog.setTitle("Processing");
//                progressDialog.show();
                requestQueue = NetworkServices.getInstance(context).getRequestQueue();
                Log.v("JSON_SEND", jsonObject.toString());
                Log.v("ACTION_ID", actionID);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, actionID, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse( JSONObject jsonResponse) {
                        postResponse = jsonResponse.toString();
                        Log.v("Response from server", jsonResponse.toString());
                        Toast.makeText(context, "Success!!", Toast.LENGTH_SHORT).show();
                        /*try {
                            if (jsonResponse.length()!=0) {
                                responseCallBack.SuccessCallBack(jsonResponse);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error", "Error: " + error.getMessage());
                        Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            } else {
                Toast.makeText(context, "Please connect to network", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        return postResponse;
    }

}
