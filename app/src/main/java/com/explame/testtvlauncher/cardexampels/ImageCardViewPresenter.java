package com.explame.testtvlauncher.cardexampels;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.view.ContextThemeWrapper;

import com.bumptech.glide.Glide;
import com.explame.testtvlauncher.R;

/**
 * Created by admin on 2018/5/3.
 */

public class ImageCardViewPresenter extends AbstractCardPresenter<ImageCardView> {

    public ImageCardViewPresenter(Context context, int cardThemeResId) {
        super(new ContextThemeWrapper(context, cardThemeResId));
    }

    public ImageCardViewPresenter(Context context) {
        this(context, R.style.DefaultCardTheme);
    }

    @Override
    protected ImageCardView onCreateView() {
        ImageCardView imageCardView = new ImageCardView(getContext());
//        imageCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Clicked on ImageCardView", Toast.LENGTH_SHORT).show();
//            }
//        });
        return imageCardView;
    }

    @Override
    public void onBindViewHolder(Card card, final ImageCardView cardView) {
        cardView.setTag(card);
        cardView.setTitleText(card.getTitle());
        cardView.setContentText(card.getDescription());
        if (card.getLocalImageResourceName() != null) {
            int resourceId = getContext().getResources()
                    .getIdentifier(card.getLocalImageResourceName(),
                            "drawable", getContext().getPackageName());
            Glide.with(getContext())
                    .load(resourceId)
                    .asBitmap()
                    .into(cardView.getMainImageView());
        }
    }
}
