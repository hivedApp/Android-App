package com.megthinksolutions.apps.hived.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.ActivityProductBinding;
import com.megthinksolutions.apps.hived.fragments.ProductTypeFragment;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;

public class ProductActivity extends AppCompatActivity {
   private ActivityProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
       // AWSMobileClient.getInstance().initialize(this).execute();

        getSupportActionBar().hide();
        PreferenceUtils.getInstance(this,true); // Get the preferences

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        NavHostFragment navHostFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_product_activity);
//        ProductTypeFragment fragment =
//                navHostFragment.getChildFragmentManager().getFragment();
//
//        fragment.onActivityResult(requestCode, resultCode, data);
    }
}