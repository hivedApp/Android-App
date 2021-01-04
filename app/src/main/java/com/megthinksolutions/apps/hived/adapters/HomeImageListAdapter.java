package com.megthinksolutions.apps.hived.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.ItemSingleHomeImageBinding;
import com.megthinksolutions.apps.hived.models.HomeImageListModel;
import com.megthinksolutions.apps.hived.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeImageListAdapter extends RecyclerView.Adapter<HomeImageListAdapter.ViewHolder> {
    private ItemSingleHomeImageBinding binding;
    private Context context;
    private String categoryName;
    private HomeFragment homeFragment;
    private List<HomeImageListModel> homeImageListModelList = new ArrayList<>();

    public HomeImageListAdapter(Context context,
                                List<HomeImageListModel> homeImageListModelList) {
        this.context = context;
        this.categoryName = categoryName;
        this.homeImageListModelList = homeImageListModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_single_home_image, parent, false);

        return new HomeImageListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeImageListModel homeImageListModel = homeImageListModelList.get(position);
        if (homeImageListModel.getImageUrl() != null){
            Picasso.get()
                    .load(homeImageListModel.getImageUrl())
                    .placeholder(R.drawable.avatar1)
                    .error(R.drawable.avatar1)
                    .into(binding.imagePreview);
        }
    }

    @Override
    public int getItemCount() {
        return homeImageListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       private ItemSingleHomeImageBinding itemSingleHomeImageBinding;

        public ViewHolder(@NonNull ItemSingleHomeImageBinding itemSingleHomeImageBinding) {
            super(itemSingleHomeImageBinding.getRoot());
            this.itemSingleHomeImageBinding = itemSingleHomeImageBinding;
        }
    }
}
