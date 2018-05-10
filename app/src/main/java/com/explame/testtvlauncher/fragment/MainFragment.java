package com.explame.testtvlauncher.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.activity.MediaDetailsActivity;
import com.explame.testtvlauncher.app.AppCardPresenter;
import com.explame.testtvlauncher.app.AppDataManage;
import com.explame.testtvlauncher.app.AppModel;
import com.explame.testtvlauncher.domain.FunctionModel;
import com.explame.testtvlauncher.domain.IconHeaderItem;
import com.explame.testtvlauncher.domain.MediaModel;
import com.explame.testtvlauncher.presenter.FunctionCardPresenter;
import com.explame.testtvlauncher.presenter.HeaderItemPresenter;
import com.explame.testtvlauncher.presenter.ImgCardPresenter;
import com.explame.testtvlauncher.presenter.VidoeCardPresenter;
import com.explame.testtvlauncher.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/3.
 */

public class MainFragment extends BrowseFragment {

    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter rowsAdapter;
    private Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareBackgroundManager();
        setupUIElements();
        buildRowsAdapter();
    }

    /**
     * 准备背景管理器
     */
    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.background_canyon);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        mBackgroundManager.setDrawable(mDefaultBackground);
    }

    /**
     * 设置UI元素
     */
    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));//设置标题
        setBadgeDrawable(getActivity().getResources().getDrawable(
                R.drawable.title_android_tv));//展示在标题栏上的图片(图片会隐藏标题)
        setHeadersState(HEADERS_ENABLED);//设置侧边栏显示状态 enabled 可见
        setHeadersTransitionOnBackEnabled(true);//暂不知道方法具体作用
        setBrandColor(getResources().getColor(R.color.fastlane_background));//设置快速通道（侧边栏）背景
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque)); //搜索图标颜色
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new HeaderItemPresenter();
            }
        });
    }

    /**
     * 创建适配器并加载数据
     */
    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        addFunctionRow();
        addPhotoRow();
        addAppRow();
        addVideoRow();


        setAdapter(rowsAdapter);
        /**
         * 点击监听
         */
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel) {
                    LogUtils.i("row----->" + row.getId() + "|" + row.toString());
                    MediaModel mediaModel = (MediaModel) item;
                    Intent intent = new Intent(mActivity, MediaDetailsActivity.class);
                    intent.putExtra(MediaDetailsActivity.MEDIA, mediaModel);
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            mActivity,
                            ((ImageCardView) itemViewHolder.view).getMainImageView(),
                            MediaDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                    startActivity(intent, bundle);
                } else if (item instanceof FunctionModel) {
                    LogUtils.i("row----->" + row.getId() + "|" + row.toString());
                    FunctionModel functionModel = (FunctionModel) item;
                    Intent intent = functionModel.getIntent();
                    if (intent != null) {
                        startActivity(intent);
                    }
                } else if (item instanceof AppModel) {
                    LogUtils.i("row----->" + row.getId() + "|" + row.toString());
                    AppModel appBean = (AppModel) item;
                    Intent launchIntent = mActivity.getPackageManager().getLaunchIntentForPackage(
                            appBean.getPackageName());
                    if (launchIntent != null) {
                        mActivity.startActivity(launchIntent);
                    }
                }
            }
        });
        /**
         *选择监听
         */
        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel) {//当选择MediaModel的时候，改变背景图片
                    MediaModel mediaModel = (MediaModel) item;
                    int width = mMetrics.widthPixels;
                    int height = mMetrics.heightPixels;
                    Glide.with(mActivity)
                            .load(mediaModel.getImageUrl())
                            .asBitmap()
                            .centerCrop()
                            .into(new SimpleTarget<Bitmap>(width, height) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    mBackgroundManager.setBitmap(resource);
                                }
                            });
                } else {
                    mBackgroundManager.setDrawable(mDefaultBackground);
                }
            }
        });
    }

    private void addFunctionRow() {
        String headerName = getResources().getString(R.string.app_header_function_name);
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(0, headerName, R.mipmap.ic_launcher_round);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new FunctionCardPresenter());
        List<FunctionModel> functionModels = FunctionModel.getFunctionList(mActivity);
        int cardCount = functionModels.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(functionModels.get(i));
        }
//        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
    }


    private void addPhotoRow() {
        String headerName = getResources().getString(R.string.app_header_photo_name);
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(1, headerName, R.mipmap.ic_launcher_round);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ImgCardPresenter());
        for (MediaModel mediaModel : MediaModel.getPhotoModels()) {
            listRowAdapter.add(mediaModel);
        }
//        HeaderItem header = new HeaderItem(1, headerName);
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
    }

    /**
     * APP展示
     */
    private void addAppRow() {
        String headerName = getResources().getString(R.string.app_header_app_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter());
        ArrayList<AppModel> appDataList = new AppDataManage(mActivity).getLaunchAppList();
        int cardCount = appDataList.size();
        for (AppModel appModel : appDataList) {
            listRowAdapter.add(appModel);
        }
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(2, headerName, R.mipmap.ic_launcher_round);
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
    }

    /**
     * 视频row
     */
    private void addVideoRow() {
        String headerName = getResources().getString(R.string.app_header_video_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new VidoeCardPresenter());
        for (MediaModel mediaModel : MediaModel.getVideoModels()) {
            listRowAdapter.add(mediaModel);
        }
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(3, headerName, R.mipmap.ic_launcher_round);
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
    }


    private void addHeaderRow() {
        /* GridItemPresenter */
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(0, "GridItemPresenter", R.mipmap.ic_launcher_round);
        GridItemPresenter gridItemPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridItemPresenter);
        gridRowAdapter.add("ITEM 1");
        gridRowAdapter.add("ITEM 2");
        gridRowAdapter.add("ITEM 3");
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));
    }

    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    private class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
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

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("----->onResume");
    }
}
