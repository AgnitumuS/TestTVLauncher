package com.explame.testtvlauncher.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.activity.MediaDetailsActivity;
import com.explame.testtvlauncher.domain.MediaModel;
import com.explame.testtvlauncher.presenter.MediaDetailsDescriptionPresenter;

/**
 * @author jacky
 * @version v1.0
 * @since 16/8/28
 */
public class MediaDetailsFragment extends DetailsFragment {

    private ArrayObjectAdapter mRowsAdapter;
    private MediaModel mMediaModel;
    private Context mContext;
    private static final int ACTION_WATCH_TRAILER = 1;

    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        mMediaModel = getActivity().getIntent().getParcelableExtra(MediaDetailsActivity.MEDIA);

        prepareBackgroundManager();
        buildDetails();
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void buildDetails() {
        ClassPresenterSelector selector = new ClassPresenterSelector();
        FullWidthDetailsOverviewRowPresenter rowPresenter = new FullWidthDetailsOverviewRowPresenter(
                new MediaDetailsDescriptionPresenter(),
                new DetailsOverviewLogoPresenter());

        rowPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
//                if (action.getId() == ACTION_WATCH_TRAILER) {
//                    Intent intent = new Intent(getActivity(), VideoActivity.class);
//                    intent.putExtra(VideoActivity.VIDEO, mMediaModel);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
//                }
                Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        selector.addClassPresenter(DetailsOverviewRow.class, rowPresenter);
        selector.addClassPresenter(ListRow.class, new ListRowPresenter());
        mRowsAdapter = new ArrayObjectAdapter(selector);

        final DetailsOverviewRow detailsOverview = new DetailsOverviewRow(mMediaModel);
        detailsOverview.addAction(new Action(1, "香蕉激光枪"));
        detailsOverview.addAction(new Action(2, "巨人之拳"));
        Glide.with(mContext)
                .load(mMediaModel.getImageUrl())
                .asBitmap()
                .listener(new RequestListener<String, Bitmap>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        detailsOverview.setImageBitmap(mContext, resource);
                        return true;
                    }
                })
                .into(-1, -1);
        updateBackground(mMediaModel.getImageUrl());
//        SparseArrayObjectAdapter adapter = new SparseArrayObjectAdapter();
//        if (!mMediaModel.getVideoUrl().isEmpty()) {
//            adapter.set(ACTION_WATCH_TRAILER, new Action(ACTION_WATCH_TRAILER, "播放"));
//        }
//        detailsOverview.setActionsAdapter(adapter);
        mRowsAdapter.add(detailsOverview);
        setAdapter(mRowsAdapter);
    }

    private void updateBackground(String uri) {
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(new SimpleTarget<Bitmap>(mMetrics.widthPixels, mMetrics.heightPixels) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBackgroundManager.setBitmap(resource);
                    }
                });
    }

    @Override
    public void onStop() {
        mBackgroundManager.release();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class GridItemPresenter extends Presenter {

        @Override
        public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.dimen_300x), mContext.getResources().getDimensionPixelSize(R.dimen.dimen_176x)));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }


}