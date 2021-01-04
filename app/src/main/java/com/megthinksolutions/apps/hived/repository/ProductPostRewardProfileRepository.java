package com.megthinksolutions.apps.hived.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.models.PostProfileModel;
import com.megthinksolutions.apps.hived.models.ProductProfileModel;
import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.responseModel.ErrorResponse;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPostRewardProfileRepository {
    public static final String TAG = ProductPostRewardProfileRepository.class.getSimpleName();
    private ApiInterface apiInterface;
    private static ProductPostRewardProfileRepository repository;

    public static ProductPostRewardProfileRepository getInstance() {
        if (repository == null) {
            repository = new ProductPostRewardProfileRepository();
        }
        return repository;
    }

    public ProductPostRewardProfileRepository() {
        apiInterface = ApiClient.createService(ApiInterface.class);
    }


    public void getProductProfileData(JsonObject jsonObjectUserId, MutableLiveData<List<ProductProfileModel>> productProfileLiveData) {
        apiInterface.getProductProfile(jsonObjectUserId)
                .enqueue(new Callback<List<ProductProfileModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductProfileModel>> call, Response<List<ProductProfileModel>> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            if (response.code() == 200) {
                                Log.d(TAG, response.code() + "  " + response.raw() + response.body().get(0).getProductId());
                                productProfileLiveData.setValue(response.body());
                            } else if (response.code() == 400) {
                                Log.d(TAG, "" + response.raw() + "  HTTP Bad Request");
                            } else {
                                ErrorResponse errorResponse = null;
                                try {
                                    errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                                    Log.e("Error", "code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
                                    // FirebaseCrashlytics.getInstance().log("code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    // FirebaseCrashlytics.getInstance().recordException(e);
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductProfileModel>> call, Throwable t) {
                        Log.d(TAG, "onFailure ::" + t.getMessage());

                    }
                });

    }

    public void getPostProfileData(JsonObject jsonObjectUserId, MutableLiveData<List<PostProfileModel>> postProfileLiveData) {
        apiInterface
                .getPostProfile(jsonObjectUserId)
                .enqueue(new Callback<List<PostProfileModel>>() {
                    @Override
                    public void onResponse(Call<List<PostProfileModel>> call, Response<List<PostProfileModel>> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            if (response.code() == 200) {
                                Log.d(TAG, response.code() + "  " + response.raw() + response.body().get(0).getProductDescription());
                                postProfileLiveData.setValue(response.body());
                            } else {
                                ErrorResponse errorResponse = null;
                                try {
                                    errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                                    Log.e("Error", "code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
                                    // FirebaseCrashlytics.getInstance().log("code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    // FirebaseCrashlytics.getInstance().recordException(e);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PostProfileModel>> call, Throwable t) {
                        Log.d(TAG, "onFailure ::" + t.getMessage());
                    }
                });

    }


}
