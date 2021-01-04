package com.megthinksolutions.apps.hived.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.LayoutPostProfileBinding;
import com.megthinksolutions.apps.hived.models.PostProfileModel;
import com.megthinksolutions.apps.hived.models.ProductProfileModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostProfileAdapter extends RecyclerView.Adapter<PostProfileAdapter.Viewholder> {
    private LayoutPostProfileBinding binding;
    private List<PostProfileModel> postProfileModelList;
    private Context context;

    public PostProfileAdapter(List<PostProfileModel> postProfileModelList, Context context) {
        this.postProfileModelList = postProfileModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_post_profile, parent, false);

        return new PostProfileAdapter.Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        PostProfileModel profileModel = postProfileModelList.get(position);
        if (profileModel != null) {

            if (profileModel.getUserProfileUrl() != null) {
                Picasso.get()
                        .load(profileModel.getUserProfileUrl())
                        .placeholder(R.drawable.avatar1)
                        .error(R.drawable.avatar1)
                        .into(binding.imageProfile);
            }

            if (profileModel.getImages() != null) {
                Picasso.get()
                        .load(profileModel.getImages())
                        .placeholder(R.drawable.laptopn_sample)
                        .error(R.drawable.laptopn_sample)
                        .into(binding.imageProduct);
            }

            binding.txtProfileName.setText(profileModel.getUserName());
            binding.txtDateTime.setText(profileModel.getDatetime());
            binding.tvProductDesc.setText(profileModel.getProductDescription());

            binding.imageSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Click Setting!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return postProfileModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private LayoutPostProfileBinding binding;

        public Viewholder(@NonNull LayoutPostProfileBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
