package com.megthinksolutions.apps.hived.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.databinding.LayoutHomeListBinding;
import com.megthinksolutions.apps.hived.responseModel.HomeListResponse;
import com.megthinksolutions.apps.hived.responseModel.ProductPostReviewResponse;
import com.megthinksolutions.apps.hived.ui.home.HomeFragment;
import com.megthinksolutions.apps.hived.utils.GlideApp;
import com.megthinksolutions.apps.hived.utils.GlideRequest;
import com.megthinksolutions.apps.hived.utils.MySpannable;
import com.megthinksolutions.apps.hived.utils.PaginationAdapterCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;
    private HomeFragment homeFragment;
    private String errorMsg;

    private Context context;
    private List<ProductPostReviewResponse> productPostReviewResponseList;

    public HomeListAdapter2(Context context) {
        this.context = context;
        //this.mCallback = callback;
        productPostReviewResponseList = new ArrayList<>();
    }

    public List<ProductPostReviewResponse> getMovies() {
        return productPostReviewResponseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.layout_home_list, parent, false);
                viewHolder = new HomeListAdapter2.MovieVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new HomeListAdapter2.LoadingVH(viewLoading);
                break;
//            case HERO:
//                View viewHero = inflater.inflate(R.layout.item_hero, parent, false);
//                viewHolder = new HomeListAdapter2.HeroVH(viewHero);
//                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductPostReviewResponse result = productPostReviewResponseList.get(position);
        if (result != null) {

            switch (getItemViewType(position)) {
                case ITEM:
                    HomeListAdapter2.MovieVH movieVH = (HomeListAdapter2.MovieVH) holder;
                    movieVH.tvTitle.setText(result.getTitle());
                   // movieVH.tvSubTitle.setText(result.getSubTitle());
                    if (result.getUserName() != null){
                        movieVH.tvReviewerUserName.setText(result.getUserName());
                    }else {
                        movieVH.tvReviewerUserName.setText("No User Name");

                    }
                    movieVH.tvRating.setText(Integer.parseInt(result.getRating()) + "/5 Rating");
                    movieVH.tvDateTime.setText(result.getDatetime());

                    if (result.getProfileUrl() != null) {
                        Picasso.get()
                                .load(result.getProfileUrl())
                                .placeholder(R.drawable.avatar1)
                                .error(R.drawable.avatar1)
                                .into(movieVH.imageReviewUser);
                    }

                    String subText = result.getSubTitle();
                    movieVH.tvSubTitle.setText(subText);
                    makeTextViewResizable(movieVH.tvSubTitle, 2, "See More", true);
                   // movieVH.tvSubTitle.setText(subText);

                    movieVH.rating.setRating(Integer.parseInt(result.getRating()));

                    movieVH.rvScrollImage.setAdapter(
                            new HomeImageListAdapter(context, result.getImages()));
                    movieVH.rvScrollImage.setLayoutManager(new
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    movieVH.rvScrollImage.setHasFixedSize(true);

                    // load movie thumbnail
//                    loadImage(result.getProfileUrl())
//                            .listener(new RequestListener<Drawable>() {
//                                @Override
//                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                    // TODO: 2/16/19 Handle failure
//                                    movieVH.mProgress.setVisibility(View.GONE);
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                    // image ready, hide progress now
//                                    movieVH.mProgress.setVisibility(View.GONE);
//                                    return false;   // return false if you want Glide to handle everything else.
//                                }
//                            })
//                            .into(movieVH.imageReviewUser);

                    break;


                case LOADING:
                    HomeListAdapter2.LoadingVH loadingVH = (HomeListAdapter2.LoadingVH) holder;

                    if (retryPageLoad) {
                        loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                        loadingVH.mProgressBar.setVisibility(View.GONE);

                        loadingVH.mErrorTxt.setText(
                                errorMsg != null ?
                                        errorMsg :
                                        context.getString(R.string.error_msg_unknown));

                    } else {
                        loadingVH.mErrorLayout.setVisibility(View.GONE);
                        //loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                        loadingVH.mProgressBar.setVisibility(View.GONE);

                    }
                    break;

            }

        } else {
            Toast.makeText(context, "Adapter Item is null", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public int getItemCount() {
        return productPostReviewResponseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM;
        } else {
            return (position == productPostReviewResponseList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }
    }

    private GlideRequest<Drawable> loadImage(@NonNull String posterPath) {
        return GlideApp
                .with(context)
                .load(posterPath)
                .centerCrop();
    }

    public void add(ProductPostReviewResponse r) {
        productPostReviewResponseList.add(r);
        notifyItemInserted(productPostReviewResponseList.size() - 1);
    }

    public void addAll(List<ProductPostReviewResponse> reviewResponseList) {
        for (ProductPostReviewResponse result : reviewResponseList) {
            add(result);
        }
    }

    public void remove(ProductPostReviewResponse r) {
        int position = productPostReviewResponseList.indexOf(r);
        if (position > -1) {
            productPostReviewResponseList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ProductPostReviewResponse());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productPostReviewResponseList.size() - 1;
        ProductPostReviewResponse result = getItem(position);

        if (result != null) {
            productPostReviewResponseList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ProductPostReviewResponse getItem(int position) {
        return productPostReviewResponseList.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(productPostReviewResponseList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    /**
     * Header ViewHolder
     */
    protected class HeroVH extends RecyclerView.ViewHolder {
        private TextView mMovieTitle;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;

        public HeroVH(View itemView) {
            super(itemView);

//            mMovieTitle = itemView.findViewById(R.id.movie_title);
//            mMovieDesc = itemView.findViewById(R.id.movie_desc);
//            mYear = itemView.findViewById(R.id.movie_year);
//            mPosterImg = itemView.findViewById(R.id.movie_poster);
        }
    }

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        TextView tvDateTime, tvTitle, tvSubTitle, tvRating, tvReviewerUserName;
        ImageView imageShare;
        CircleImageView imageReviewUser;
        private ProgressBar mProgress;
        RatingBar rating;
        RecyclerView rvScrollImage;

        public MovieVH(View itemView) {
            super(itemView);

            tvDateTime = itemView.findViewById(R.id.tv_date_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubTitle = itemView.findViewById(R.id.tv_sub_title);
            rvScrollImage = itemView.findViewById(R.id.rv_scroll_image);
            rating = itemView.findViewById(R.id.rating);
            tvRating = itemView.findViewById(R.id.tv_rating);
            imageShare = itemView.findViewById(R.id.img_share);
            tvReviewerUserName = itemView.findViewById(R.id.tv_reviewer_user_name);
            imageReviewUser = itemView.findViewById(R.id.image_review_user);


        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    //mCallback.retryPageLoad();

                    break;
            }
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
