package com.megthinksolutions.apps.hived.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.activity.ProductActivity;
import com.megthinksolutions.apps.hived.adapters.PostProfileAdapter;
import com.megthinksolutions.apps.hived.adapters.ProductProfileAdapter;
import com.megthinksolutions.apps.hived.databinding.ProfileFragmentBinding;
import com.megthinksolutions.apps.hived.models.PostProfileModel;
import com.megthinksolutions.apps.hived.models.ProductProfileModel;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding binding;
    private ProfileViewModel mViewModel;
    private List<ProductProfileModel> productProfileModelList = new ArrayList<>();
    private List<PostProfileModel> postProfileModelList = new ArrayList<>();
    private ProductProfileAdapter productAdapter;
    private PostProfileAdapter postProfileAdapter;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);

        binding.btnStateProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("addNewValue","ProfileFragment");
                startActivity(intent);
            }
        });


        binding.rvProductProfile.setVisibility(View.VISIBLE);
        binding.rvPostProfile.setVisibility(View.GONE);
        binding.rvRewardsProfile.setVisibility(View.GONE);

        binding.btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnProducts.setTextColor(getResources().getColor(R.color.red));
                binding.btnProducts.setBackgroundColor(getResources().getColor(R.color.white));
                binding.btnPosts.setBackgroundColor(getResources().getColor(R.color.light_very_grey));
                binding.btnPosts.setTextColor(getResources().getColor(R.color.text_color));
                binding.btnRewards.setBackgroundColor(getResources().getColor(R.color.light_very_grey));
                binding.btnRewards.setTextColor(getResources().getColor(R.color.text_color));
                binding.rvProductProfile.setVisibility(View.VISIBLE);
                binding.rvPostProfile.setVisibility(View.GONE);
                binding.rvRewardsProfile.setVisibility(View.GONE);
            }
        });

        binding.btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnProducts.setTextColor(getResources().getColor(R.color.text_color));
                binding.btnProducts.setBackgroundColor(getResources().getColor(R.color.light_very_grey));
                binding.btnPosts.setBackgroundColor(getResources().getColor(R.color.white));
                binding.btnPosts.setTextColor(getResources().getColor(R.color.red));
                binding.btnRewards.setBackgroundColor(getResources().getColor(R.color.light_very_grey));
                binding.btnRewards.setTextColor(getResources().getColor(R.color.text_color));
                binding.rvPostProfile.setVisibility(View.VISIBLE);
                binding.rvProductProfile.setVisibility(View.GONE);
                binding.rvRewardsProfile.setVisibility(View.GONE);
            }
        });

        binding.btnRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnProducts.setTextColor(getResources().getColor(R.color.text_color));
                binding.btnProducts.setBackgroundColor(getResources().getColor(R.color.light_very_grey));
                binding.btnPosts.setBackgroundColor(getResources().getColor(R.color.light_very_grey));
                binding.btnPosts.setTextColor(getResources().getColor(R.color.text_color));
                binding.btnRewards.setBackgroundColor(getResources().getColor(R.color.white));
                binding.btnRewards.setTextColor(getResources().getColor(R.color.red));
                binding.rvRewardsProfile.setVisibility(View.VISIBLE);
                binding.rvPostProfile.setVisibility(View.GONE);
                binding.rvProductProfile.setVisibility(View.GONE);
            }
        });

//        productAdapter = new ProductProfileAdapter(profileModelList, getActivity());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        binding.rvProductProfile.setLayoutManager(mLayoutManager);
//        binding.rvProductProfile.setItemAnimator(new DefaultItemAnimator());
//        binding.rvProductProfile.setAdapter(productAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        refreshProductProfileData();
    }

    private void refreshProductProfileData() {
        binding.progressBarProfile.setVisibility(View.VISIBLE);

//        try {
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    try {
//                        if (getActivity() != null) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
                                    mViewModel.sendProductProfileData(RequestFormatter.jsonObjectProfileProduct
                                            (PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id)));

                                    mViewModel.sendPostProfileData(RequestFormatter.jsonObjectProfilePost
                                            (PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id)));
//                                }
//                            });
//                        }
//                    } catch (Exception e) {
//                        e.getLocalizedMessage();
//                    }
//                }
//            }, 7000, 10000);
//        } catch (Exception e) {
//            e.getLocalizedMessage();
//        }

//        mViewModel.sendProductProfileData();
//        mViewModel.sendPostProfileData();
        getProductProfileData();
        getPostProfileData();

    }

    private void getProductProfileData() {
        mViewModel.getProductProfileData().observe(getActivity(), new Observer<List<ProductProfileModel>>() {
            @Override
            public void onChanged(List<ProductProfileModel> productProfileModels) {
                if (productProfileModels != null) {
                    binding.progressBarProfile.setVisibility(View.GONE);
                    productProfileModelList = productProfileModels;
                   // Log.d("product",postProfileModelList.get(0).getProductDescription());

                    productAdapter = new ProductProfileAdapter(productProfileModelList, getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    binding.rvProductProfile.setLayoutManager(mLayoutManager);
                    binding.rvProductProfile.setItemAnimator(new DefaultItemAnimator());
                    binding.rvProductProfile.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getPostProfileData() {
        mViewModel.getPostProfileData().observe(getActivity(), new Observer<List<PostProfileModel>>() {
            @Override
            public void onChanged(List<PostProfileModel> postProfileModels) {
                if (postProfileModels != null) {
                    binding.progressBarProfile.setVisibility(View.GONE);
                    postProfileModelList = postProfileModels;


                    postProfileAdapter = new PostProfileAdapter(postProfileModelList, getActivity());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    binding.rvPostProfile.setLayoutManager(mLayoutManager);
                    binding.rvPostProfile.setItemAnimator(new DefaultItemAnimator());
                    binding.rvPostProfile.setAdapter(postProfileAdapter);

                    postProfileAdapter.notifyDataSetChanged();


                }
            }
        });
    }

}