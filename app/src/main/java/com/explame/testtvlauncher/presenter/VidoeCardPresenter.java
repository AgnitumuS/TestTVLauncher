package com.explame.testtvlauncher.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.domain.MediaModel;

/**
 * Created by admin on 2018/5/9.
 */

public class VidoeCardPresenter extends Presenter {

    private Context mContext;
    private Drawable mDefaultCardImage;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.pic_default);
        ImageCardView cardView = new ImageCardView(mContext) {
            @Override
            public void setSelected(boolean selected) {
                super.setSelected(selected);
                int selected_background = mContext.getResources().getColor(R.color.feedback_bg_pre);
                int default_background = mContext.getResources().getColor(R.color.default_background);
                int color = selected ? selected_background : default_background;
                findViewById(R.id.info_field).setBackgroundColor(color);
            }
        };
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setMainImageDimensions(mContext.getResources().getDimensionPixelSize(R.dimen.dimen_300x), mContext.getResources().getDimensionPixelSize(R.dimen.dimen_176x));
        if (item instanceof MediaModel) {
            MediaModel mediaModel = (MediaModel) item;
            cardView.setTitleText(mediaModel.getTitle());
//            cardView.setContentText(mediaModel.getContent());
//            cardView.setBadgeImage(mContext.getResources().getDrawable(R.drawable.tvico));//设置可绘制的徽章图像。
            Glide.with(cardView.getMainImageView().getContext())
                    .load(mediaModel.getImageUrl())
                    .crossFade()
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
