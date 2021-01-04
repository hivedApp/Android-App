package com.megthinksolutions.apps.hived.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.ActivityChooseLoginOptionBinding;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;
import com.megthinksolutions.apps.hived.utils.Utils;

import java.util.Arrays;

public class ChooseLoginOptionActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityChooseLoginOptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_login_option);
        getSupportActionBar().hide();

        PreferenceUtils.getInstance(this,true); // Get the preferences

        if (PreferenceUtils.getInstance().getBoolean(R.string.pref_is_login_key)){
            Intent intent = new Intent(ChooseLoginOptionActivity.this, MainActivity.class);
            startActivity(intent);
        }

        binding.imgPhoneLogin.setOnClickListener(this);
        binding.imgFacebookLogin.setOnClickListener(this);
        binding.imgGmailLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (Utils.isNetworkConnected(this)) {
            if (view == binding.imgPhoneLogin) {
                nextActivity();
            }
            if (view == binding.imgFacebookLogin) {
                nextActivity();
            }
            if (view == binding.imgGmailLogin) {
                nextActivity();
            }
        } else {
            Utils.showSnackBar(this, getResources().getString(R.string.internet_not_available));
        }

    }

    private void nextActivity() {
        Intent intent = new Intent(ChooseLoginOptionActivity.this, LoginSignUpActivity.class);
        startActivity(intent);
    }

}