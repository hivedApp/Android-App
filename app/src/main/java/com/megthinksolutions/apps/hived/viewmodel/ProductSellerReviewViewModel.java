package com.megthinksolutions.apps.hived.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.repository.ProductReviewRepository;

public class ProductSellerReviewViewModel extends ViewModel {
    private ProductReviewRepository productSellerReviewRepository;
    private MutableLiveData<ProductReviewModel> getProductSellerReviewLiveData = new MutableLiveData<>();

    public void sendProductSellerreviewData(JsonObject jsonObject) {
        productSellerReviewRepository.getInstance().productReviewData(jsonObject, getProductSellerReviewLiveData);
    }

    public LiveData<ProductReviewModel> getProductSellerReviewLiveData() {
        return getProductSellerReviewLiveData;
    }
}