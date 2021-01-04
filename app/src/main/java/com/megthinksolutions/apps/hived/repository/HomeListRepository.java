package com.megthinksolutions.apps.hived.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.responseModel.ErrorResponse;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeListRepository {
    public static final String TAG = HomeListRepository.class.getSimpleName();
    private ApiInterface apiInterface;
    private static HomeListRepository homeListRepository;

    public static HomeListRepository getInstance() {
        if (homeListRepository == null) {
            homeListRepository = new HomeListRepository();
        }
        return homeListRepository;
    }

    public HomeListRepository() {
       // apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    public void sendHomeList(JsonObject userId,
                             MutableLiveData<List<HomeListResponse>> homeListLiveData) {

//        apiInterface.homeListResponse(userId)
//                .enqueue(new Callback<List<HomeListResponse>>() {
//                    @Override
//                    public void onResponse(Call<List<HomeListResponse>> call,
//                                           Response<List<HomeListResponse>> response) {
//                        Log.d(TAG,"onResponse: " + response.body());
//                        if (response.code() == 200){
//                            homeListLiveData.setValue(response.body());
//                        } else if (response.code() == 201){
//                            homeListLiveData.setValue(response.body());
//                        }else {
//                            ErrorResponse errorResponse = null;
//                            try {
//                                errorResponse = new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
//                                Log.e("Error", "code: " + response.code() + " error: " + errorResponse.getError() + " errorMsg: " + errorResponse.getErrorMsg());
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<HomeListResponse>> call, Throwable error) {
//                         Log.d(TAG,"onFailure: " + error.getMessage());
//                        if (error instanceof SocketTimeoutException)
//                        {
//                            // "Connection Timeout";
//                        }
//                        else if (error instanceof IOException)
//                        {
//                            // "Timeout";
//                        }
//                        else
//                        {
//                            //Call was cancelled by user
//                            if(call.isCanceled())
//                            {
//                                System.out.println("Call was cancelled forcefully");
//                            }
//                            else
//                            {
//                                //Generic error handling
//                                System.out.println("Network Error :: " + error.getLocalizedMessage());
//                            }
//                        }
//
//                    }
//                });
    }
}
