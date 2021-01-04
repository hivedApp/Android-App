package com.megthinksolutions.apps.hived.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;
import com.megthinksolutions.apps.hived.responseModel.ProductPostReviewResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDataSource{
//public class HomeDataSource extends PageKeyedDataSource<Integer, HomeListResponse.ProductPostReviewResponse> {
//    public static String PAGINATION_KEY_VALUE_1 = "6";
//    public static String PAGINATION_KEY_VALUE_2 = "";
//
//    @Override
//    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull
//            LoadInitialCallback<Integer, HomeListResponse.ProductPostReviewResponse> callback) {
//
//        ApiClient.getInstance()
//                .getApiInterface()
//                .getHomeListResponse(RequestFormatter.jsonObjectHomeListWithPagination(12,
//                "",
//                "")).enqueue(new Callback<HomeListResponse>() {
//            @Override
//            public void onResponse(Call<HomeListResponse> call,
//                                   Response<HomeListResponse> response) {
//                if (response.body() != null){
//                    if (response.code() == 200) {
//                        if (response.body().getPaginationKeyValue1() != null
//                                && response.body().getPaginationKeyValue2() != null) {
//
//                            PAGINATION_KEY_VALUE_1 = response.body().getPaginationKeyValue1();
//                            PAGINATION_KEY_VALUE_2 = response.body().getPaginationKeyValue2();
//
//                        }
//
//                        Log.d("AjayAInitial", "Hiiii " + PAGINATION_KEY_VALUE_1 +"  "+PAGINATION_KEY_VALUE_2);
//                        callback.onResult(response.body().getProductPostReviewResponse(), null, null);
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<HomeListResponse> call, Throwable t) {
//                Log.d("AjayAInitialFailure", call.toString() + "   " + t.toString());
//
//            }
//        });
//    }
//
//    @Override
//    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull
//            LoadCallback<Integer, HomeListResponse.ProductPostReviewResponse> callback) {
//
//    }
//
//    @Override
//    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull
//            LoadCallback<Integer, HomeListResponse.ProductPostReviewResponse> callback) {
//
//        ApiClient.getInstance()
//                .getApiInterface()
//                .getHomeListResponse(RequestFormatter.jsonObjectHomeListWithPagination(12,
//                PAGINATION_KEY_VALUE_1,
//                PAGINATION_KEY_VALUE_2)).enqueue(new Callback<HomeListResponse>() {
//            @Override
//            public void onResponse(Call<HomeListResponse> call,
//                                   Response<HomeListResponse> response) {
//                if (response.body() != null){
//                    if (response.code() == 200) {
//                        Log.d("AjayAfter", "Hiiii" + response.body().getProductPostReviewResponse().get(1).getId());
//
//                        callback.onResult(response.body().getProductPostReviewResponse(), params.key + 1);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<HomeListResponse> call, Throwable t) {
//                Log.d("AjayAfterFailure", call.toString() + "   " + t.toString());
//
//            }
//        });
//    }
//
}
