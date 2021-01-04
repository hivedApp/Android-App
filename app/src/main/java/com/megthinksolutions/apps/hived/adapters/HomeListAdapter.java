package com.megthinksolutions.apps.hived.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.LayoutHomeListBinding;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;
import com.megthinksolutions.apps.hived.responseModel.ProductPostReviewResponse;
import com.megthinksolutions.apps.hived.ui.home.HomeFragment;
import com.megthinksolutions.apps.hived.utils.MySpannable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeListAdapter extends PagedListAdapter<HomeListResponse,HomeListAdapter.ItemViewHolder> {
    private LayoutHomeListBinding binding;
    private Context context;
    private HomeFragment homeFragment;
    private List<HomeListResponse> productPostReviewResponseList;

    public HomeListAdapter(Context context, HomeFragment homeFragment, List<HomeListResponse> productPostReviewResponses) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.homeFragment = homeFragment;
        this.productPostReviewResponseList = productPostReviewResponses;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       binding = DataBindingUtil.inflate(LayoutInflater.from(
                         parent.getContext()), R.layout.layout_home_list, parent, false);

        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//        ProductPostReviewResponse productPostReviewResponse = getItem(position);
//
//        if (productPostReviewResponse != null){
//
//            binding.rvScrollImage.setAdapter(new HomeImageListAdapter(context,
//                    productPostReviewResponse.getImages(),
//                    homeFragment));
//            binding.rvScrollImage.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//            binding.rvScrollImage.setHasFixedSize(true);
//
//            binding.tvDateTime.setText(productPostReviewResponse.getDatetime());
//            binding.tvTitle.setText(productPostReviewResponse.getTitle());
//            binding.rating.setRating(Integer.parseInt(productPostReviewResponse.getRating()));
//            binding.tvRating.setText(Integer.parseInt(productPostReviewResponse.getRating())+"/5 Rating");
//            binding.tvReviewerName.setText(productPostReviewResponse.getUserName());
//
//            if (productPostReviewResponse.getProfileUrl() != null){
//                Picasso.get()
//                        .load(productPostReviewResponse.getProfileUrl())
//                        .placeholder(R.drawable.avatar1)
//                        .error(R.drawable.avatar1)
//                        .into(binding.imageReviewUser);
//            }
//
//            String subText = productPostReviewResponse.getSubTitle();
//            binding.tvSubTitle.setText(subText);
//            makeTextViewResizable(binding.tvSubTitle, 2, "See More", true);
//
//
//        }else {
//            Toast.makeText(context, "Adapter Item is null", Toast.LENGTH_LONG).show();
//
//        }

    }

    private static DiffUtil.ItemCallback<HomeListResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<HomeListResponse>() {
                @Override
                public boolean areItemsTheSame(@NonNull HomeListResponse oldItem, @NonNull HomeListResponse newItem) {
                    return oldItem.getPaginationKeyValue1() == newItem.getPaginationKeyValue1();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull HomeListResponse oldItem, @NonNull HomeListResponse newItem) {
                    return oldItem.equals(newItem);
                }
            };



//    @Override
//    public int getItemCount() {
//        return homeListResponseList.size();
//    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
          LayoutHomeListBinding layoutHomeListBinding;

        public ItemViewHolder(@NonNull LayoutHomeListBinding layoutHomeListBinding) {
            super(layoutHomeListBinding.getRoot());
            this.layoutHomeListBinding = layoutHomeListBinding;
        }
    }

    private void makeTextViewResizable(TextView tv, int maxLine, String expandText, boolean viewMore) {
        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }

        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });


      }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {

            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 2, "...See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

}
