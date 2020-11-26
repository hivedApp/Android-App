package com.megthinksolutions.apps.hived.ui.divvideo;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.megthinksolutions.apps.hived.R;

public class DivVideoFragment extends Fragment {

    private DivVideoViewModel mViewModel;

    public static DivVideoFragment newInstance() {
        return new DivVideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.div_video_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DivVideoViewModel.class);
        // TODO: Use the ViewModel
    }

}