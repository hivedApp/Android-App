package com.megthinksolutions.apps.hived.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.google.gson.JsonObject;
import com.megthinksolutions.apps.hived.repository.HomeDataSource;
import com.megthinksolutions.apps.hived.repository.HomeDataSourceFactory;
import com.megthinksolutions.apps.hived.repository.HomeListRepository;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;

import java.util.List;
import java.util.concurrent.Executor;

public class HomeViewModel extends ViewModel {
    HomeDataSourceFactory homeDataSourceFactory;
//     LiveData<PagedList<HomeListResponse.ProductPostReviewResponse>> homeListLiveData;
//     LiveData<PageKeyedDataSource<Integer, HomeListResponse.ProductPostReviewResponse>>liveDataSource;
//
//    public HomeViewModel() {
//        homeDataSourceFactory = new HomeDataSourceFactory();
//
//        liveDataSource = homeDataSourceFactory.getHomeDataSourceMutableLiveData();
//
//        PagedList.Config pagedListConfig =
//                (new PagedList.Config.Builder())
//                        .setEnablePlaceholders(false)
//                        .setPageSize((int) Double.parseDouble(HomeDataSource.PAGINATION_KEY_VALUE_1))
//                        .build();
//
//        homeListLiveData = (new LivePagedListBuilder<Integer, HomeListResponse.ProductPostReviewResponse>(homeDataSourceFactory, pagedListConfig))
//                .build();
//    }


//    private MutableLiveData<String> mText;
//
//    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }

//    public void sendHomeList(JsonObject jsonObjectUserId) {
//       homeListRepository.getInstance().sendHomeList(jsonObjectUserId, homeListLiveData);
//    }
//
//    public LiveData<List<HomeListResponse>> getHomeListResponseLiveData(){
//       return homeListLiveData;
//    }


}