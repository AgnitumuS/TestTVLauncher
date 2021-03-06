
package com.explame.testtvlauncher.domain;

import android.content.Context;
import android.content.Intent;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.activity.TestRecyclerViewActivity;
import com.explame.testtvlauncher.activity.TestTvWidgetActivity;
import com.explame.testtvlauncher.app.AppUninstall;

import java.util.ArrayList;
import java.util.List;

public class FunctionModel {

    private int icon;
    private String id;
    private String name;
    private Intent mIntent;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    public static List<FunctionModel> getFunctionList(Context context) {
        List<FunctionModel> functionModels = new ArrayList<>();

        FunctionModel appUninstall = new FunctionModel();
        appUninstall.setName("应用卸载");
        appUninstall.setIcon(R.drawable.category_drama);
        appUninstall.setIntent(new Intent(context, AppUninstall.class));

        FunctionModel appUninstall1 = new FunctionModel();
        appUninstall1.setName("TestRecyclerView");
        appUninstall1.setIcon(R.drawable.category_comedy);
        appUninstall1.setIntent(new Intent(context, TestRecyclerViewActivity.class));

        FunctionModel appUninstall2 = new FunctionModel();
        appUninstall2.setName("TestTvWidget");
        appUninstall2.setIcon(R.drawable.category_action);
        appUninstall2.setIntent(new Intent(context, TestTvWidgetActivity.class));


        functionModels.add(appUninstall);
        functionModels.add(appUninstall1);
        functionModels.add(appUninstall2);
        return functionModels;
    }
}
