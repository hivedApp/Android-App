package com.megthinksolutions.apps.hived.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductReviewWithPreSignUrlModel {
    @SerializedName("product_seller")
    @Expose
    private String productSeller;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("preSignedURL")
    @Expose
    private List<String> preSignedURL = null;

    public String getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(String productSeller) {
        this.productSeller = productSeller;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<String> getPreSignedURL() {
        return preSignedURL;
    }

    public void setPreSignedURL(List<String> preSignedURL) {
        this.preSignedURL = preSignedURL;
    }

}
