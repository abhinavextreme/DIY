package com.diy.travelers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.diy.travelers.Adapters.VendorsAdapter;
import com.diy.travelers.ApplicationUtils;
import com.diy.travelers.Models.UserBean;
import com.diy.travelers.R;
import com.diy.travelers.Utils.MyJsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsFragment extends Fragment {

    public Context context;
    public View rootView;
    RecyclerView vendorRecyclerView;
    Toolbar toolbar;
    UserBean userBean;
    ArrayList<UserBean> userBeanArrayList=null;

    public VendorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_vendors, container, false);
        vendorRecyclerView= rootView.findViewById(R.id.allVendorRV);

       /* toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setTitle("Vendors");
        //int black= Color.BLACK;
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);*/

        myJsonArrayRequest();

        return rootView;
    }

    public void myJsonArrayRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("className", "EOUser");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        MyJsonArrayRequest myJsonArrayRequest = new MyJsonArrayRequest(Request.Method.POST, ApplicationUtils.GET_VENDORS, jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.v("RESPONSE", response.toString());

                userBeanArrayList= new ArrayList<>();
                for(int i=0; i<response.length(); i++){
                    userBeanArrayList.add(userBean);
                }

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                UserBean userBean = gson.fromJson(response.toString(), UserBean.class);
                Log.v("userBean", userBean.toString());

                VendorsAdapter vendorsAdapter = new VendorsAdapter(context, userBeanArrayList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                vendorRecyclerView.setLayoutManager(mLayoutManager);
                vendorRecyclerView.setAdapter(vendorsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", error.toString());
            }
        });
        requestQueue.add(myJsonArrayRequest);
    }

}
