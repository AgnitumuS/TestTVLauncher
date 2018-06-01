package com.explame.testtvlauncher.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.customtvrecyclerview.CustomRecyclerView;
import com.explame.testtvlauncher.customtvrecyclerview.HomeTvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2018/5/10.
 * 自定义的RecyclerView，实现电视上面使用的效果
 */

public class TestRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private CustomRecyclerView mRecyclerView;
    private HomeTvAdapter mAdapter;
    private List<Integer> mData;
    private StaggeredGridLayoutManager mLayoutManager;
    public static final int LINE_NUM = 3;  //要显示的行数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
        setListener();
    }

    private void initView() {
        mContext = this;
        mRecyclerView = (CustomRecyclerView) findViewById(R.id.id_recycler_view);
        initData();
        mAdapter = new HomeTvAdapter(this, mData);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //设置布局管理器
        mLayoutManager = new StaggeredGridLayoutManager(LINE_NUM, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyOnItemClickListener());
    }

    private void initData() {
        mData = new ArrayList<Integer>();
        for (int i = 0; i < 350; i++) {
            mData.add(i);
        }
    }

    private void setListener() {

    }


    @Override
    public void onClick(View view) {

    }


    private class MyOnItemClickListener implements HomeTvAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mContext, "click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(mContext, "ItemLong:" + position, Toast.LENGTH_SHORT).show();
        }
    }
}
