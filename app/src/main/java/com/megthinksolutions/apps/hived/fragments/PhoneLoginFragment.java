package com.megthinksolutions.apps.hived.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.PhoneLoginFragmentBinding;
import com.megthinksolutions.apps.hived.utils.Utils;
import com.megthinksolutions.apps.hived.viewmodel.PhoneLoginViewModel;


public class PhoneLoginFragment extends Fragment implements View.OnClickListener {
    private PhoneLoginFragmentBinding binding;
    private PhoneLoginViewModel mViewModel;
    private String userPhoneNumber = null;

    public static PhoneLoginFragment newInstance() {
        return new PhoneLoginFragment();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.phone_login_fragment, container, false);

        // Click Listeners
        binding.btnContinue.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);



        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PhoneLoginViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if (Utils.isNetworkConnected(getActivity())) {
            if (v == binding.btnContinue) {
                if (isValid()) {
                    userPhoneNumber = binding.editMobileNo.getText().toString();
                    Toast.makeText(getActivity(), userPhoneNumber, Toast.LENGTH_SHORT).show();
                    nextFragment();
                }

            }
            if (v == binding.btnBack) {
                previousFragment();
            }
        } else {
            Snackbar.make(binding.getRoot(), R.string.internet_not_available, Snackbar.LENGTH_LONG).show();
        }

    }

    private void nextFragment() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_phoneLoginFragment_to_OTPFragment);
    }

    private void previousFragment() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_phoneLoginFragment_to_chooseLoginOptionFragment);
    }

    private Boolean validPhoneNumber() {
        userPhoneNumber = binding.editMobileNo.getText().toString().trim();

        if (userPhoneNumber.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Field can't be empty", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (userPhoneNumber.length() != 10 || !android.util.Patterns.PHONE.matcher(userPhoneNumber).matches()) {
            Snackbar.make(binding.getRoot(), "Enter valid phone number", Snackbar.LENGTH_LONG).show();
            return false;
        } else {

            return true;
        }

    }

    private boolean isValid() {
        if (binding.editMobileNo.getText().toString().length() < 10) {
            Utils.showSnackBar(getActivity(), "Please enter a valid mobile number");
            return false;
        }
        return true;
    }

}