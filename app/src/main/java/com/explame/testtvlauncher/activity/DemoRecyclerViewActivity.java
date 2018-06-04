package com.explame.testtvlauncher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.adapter.MyAdapter;
import com.explame.testtvlauncher.utils.StringUtils;

import org.evilbinary.tv.widget.BorderView;
import org.evilbinary.tv.widget.TvGridLayoutManagerScrolling;

/**
 * Created by admin on 2018/6/4.
 */

public class DemoRecyclerViewActivity extends AppCompatActivity {

    private BorderView border;
    private String classname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_recyclerview);
        border = new BorderView(this);
        border.setBackgroundResource(R.drawable.border_highlight);

        Intent intent = getIntent();
        classname = intent.getStringExtra("classname");
        if (!StringUtils.isBlank(classname)) {
            if (classname.equals("linerLayout")) {
                testRecyclerViewLinerLayout();
            } else {
                testRecyclerViewGridLayout();
            }
        }

    }

    private void testRecyclerViewLinerLayout() {
        //test linearlayout
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        border.attachTo(recyclerView);
        createData(recyclerView);
    }

    private void testRecyclerViewGridLayout() {
        //test grid
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridlayoutManager = new TvGridLayoutManagerScrolling(this, 4);
        gridlayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridlayoutManager);
        recyclerView.setFocusable(false);

        border.attachTo(recyclerView);
        createData(recyclerView);

    }


    private void createData(RecyclerView recyclerView) {
        //创建数据集
        String[] dataset = new String[100];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }
        // 创建Adapter，并指定数据集
        MyAdapter adapter = new MyAdapter(this, dataset);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
    }
}
