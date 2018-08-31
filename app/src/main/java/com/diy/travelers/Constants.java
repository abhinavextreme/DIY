package com.diy.travelers;

import android.content.SharedPreferences;

/**
 * Created by Abhinav on 8/13/2018.
 */

public class Constants {
    public static final String eoAdmin = "1";
    public static final String eoVendor = "2";
    public static final String eoTraveler = "3";

    //SHARED PREFRENCE

    public static final String USER_ID = "userId";
    public static final String DB_ROLE = "dbRole";
    public static final String USER_KEY = "userKey";
    public static final String IS_USER_VERIFIED = "isUserVerified";
    public static final String DEVICE_TOKEN = "deviceToken";
    public static final String FIRST_NAME = "firstName";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    //DB Roles
    public static final String VENDOR = "Vendor";
    public static final String TRAVELLER = "Traveller";

    public static SharedPreferences sharedPreferences;
    public static String LOGIN_SHARED_PREFRENCE = "LoginSharedPref";
}
