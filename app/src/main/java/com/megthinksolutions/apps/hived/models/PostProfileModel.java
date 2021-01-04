package com.megthinksolutions.apps.hived.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProfileModel {
    @SerializedName("usrerId")
    @Expose
    private String usrerId;
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("user_profile_url")
    @Expose
    private String userProfileUrl;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("images")
    @Expose
    private String images;

    public String getUsrerId() {
        return usrerId;
    }

    public void setUsrerId(String usrerId) {
        this.usrerId = usrerId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
