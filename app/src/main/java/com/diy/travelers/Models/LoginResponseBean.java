
package com.diy.travelers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseBean {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("dbRole")
    @Expose
    private String dbRole;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("userKey")
    @Expose
    private Integer userKey;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("deviceToken")
    @Expose
    private Object deviceToken;
    @SerializedName("isUserVerified")
    @Expose
    private Boolean isUserVerified;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDbRole() {
        return dbRole;
    }

    public void setDbRole(String dbRole) {
        this.dbRole = dbRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserKey() {
        return userKey;
    }

    public void setUserKey(Integer userKey) {
        this.userKey = userKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Boolean getIsUserVerified() {
        return isUserVerified;
    }

    public void setIsUserVerified(Boolean isUserVerified) {
        this.isUserVerified = isUserVerified;
    }

}
