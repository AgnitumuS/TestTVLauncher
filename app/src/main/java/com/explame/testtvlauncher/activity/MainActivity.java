package com.explame.testtvlauncher.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.domain.MediaModel;
import com.explame.testtvlauncher.widget.ImgCardPresenter;

public class MainActivity extends Activity {

    private Context mContext;

    protected BrowseFragment mBrowseFragment;
    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private ArrayObjectAdapter rowsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mContext = this;
        mBrowseFragment = (BrowseFragment) getFragmentManager().findFragmentById(R.id.browse_fragment);

        mBrowseFragment.setHeadersState(BrowseFragment.HEADERS_DISABLED);
        mBrowseFragment.setTitle(getString(R.string.app_name));

        prepareBackgroundManager();
        buildRowsAdapter();

        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化监听
     */
    public void initListener() {

    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(this);
        mBackgroundManager.attach(this.getWindow());
        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        addPhotoRow();

        mBrowseFragment.setAdapter(rowsAdapter);
        mBrowseFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof MediaModel) {
                    MediaModel mediaModel = (MediaModel) item;
                    Intent intent = new Intent(mContext, MediaDetailsActivity.class);
                    intent.putExtra(MediaDetailsActivity.MEDIA, mediaModel);
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) mContext,
                            ((ImageCardView) itemViewHolder.view).getMainImageView(),
                            MediaDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                    startActivity(intent, bundle);
                }
            }
        });
    }

    private void addPhotoRow() {
        String headerName = getResources().getString(R.string.app_header_photo_name);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ImgCardPresenter());
        for (MediaModel mediaModel : MediaModel.getPhotoModels()) {
            listRowAdapter.add(mediaModel);
        }
        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
    }


}
