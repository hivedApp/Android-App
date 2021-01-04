package com.megthinksolutions.apps.hived.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megthinksolutions.apps.hived.models.HomeImageListModel;

import java.util.List;

public class HomeListResponse {
    @SerializedName("paginationKeyValue1")
    @Expose
    private String paginationKeyValue1;
    @SerializedName("paginationKeyValue2")
    @Expose
    private String paginationKeyValue2;
    @SerializedName("productPostReviewResponse")
    @Expose
    private List<ProductPostReviewResponse> productPostReviewResponse = null;

    public String getPaginationKeyValue1() {
        return paginationKeyValue1;
    }

    public void setPaginationKeyValue1(String paginationKeyValue1) {
        this.paginationKeyValue1 = paginationKeyValue1;
    }

    public String getPaginationKeyValue2() {
        return paginationKeyValue2;
    }

    public void setPaginationKeyValue2(String paginationKeyValue2) {
        this.paginationKeyValue2 = paginationKeyValue2;
    }

    public List<ProductPostReviewResponse> getProductPostReviewResponse() {
        return productPostReviewResponse;
    }

    public void setProductPostReviewResponse(List<ProductPostReviewResponse> productPostReviewResponse) {
        this.productPostReviewResponse = productPostReviewResponse;
    }





}


