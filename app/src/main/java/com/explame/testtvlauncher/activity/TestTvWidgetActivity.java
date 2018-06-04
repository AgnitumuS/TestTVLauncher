package com.explame.testtvlauncher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.explame.testtvlauncher.R;

import org.evilbinary.tv.widget.BorderView;

/**
 * Created by XQ on 2018/6/1.
 * tv常用效果控件，包括焦点、边框处理等
 */

public class TestTvWidgetActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity mActivity;


    private RelativeLayout main;
    private BorderView border;

    private TextView textView4;//ListView示例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testtvwidget);
        mActivity = TestTvWidgetActivity.this;
        main = (RelativeLayout) findViewById(R.id.main);
        BorderView border = new BorderView(mActivity);
        border.setBackgroundResource(R.drawable.border_highlight);
        border.attachTo(main);

        textView4 = findViewById(R.id.textView4);

        textView4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView4:
                Intent intent = new Intent(mActivity,
                        DemoListViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
