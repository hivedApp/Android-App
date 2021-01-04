package com.megthinksolutions.apps.hived.ui.addNew;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.activity.ProductActivity;
import com.megthinksolutions.apps.hived.databinding.AddNewFragmentBinding;

public class AddNewFragment extends Fragment {
    private AddNewFragmentBinding binding;
    private AddNewViewModel mViewModel;

    public static AddNewFragment newInstance() {
        return new AddNewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("addNewValue","AddNewFragment");
        startActivity(intent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.add_new_fragment, container, false);


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddNewViewModel.class);
        // TODO: Use the ViewModel
    }

}