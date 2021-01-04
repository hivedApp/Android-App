package com.megthinksolutions.apps.hived.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.PostProfileModel;
import com.megthinksolutions.apps.hived.models.ProductProfileModel;
import com.megthinksolutions.apps.hived.repository.ProductPostRewardProfileRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private ProductPostRewardProfileRepository productPostRewardProfileRepository;
    private MutableLiveData<List<ProductProfileModel>> productProfileLiveData = new MutableLiveData<>();
    private MutableLiveData<List<PostProfileModel>> postProfileLiveData = new MutableLiveData<>();

    public void sendProductProfileData(JsonObject jsonObjectUserId) {
       productPostRewardProfileRepository.getInstance().getProductProfileData(jsonObjectUserId, productProfileLiveData);
    }

    public void sendPostProfileData(JsonObject jsonObjectUserId) {
        productPostRewardProfileRepository.getInstance().getPostProfileData(jsonObjectUserId, postProfileLiveData);
    }

    public LiveData<List<ProductProfileModel>> getProductProfileData(){
        return productProfileLiveData;
    }

    public LiveData<List<PostProfileModel>> getPostProfileData(){
        return postProfileLiveData;
    }


}