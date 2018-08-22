package com.diy.travelers;

import android.content.SharedPreferences;

/**
 * Created by Abhinav on 7/31/2018.
 */

public class ApplicationUtils {
    //public static final String base_url = "http://192.168.0.101:8081/DIY/rest/";   //home
    public static final String base_url = "http://192.168.43.124:8081/DIY/rest/";       //mobile

    public static final String TEST_API = "https://reqres.in/api/users";
    public static final String TEST_API_2 = "https://reqres.in/api/users/2";
    public static final String LOGIN_API = base_url + "auth/loginUser";
    public static final String CREATE_OBJ = base_url + "ajax/createObject";
    public static final String GET_OBJECT = base_url + "ajax/getObject";
    public static final String GET_TRAVELLERS = base_url + "ajax/getAllTravellers";
    public static final String GET_VENDORS = base_url + "ajax/getAllVendors";
}
