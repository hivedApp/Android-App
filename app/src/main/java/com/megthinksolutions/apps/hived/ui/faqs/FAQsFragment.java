package com.megthinksolutions.apps.hived.ui.faqs;

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

public class FAQsFragment extends Fragment {

    private FAQsViewModel faQsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        faQsViewModel =
                ViewModelProviders.of(this).get(FAQsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_faqs, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        faQsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}