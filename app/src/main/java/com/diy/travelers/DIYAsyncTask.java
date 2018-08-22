package com.diy.travelers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Abhinav on 8/10/2018.
 */

public class DIYAsyncTask extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    Context context;

    public DIYAsyncTask(Context context) {
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.setTitle("Processing");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
       NetworkServices networkServices= new NetworkServices(context);
       networkServices.getRequestQueue().start();
       return null;
    }
}
