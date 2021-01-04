package com.megthinksolutions.apps.hived.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.ProductReviewFragmentBinding;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;
import com.megthinksolutions.apps.hived.utils.Utils;
import com.megthinksolutions.apps.hived.viewmodel.ProductReviewViewModel;

public class ProductReviewFragment extends Fragment {
    private ProductReviewFragmentBinding binding;
    private ProductReviewViewModel mViewModel;
    String[] descriptionData = {"Product", "Product Review", "Review"};


    public static ProductReviewFragment newInstance() {
        return new ProductReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.product_review_fragment, container, false);

        binding.stateProgressBar.setStateDescriptionData(descriptionData);

        binding.txtContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getContext())) {
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                } else {
                    if (validation()) {
                        sendProductReviewData();
                    }


                }

            }
        });

        return binding.getRoot();
    }

    private void sendProductReviewData() {
        mViewModel.sendProductReviewData(RequestFormatter.jsonObjectProductReviewPage2(
                PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id),
                PreferenceUtils.getInstance().getString(R.string.pref_product_id),
                binding.editTitle.getText().toString(),
                binding.editDescription.getText().toString(),
                binding.rating.getRating()
        ));

        getProductReviewData();
    }

    private void getProductReviewData() {
        mViewModel.getProductReviewLiveData().observe(getActivity(), new Observer<ProductReviewModel>() {
            @Override
            public void onChanged(ProductReviewModel productReviewModel) {
                if (productReviewModel != null) {
                    Toast.makeText(getActivity(), productReviewModel.getProductSeller(), Toast.LENGTH_SHORT).show();
                    //PreferenceManager.getInstance().putString(R.string.pref_product_review_id, productReviewModel.getProductId());
                    nextFragment();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductReviewViewModel.class);
        // TODO: Use the ViewModel
    }

    private void nextFragment() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_productReviewFragment_to_productSellerReviewFragment);
    }

    private boolean validation() {
        String title = binding.editTitle.getText().toString();
        String description = binding.editDescription.getText().toString();
        float rating = binding.rating.getRating();

        if (title.isEmpty()) {
            Utils.showSnackBar(getActivity(), "Please Enter Product Review Title");
            return false;
        }

        if (title.length() < 10) {
            Utils.showSnackBar(getActivity(), "At Least Enter 10 Alphabetic in Product Review Title");
            return false;
        }

        if (description.isEmpty()) {
            Utils.showSnackBar(getActivity(), "Please Enter Product Review Description");
            return false;
        }

        if (description.length() < 15) {
            Utils.showSnackBar(getActivity(), "At Least Enter 15 Alphabetic Product Review Description");
            return false;
        }

        return true;
    }

}