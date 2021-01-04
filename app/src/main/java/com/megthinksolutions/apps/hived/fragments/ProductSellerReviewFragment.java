package com.megthinksolutions.apps.hived.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.ProductSellerReviewFragmentBinding;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;
import com.megthinksolutions.apps.hived.utils.Utils;
import com.megthinksolutions.apps.hived.viewmodel.ProductSellerReviewViewModel;

public class ProductSellerReviewFragment extends Fragment {
    private ProductSellerReviewFragmentBinding binding;
    private ProductSellerReviewViewModel mViewModel;
    String[] descriptionData = {"Product", "Product Review", "Review"};


    public static ProductSellerReviewFragment newInstance() {
        return new ProductSellerReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.product_seller_review_fragment, container, false);

        binding.stateProgressBar.setStateDescriptionData(descriptionData);

        binding.txtContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getActivity())){
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                }else {
                    mViewModel.sendProductSellerreviewData(RequestFormatter.jsonObjectProductReviewPage3(
                            PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id),
                            PreferenceUtils.getInstance().getString(R.string.pref_product_id),
                            "",
                            binding.autoCompleteSeller.getText().toString(),
                            binding.autoCompleteBranch.getText().toString(),
                            binding.editSalesPerson.getText().toString(),
                            binding.editTitle.getText().toString(),
                            binding.editDescription.getText().toString(),
                            binding.rating.getRating()));

                    getProductSellerReviewData();
                }
            }
        });

        return binding.getRoot();
    }

    private void getProductSellerReviewData() {
        mViewModel.getProductSellerReviewLiveData().observe(getActivity(), new Observer<ProductReviewModel>() {
            @Override
            public void onChanged(ProductReviewModel productReviewModel) {
                if (productReviewModel != null){
                    Toast.makeText(getActivity(), productReviewModel.getProductSeller(), Toast.LENGTH_SHORT).show();
                   // PreferenceManager.getInstance().putString(R.string.pref_product_review_id, productReviewModel.getProductId());
                    nextFragment();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductSellerReviewViewModel.class);
        // TODO: Use the ViewModel
    }

    private void nextFragment() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_productSellerReviewFragment_to_productPostFragment);
    }

}