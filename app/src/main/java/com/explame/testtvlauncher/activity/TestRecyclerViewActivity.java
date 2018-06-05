package com.explame.testtvlauncher.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.adapter.CustomGridRecyclerAdapter;
import com.explame.testtvlauncher.adapter.CustomLineRecyclerAdapter;
import com.explame.testtvlauncher.customtvrecyclerview.CustomGridRecyclerView;
import com.explame.testtvlauncher.customtvrecyclerview.CustomLineRecyclerView;

import org.evilbinary.tv.widget.TvGridLayoutManagerScrolling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2018/5/10.
 * 自定义的RecyclerView，实现电视上面使用的效果
 */

public class TestRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private CustomGridRecyclerView id_recycler_grid;
    private CustomGridRecyclerAdapter gridrecycleradapter;
    private List<Integer> mData;
    private StaggeredGridLayoutManager mLayoutManager;
    public static final int LINE_NUM = 3;  //要显示的行数

    private CustomLineRecyclerView id_recycler_line;
    private CustomLineRecyclerAdapter linerecycleradapter;
    private LinearLayoutManager linearlayoutmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
        setListener();
    }

    private GridLayoutManager gridLayoutManager;

    private void initView() {
        mContext = this;
        id_recycler_grid = (CustomGridRecyclerView) findViewById(R.id.id_recycler_grid);
        initData();
        gridrecycleradapter = new CustomGridRecyclerAdapter(this, mData);
        id_recycler_grid.setItemAnimator(new DefaultItemAnimator());

        id_recycler_line = (CustomLineRecyclerView) findViewById(R.id.id_recycler_line);
        linerecycleradapter = new CustomLineRecyclerAdapter(this, mData);
        id_recycler_line.setItemAnimator(new DefaultItemAnimator());

        //设置布局管理器
        linearlayoutmanager = new LinearLayoutManager(this);
        linearlayoutmanager.setAutoMeasureEnabled(false);
        id_recycler_line.setLayoutManager(linearlayoutmanager);
        id_recycler_line.setAdapter(linerecycleradapter);
        linerecycleradapter.setOnItemClickListener(new LineOnItemClickListener());

        gridLayoutManager = new TvGridLayoutManagerScrolling(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        id_recycler_grid.setLayoutManager(gridLayoutManager);
        id_recycler_grid.setAdapter(gridrecycleradapter);
        gridrecycleradapter.setOnItemClickListener(new GridOnItemClickListener());
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


    private class GridOnItemClickListener implements CustomGridRecyclerAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mContext, "Grid_click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(mContext, "Grid_ItemLong:" + position, Toast.LENGTH_SHORT).show();
        }
    }

    private class LineOnItemClickListener implements CustomLineRecyclerAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mContext, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(mContext, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }
    }

}
