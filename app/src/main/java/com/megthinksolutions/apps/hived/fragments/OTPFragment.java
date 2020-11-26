package com.megthinksolutions.apps.hived.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.activity.MainActivity;
import com.megthinksolutions.apps.hived.databinding.OtpFragmentBinding;
import com.megthinksolutions.apps.hived.utils.Utils;
import com.megthinksolutions.apps.hived.viewmodel.OTPViewModel;


public class OTPFragment extends Fragment implements View.OnClickListener{
    private OtpFragmentBinding binding;
    private OTPViewModel mViewModel;

    public static OTPFragment newInstance() {
        return new OTPFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.otp_fragment, container, false);

        binding.btnValidate.setOnClickListener(this);
        binding.tvResend.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);

        binding.edOtp1.addTextChangedListener(new GenericTextWatcher(binding.edOtp1, binding.edOtp2));
        binding.edOtp2.addTextChangedListener(new GenericTextWatcher(binding.edOtp2, binding.edOtp3));
        binding.edOtp3.addTextChangedListener(new GenericTextWatcher(binding.edOtp3, binding.edOtp4));
        binding.edOtp4.addTextChangedListener(new GenericTextWatcher(binding.edOtp4, null));

        binding.edOtp1.setOnKeyListener(new GenericKeyEvent(binding.edOtp1, null));
        binding.edOtp2.setOnKeyListener(new GenericKeyEvent(binding.edOtp2, binding.edOtp1));
        binding.edOtp3.setOnKeyListener(new GenericKeyEvent(binding.edOtp3, binding.edOtp2));
        binding.edOtp4.setOnKeyListener(new GenericKeyEvent(binding.edOtp4, binding.edOtp3));


        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OTPViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if (Utils.isNetworkConnected(getActivity())){
            if (v == binding.btnValidate){
                if (isOtpValid()){
                    String enteredOtp = binding.edOtp1.getText().toString() + binding.edOtp2.getText().toString()
                            + binding.edOtp3.getText().toString() + binding.edOtp4.getText().toString();
                    Toast.makeText(getActivity(), enteredOtp,Toast.LENGTH_SHORT).show();
                    nextFragment();

                }
            }
            if (v == binding.btnBack){
                previousFragment();
            }
        }else {
            Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
        }

    }

   private void nextFragment() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    private void previousFragment() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_OTPFragment_to_phoneLoginFragment);
    }

    // Validations
    private boolean isOtpValid() {
        if (binding.edOtp1.getText().toString().length() < 1
                && binding.edOtp2.getText().toString().length() < 1
                && binding.edOtp3.getText().toString().length() < 1
                && binding.edOtp4.getText().toString().length() < 1) {
            Snackbar.make(binding.getRoot(), "Please Enter OTP", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private class GenericTextWatcher implements TextWatcher {

        private View currentView;
        private View nextView;

        public GenericTextWatcher(View currentView, View nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            switch (currentView.getId()) {
                case R.id.ed_otp_1:
                case R.id.ed_otp_2:
                case R.id.ed_otp_3:
                    if (text.length() == 1)
                        nextView.requestFocus();
                    break;
            }
        }
    }

    class GenericKeyEvent implements View.OnKeyListener {

        private EditText currentView;
        private EditText previousView;

        public GenericKeyEvent(EditText currentView, EditText previousView) {
            this.currentView = currentView;
            this.previousView = previousView;
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.getId() != R.id.ed_otp_1 && currentView.getText().toString().isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView.setText(null);
                previousView.requestFocus();
                return true;
            }
            return false;
        }
    }


}