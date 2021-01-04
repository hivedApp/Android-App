package com.megthinksolutions.apps.hived.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.repository.ProductReviewRepository;

public class ProductReviewViewModel extends ViewModel {
    private ProductReviewRepository productReviewRepository;
    private MutableLiveData<ProductReviewModel> getProductReviewModelLiveData = new MutableLiveData<>();

    public void sendProductReviewData(JsonObject jsonObject) {
        productReviewRepository.getInstance().productReviewData(jsonObject, getProductReviewModelLiveData);
    }

    public LiveData<ProductReviewModel> getProductReviewLiveData() {
        return getProductReviewModelLiveData;
    }
}