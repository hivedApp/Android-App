package com.megthinksolutions.apps.hived.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;

public class HomeDataSourceFactory{
//public class HomeDataSourceFactory extends DataSource.Factory {
//    private MutableLiveData<PageKeyedDataSource<Integer, HomeListResponse.ProductPostReviewResponse>> homeDataSourceMutableLiveData = new MutableLiveData<>();
//
//    @Override
//    public DataSource<Integer, HomeListResponse.ProductPostReviewResponse> create() {
//        HomeDataSource homeDataSource = new HomeDataSource();
//        homeDataSourceMutableLiveData.postValue(homeDataSource);
//        return homeDataSource;
//    }
//
//    public MutableLiveData<PageKeyedDataSource<Integer, HomeListResponse.ProductPostReviewResponse>> getHomeDataSourceMutableLiveData(){
//        return homeDataSourceMutableLiveData;
//    }
}
