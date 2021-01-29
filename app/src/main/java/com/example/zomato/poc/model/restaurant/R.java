
package com.example.zomato.poc.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class R {

    @SerializedName("res_id")
    @Expose
    private Integer resId;
    @SerializedName("is_grocery_store")
    @Expose
    private Boolean isGroceryStore;
    @SerializedName("has_menu_status")
    @Expose
    private HasMenuStatus hasMenuStatus;

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Boolean getIsGroceryStore() {
        return isGroceryStore;
    }

    public void setIsGroceryStore(Boolean isGroceryStore) {
        this.isGroceryStore = isGroceryStore;
    }

    public HasMenuStatus getHasMenuStatus() {
        return hasMenuStatus;
    }

    public void setHasMenuStatus(HasMenuStatus hasMenuStatus) {
        this.hasMenuStatus = hasMenuStatus;
    }

}
