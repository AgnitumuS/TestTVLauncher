package com.explame.testtvlauncher.activity;

import android.app.Activity;
import android.os.Bundle;

import com.explame.testtvlauncher.R;

/**
 * Created by admin on 2018/5/2.
 */

public class MediaDetailsActivity extends Activity {

    public static final String MEDIA = "Media";
    public static final String SHARED_ELEMENT_NAME = "hero";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
