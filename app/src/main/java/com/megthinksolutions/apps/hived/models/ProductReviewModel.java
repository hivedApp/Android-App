package com.megthinksolutions.apps.hived.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductReviewModel {
    @SerializedName("product_seller")
    @Expose
    private String productSeller;
    @SerializedName("productId")
    @Expose
    private String productId;

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


}
