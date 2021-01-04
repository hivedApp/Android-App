package com.megthinksolutions.apps.hived.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.activity.MainActivity;
import com.megthinksolutions.apps.hived.databinding.ProductPostFragmentBinding;
import com.megthinksolutions.apps.hived.utils.Utils;
import com.megthinksolutions.apps.hived.viewmodel.ProductPostViewModel;

public class ProductPostFragment extends Fragment {
    private ProductPostFragmentBinding binding;
    private ProductPostViewModel mViewModel;

    public static ProductPostFragment newInstance() {
        return new ProductPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.product_post_fragment, container, false);

        binding.txtViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getActivity())){
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                }else {

                    Toast.makeText(getActivity(), "Api Required!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.imageWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getActivity())){
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                }else {
                    Toast.makeText(getActivity(), "Api Required for share on whatsApp", Toast.LENGTH_SHORT).show();

                }
            }
        });

        binding.imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getActivity())){
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                }else {

                    Toast.makeText(getActivity(), "Api Required for share", Toast.LENGTH_SHORT).show();

                }
            }
        });
        binding.imageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getActivity())){
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                }else {

                    Toast.makeText(getActivity(), "Api Required for share on facebook", Toast.LENGTH_SHORT).show();

                }
            }
        });



        binding.txtHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getActivity())){
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                }else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductPostViewModel.class);
        // TODO: Use the ViewModel
    }

}