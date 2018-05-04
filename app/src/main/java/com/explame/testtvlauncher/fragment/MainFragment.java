package com.explame.testtvlauncher.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.domain.FunctionModel;
import com.explame.testtvlauncher.domain.MediaModel;
import com.explame.testtvlauncher.presenter.FunctionCardPresenter;
import com.explame.testtvlauncher.presenter.ImgCardPresenter;
import com.explame.testtvlauncher.utils.LogUtils;

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
    }

    /**
     * 创建适配器并加载数据
     */
    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        addFunctionRow();
        addPhotoRow();



        setAdapter(rowsAdapter);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel) {
//                    MediaModel mediaModel = (MediaModel) item;
//                    Intent intent = new Intent(mContext, MediaDetailsActivity.class);
//                    intent.putExtra(MediaDetailsActivity.MEDIA, mediaModel);
//                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            (Activity) mContext,
//                            ((ImageCardView) itemViewHolder.view).getMainImageView(),
//                            MediaDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
//                    startActivity(intent, bundle);
                    LogUtils.i("row----->" + row.getId() + "|" + row.toString());
                } else if (item instanceof FunctionModel) {
                    LogUtils.i("row----->" + row.getId() + "|" + row.toString());
                    FunctionModel functionModel = (FunctionModel) item;
                    Intent intent = functionModel.getIntent();
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void addFunctionRow() {
        String headerName = getResources().getString(R.string.app_header_function_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new FunctionCardPresenter());
        List<FunctionModel> functionModels = FunctionModel.getFunctionList(mActivity);
        int cardCount = functionModels.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(functionModels.get(i));
        }
        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }


    private void addPhotoRow() {
        String headerName = getResources().getString(R.string.app_header_photo_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ImgCardPresenter());
        for (MediaModel mediaModel : MediaModel.getPhotoModels()) {
            listRowAdapter.add(mediaModel);
        }
        HeaderItem header = new HeaderItem(1, headerName);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }

}
