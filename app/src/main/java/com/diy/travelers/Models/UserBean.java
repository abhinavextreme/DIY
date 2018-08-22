
package com.diy.travelers.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBean {

    @SerializedName("primaryKey")
    @Expose
    private Integer primaryKey;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private Object middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("primaryEmail")
    @Expose
    private String primaryEmail;
    @SerializedName("secondaryEmail")
    @Expose
    private String secondaryEmail;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("alternatePhone")
    @Expose
    private Object alternatePhone;
    @SerializedName("eoImage")
    @Expose
    private Object eoImage;
    @SerializedName("addressLine1")
    @Expose
    private Object addressLine1;
    @SerializedName("addressLine2")
    @Expose
    private Object addressLine2;
    @SerializedName("landMark")
    @Expose
    private Object landMark;
    @SerializedName("pinCode")
    @Expose
    private Object pinCode;
    @SerializedName("isUserVerified")
    @Expose
    private Boolean isUserVerified;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("deviceToken")
    @Expose
    private Object deviceToken;
    @SerializedName("deviceId")
    @Expose
    private Object deviceId;
    @SerializedName("lkRole")
    @Expose
    private LkRole lkRole;
    @SerializedName("eoFunActivityArray")
    @Expose
    private List<Object> eoFunActivityArray = null;
    @SerializedName("fullName")
    @Expose
    private String fullName;

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(Object alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public Object getEoImage() {
        return eoImage;
    }

    public void setEoImage(Object eoImage) {
        this.eoImage = eoImage;
    }

    public Object getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(Object addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public Object getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(Object addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Object getLandMark() {
        return landMark;
    }

    public void setLandMark(Object landMark) {
        this.landMark = landMark;
    }

    public Object getPinCode() {
        return pinCode;
    }

    public void setPinCode(Object pinCode) {
        this.pinCode = pinCode;
    }

    public Boolean getIsUserVerified() {
        return isUserVerified;
    }

    public void setIsUserVerified(Boolean isUserVerified) {
        this.isUserVerified = isUserVerified;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    public LkRole getLkRole() {
        return lkRole;
    }

    public void setLkRole(LkRole lkRole) {
        this.lkRole = lkRole;
    }

    public List<Object> getEoFunActivityArray() {
        return eoFunActivityArray;
    }

    public void setEoFunActivityArray(List<Object> eoFunActivityArray) {
        this.eoFunActivityArray = eoFunActivityArray;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
