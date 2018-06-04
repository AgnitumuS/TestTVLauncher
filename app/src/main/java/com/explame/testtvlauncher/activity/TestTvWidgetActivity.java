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

    private TextView textView1;//RecyclerViewLinerLayout示例
    private TextView textView2;//RecyclerViewGridLayout示例
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
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        textView4.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
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
            case R.id.textView1:
                Intent intent1 = new Intent(mActivity,
                        DemoRecyclerViewActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("classname", "linerLayout");
                startActivity(intent1);
                break;
            case R.id.textView2:
                Intent intent2 = new Intent(mActivity,
                        DemoRecyclerViewActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("classname", "gridlayout");
                startActivity(intent2);
                break;
        }
    }
}
