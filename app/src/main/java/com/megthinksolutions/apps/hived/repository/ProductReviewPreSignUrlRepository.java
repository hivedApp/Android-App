package com.megthinksolutions.apps.hived.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.models.ProductReviewWithPreSignUrlModel;
import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.responseModel.ErrorResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductReviewPreSignUrlRepository {
    private String TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    private static ProductReviewPreSignUrlRepository productReviewRepository;
    private Context context;

    public static ProductReviewPreSignUrlRepository getInstance() {
        if (productReviewRepository == null) {
            productReviewRepository = new ProductReviewPreSignUrlRepository();
        }
        return productReviewRepository;
    }

    public ProductReviewPreSignUrlRepository() {
        // apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        apiInterface = ApiClient.createService(ApiInterface.class);

    }

    public void productReviewData(JsonObject data,
                                  MutableLiveData<ProductReviewWithPreSignUrlModel> productReviewMutableLiveData){
        apiInterface.getProductReviewPreSignUrl(data)
                .enqueue(new Callback<ProductReviewWithPreSignUrlModel>() {
                    @Override
                    public void onResponse(Call<ProductReviewWithPreSignUrlModel> call, Response<ProductReviewWithPreSignUrlModel> response) {
                        if (response.isSuccessful() && response.body() != null){
                            if (response.code() == 200){
                                Log.d(TAG, "onResponse: "+response.raw());
                                productReviewMutableLiveData.setValue(response.body());
                            }else {
                                ErrorResponse errorResponse = null;
                                try {
                                    errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
                                    Log.e("onError:", "code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
                                    //  FirebaseCrashlytics.getInstance().log("code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    // FirebaseCrashlytics.getInstance().recordException(e);
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ProductReviewWithPreSignUrlModel> call, Throwable t) {
                        Log.d("onFailure: ", "onFailure: "+t.getMessage());

                    }
                });

    }

}

