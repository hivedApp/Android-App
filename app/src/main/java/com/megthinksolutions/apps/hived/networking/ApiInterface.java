package com.megthinksolutions.apps.hived.networking;

import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.models.PostProfileModel;
import com.megthinksolutions.apps.hived.models.ProductProfileModel;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.models.ProductReviewWithPreSignUrlModel;
import com.megthinksolutions.apps.hived.models.UploadFile;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("devhome/")
    Call<HomeListResponse> getHomeListResponse(
            @Body JsonObject body
    );

    @Headers("Content-Type: application/json")
    @POST("devhome/createproduct")
    Call<ProductReviewWithPreSignUrlModel> getProductReviewPreSignUrl(
            @Body JsonObject body
    );


    @Headers("Content-Type: application/json")
    @POST("devhome/createproduct")
    Call<ProductReviewModel> getProductReview(
            @Body JsonObject body
    );

    @Multipart
    @POST("devhome/upload")
    Call<UploadFile> getUploadFile(
            @Part MultipartBody.Part file
    );

    @POST("devhome/profileproduct")
    Call<List<ProductProfileModel>> getProductProfile(
            @Body JsonObject body
    );

    @POST("devhome/profilepost")
    Call<List<PostProfileModel>> getPostProfile(
            @Body JsonObject body
    );


}
