package com.diy.travelers.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Abhinav on 7/19/2018.
 */

public class DataUtils {

    ProgressDialog progressDialog;
    Context context;
    String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt";

    public DataUtils() {
    }
    public DataUtils(Context context){
        this.context=context;
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
        messageDigest = MessageDigest.getInstance(type);           //type-> SHA-512
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    //for calling hash in activity
    //String serverCalculatedHash= hashCal("SHA-512", hashSequence);

    public void showProgrssDialog(){
        progressDialog= new ProgressDialog(context);
        progressDialog.setTitle("Processing..");
        progressDialog.setMessage("Please wait while we retrieve your data");
        progressDialog.show();
    }

    public void stopProgrssDialog(){
        progressDialog.dismiss();
        progressDialog.cancel();
    }
}
