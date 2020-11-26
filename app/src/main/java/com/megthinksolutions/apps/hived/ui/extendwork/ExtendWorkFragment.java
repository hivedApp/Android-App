package com.megthinksolutions.apps.hived.ui.extendwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.megthinksolutions.apps.hived.R;

public class ExtendWorkFragment extends Fragment {

    private ExtendWorkViewModel extendWorkViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        extendWorkViewModel =
                ViewModelProviders.of(this).get(ExtendWorkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_extend_work, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        extendWorkViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}