
package com.diy.travelers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LkRole {

    @SerializedName("primaryKey")
    @Expose
    private Integer primaryKey;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
