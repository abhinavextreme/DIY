package com.diy.travelers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Abhinav on 8/10/2018.
 */

public interface ResponseCallBack {
    void SuccessCallBack(JSONObject response) throws JSONException;
    void ErrorCallBack(String message);
}
