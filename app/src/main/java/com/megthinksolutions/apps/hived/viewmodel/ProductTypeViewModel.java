package com.megthinksolutions.apps.hived.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.models.ProductReviewWithPreSignUrlModel;
import com.megthinksolutions.apps.hived.repository.ProductReviewPreSignUrlRepository;
import com.megthinksolutions.apps.hived.repository.ProductReviewRepository;

public class ProductTypeViewModel extends ViewModel {
    private ProductReviewPreSignUrlRepository productReviewPreSignUrlRepository;
    private MutableLiveData<ProductReviewWithPreSignUrlModel> getProductTypeModelLiveData = new MutableLiveData<>();

    public void sendProductTypeData(JsonObject jsonObject) {
        productReviewPreSignUrlRepository.getInstance().productReviewData(jsonObject, getProductTypeModelLiveData);
    }

    public LiveData<ProductReviewWithPreSignUrlModel> getProductTypeLiveData() {
        return getProductTypeModelLiveData;
    }

}