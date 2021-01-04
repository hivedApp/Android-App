package com.megthinksolutions.apps.hived.networking;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.megthinksolutions.apps.hived.utils.ConstantUrl;
import com.megthinksolutions.apps.hived.utils.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = ConstantUrl.BASE_API_URL;
    private final static long CACHE_SIZE = 10 * 1024 * 1024; // 10MB Cache size

//    private static OkHttpClient buildClient(Context context) {
//
//        // Build interceptor
//        final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
//            Response originalResponse = chain.proceed(chain.request());
//            if (NetworkUtil.hasNetwork(context)) {
//                int maxAge = 60; // read from cache for 1 minute
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//        };
//
//        // Create Cache
//       // Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
//
//        return new OkHttpClient
//                .Builder()
//                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//               // .cache(cache)
//                .build();
//    }
//
//    public static Retrofit getClient(Context context) {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .client(buildClient(context))
//                    .addConverterFactory(GsonConverterFactory.create())
//                    // .baseUrl("https://api.themoviedb.org/3/")
//                    .baseUrl(BASE_URL)
//                    .build();
//        }
//        return retrofit;
//    }
//
    //todo new

    private static OkHttpClient httpClientsss = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                           // .addHeader("Authorization", PreferenceUtils.getInstance().getString(R.string.pref_user_token_value))
                            .build();
                    return chain.proceed(newRequest);
                }
            })
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();


    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }




}
