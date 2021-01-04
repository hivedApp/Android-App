package com.megthinksolutions.apps.hived.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.responseModel.ErrorResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductReviewRepository {
    private String TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    private static ProductReviewRepository productReviewRepository;
    private Context context;

    public static ProductReviewRepository getInstance() {
        if (productReviewRepository == null) {
            productReviewRepository = new ProductReviewRepository();
        }
        return productReviewRepository;
    }

    public ProductReviewRepository() {
       // apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        apiInterface = ApiClient.createService(ApiInterface.class);

    }

    public void productReviewData(JsonObject data,
                                   MutableLiveData<ProductReviewModel> productReviewMutableLiveData){
       apiInterface.getProductReview(data)
                .enqueue(new Callback<ProductReviewModel>() {
                    @Override
                    public void onResponse(Call<ProductReviewModel> call, Response<ProductReviewModel> response) {
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
                    public void onFailure(Call<ProductReviewModel> call, Throwable t) {
                        Log.d("onFailure: ", "onFailure: "+t.getMessage());

                    }
                });

    }

}
