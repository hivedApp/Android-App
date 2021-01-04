package com.megthinksolutions.apps.hived.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.adapters.HomeListAdapter;
import com.megthinksolutions.apps.hived.adapters.HomeListAdapter2;
import com.megthinksolutions.apps.hived.databinding.FragmentHomeBinding;
import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.repository.HomeDataSource;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;
import com.megthinksolutions.apps.hived.responseModel.ProductPostReviewResponse;
import com.megthinksolutions.apps.hived.utils.ConstantUrl;
import com.megthinksolutions.apps.hived.utils.PaginationAdapterCallback;
import com.megthinksolutions.apps.hived.utils.PaginationScrollListener;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements PaginationAdapterCallback {
    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private List<HomeListResponse> homeListResponseList = new ArrayList<>();
    private HomeListAdapter homeListAdapter;
    private String pageValue1, pageValue2;
    HomeListAdapter2 adapter;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private static int TOTAL_PAGES = 20;
    private int currentPage = PAGE_START;

    private ApiInterface apiInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);

//        homeListAdapter2 = new HomeListAdapter2(getActivity(), homeListResponseList);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        binding.rvHome.setLayoutManager(manager);
//        binding.rvHome.setAdapter(homeListAdapter2);

        adapter = new HomeListAdapter2(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvHome.setLayoutManager(linearLayoutManager);
        binding.rvHome.setItemAnimator(new DefaultItemAnimator());
        binding.rvHome.setAdapter(adapter);

        binding.rvHome.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        //apiInterface = ApiClient.getClient(getActivity()).create(ApiInterface.class);
        apiInterface = ApiClient.createService(ApiInterface.class);

        loadFirstPage();

        binding.errorLayout.errorBtnRetry.setOnClickListener(view -> loadFirstPage());

        binding.mainSwiperefresh.setOnRefreshListener(this::doRefresh);

        return binding.getRoot();
    }

    private void doRefresh() {
        binding.progressBarHome.setVisibility(View.VISIBLE);
//        if (callTopRatedMoviesApi().isExecuted())
//            callTopRatedMoviesApi().cancel();

        // TODO: Check if data is stale.
        //  Execute network request if cache is expired; otherwise do not update data.
        adapter.getMovies().clear();
        adapter.notifyDataSetChanged();
        loadFirstPage();
        binding.mainSwiperefresh.setRefreshing(false);
    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        // To ensure list is visible when retry button in error view is clicked
        hideErrorView();
        currentPage = PAGE_START;

        Call<HomeListResponse> call = apiInterface.getHomeListResponse(RequestFormatter
                .jsonObjectHomeListWithPagination(PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id),
                        "",
                        ""));

        call.enqueue(new Callback<HomeListResponse>() {
            @Override
            public void onResponse(Call<HomeListResponse> call, Response<HomeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == 200) {
                        hideErrorView();

                        // Got data. Send it to adapter
                        pageValue1 = response.body().getPaginationKeyValue1();
                        pageValue2 = response.body().getPaginationKeyValue2();

                        List<ProductPostReviewResponse> results = fetchResults(response);
                        binding.progressBarHome.setVisibility(View.GONE);
                        adapter.addAll(results);

//                        if (pageValue1.isEmpty() && pageValue2.isEmpty()) {
//                            TOTAL_PAGES -= 1;
//                        } else {
//                            TOTAL_PAGES += 1;
//                        }

                        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;

                    }
                }
            }

            @Override
            public void onFailure(Call<HomeListResponse> call, Throwable t) {
                Log.d("onFailureAjay M", t.getMessage());
                t.printStackTrace();
                showErrorView(t);
            }
        });
    }

    private List<ProductPostReviewResponse> fetchResults(Response<HomeListResponse> response) {
        HomeListResponse topRatedMovies = response.body();
        return topRatedMovies.getProductPostReviewResponse();
    }

    public void retryPageLoad() {
        loadNextPage();
    }


    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        //    if (pageValue1 != null && pageValue2 != null) {

        Call<HomeListResponse> call = apiInterface.getHomeListResponse(
                RequestFormatter.jsonObjectHomeListWithPagination(PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id),
                        pageValue1,
                        pageValue2));

        call.enqueue(new Callback<HomeListResponse>() {
            @Override
            public void onResponse(Call<HomeListResponse> call, Response<HomeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == 200) {

                        adapter.removeLoadingFooter();
                        isLoading = false;

                        Log.d(TAG, "page1: " + response.body().getPaginationKeyValue1() +
                                " page2: " + response.body().getPaginationKeyValue2());

                        pageValue1 = response.body().getPaginationKeyValue1();
                        pageValue2 = response.body().getPaginationKeyValue2();

                        List<ProductPostReviewResponse> results = fetchResults(response);
                        adapter.addAll(results);

                        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;

//                            if (pageValue1.isEmpty() && pageValue2.isEmpty()) {
//                                TOTAL_PAGES -= 1;
//                            } else {
//                                TOTAL_PAGES += 1;
//                            }

                    }

                } else {
                    Toast.makeText(getActivity(), "Not Successful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<HomeListResponse> call, Throwable t) {
                t.printStackTrace();
                adapter.showRetry(true, fetchErrorMessage(t));
            }
        });

//        } else {
//            Log.d(TAG, "Page 1 or 2 is null");
//        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        binding.progressBarHome.setVisibility(View.VISIBLE);
//
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
//
//        homeListAdapter = new HomeListAdapter(getActivity(),HomeFragment.this, homeListResponseList);
//        binding.rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //homeViewModel.sendHomeList(RequestFormatter.jsonObjectHomeListWithPagination(12,"",""));
//        getHomeListData();
    }

    private void getHomeListData() {
//        homeViewModel.homeListLiveData.observe(getActivity(), new Observer<PagedList<HomeListResponse>>() {
//            @Override
//            public void onChanged(PagedList<HomeListResponse> homeListResponses) {
//                   if (homeListResponses != null){
//                       binding.progressBarHome.setVisibility(View.GONE);
////                       List<HomeListResponse.ProductPostReviewResponse> response = homeListResponses;
////                       homeListAdapter = new HomeListAdapter(getActivity(), HomeFragment.this, response);
////                       LinearLayoutManager manager = new LinearLayoutManager(getActivity());
////                       binding.rvHome.setLayoutManager(manager);
////                       binding.rvHome.setAdapter(homeListAdapter);
////
////                       homeListAdapter.notifyDataSetChanged();
//                        homeListAdapter.submitList(homeListResponses);
//                   }
//            }
//        });

        binding.rvHome.setAdapter(homeListAdapter);
    }

    private void showErrorView(Throwable throwable) {
        if (binding.errorLayout.errorLayout.getVisibility() == View.GONE) {
            binding.errorLayout.errorLayout.setVisibility(View.VISIBLE);
            binding.progressBarHome.setVisibility(View.GONE);

            binding.errorLayout.errorTxtCause.setText(fetchErrorMessage(throwable));
        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private void hideErrorView() {
        if (binding.errorLayout.errorLayout.getVisibility() == View.VISIBLE) {
            binding.errorLayout.errorLayout.setVisibility(View.GONE);
            binding.progressBarHome.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}