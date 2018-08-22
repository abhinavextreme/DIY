package com.diy.travelers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;

/**
 * Created by mell on 3/30/2017.
 */

public class CustomSpinner {              /* extends Activity*/

    private Context context;
    public View rootView;
    public Object selectedObj,selectedPositionObj,selectedPosition;

    public CustomSpinner(Context context) {
        this.context = context;
    }

    public CustomSpinner(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    public Spinner getSpinner(int spinnerID, ArrayList<?> userDeviceList, final Activity CurrentActivity) {
        //Class<?> clazz = Class.forName("vems.visioneering.com.vems.models.EORouteMobBean");
        // Log.v("Adapter Value:",userDeviceList.toString());
        final ArrayAdapter<?> dataAdapter = new ArrayAdapter<>(CurrentActivity, android.R.layout.simple_spinner_item, userDeviceList);
        Log.v("Adapter Value:", dataAdapter.toString());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner mySpinner = (Spinner) CurrentActivity.findViewById(spinnerID);
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedObj = parent.getItemAtPosition(position);
                Log.v("Selected Object: ",selectedObj.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return mySpinner;
    }

    public Spinner getSpinnerFromFragment(int spinnerID, ArrayList<?> userDeviceList, final String className) {
        final ArrayAdapter<?> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, userDeviceList);
        Log.v("Adapter Value:", dataAdapter.toString());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner mySpinner = (Spinner) rootView.findViewById(spinnerID);
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedObj = parent.getItemAtPosition(position);
                Log.v("Selected Object: ",selectedObj.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        return mySpinner;
    }

    public Spinner getSpinnerData(int spinnerID, ArrayList<?> arrayList) {
        final ArrayAdapter<?> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
        Log.v("Adapter Value:", dataAdapter.toString());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner mySpinner = (Spinner) rootView.findViewById(spinnerID);
        mySpinner.setAdapter(dataAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedObj = parent.getItemAtPosition(position);
                Log.v("Selected Object: ",selectedObj.toString());
                selectedPosition=mySpinner.getSelectedItemPosition()+2;
                Log.v("selectedPositionObj: ", selectedPosition.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        return mySpinner;
    }
}
