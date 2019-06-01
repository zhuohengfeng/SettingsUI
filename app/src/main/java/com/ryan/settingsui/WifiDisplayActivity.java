package com.ryan.settingsui;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.ryan.settingsui.ui.BaseItemData;
import com.ryan.settingsui.ui.Constants;
import com.ryan.settingsui.ui.CustomAdapter;
import com.ryan.settingsui.ui.CustomListView;

import java.util.ArrayList;

public class WifiDisplayActivity extends Activity {

    protected ArrayList<BaseItemData> mItemDataList;

    private CustomListView mCustomListView;
    private CustomAdapter mCustomAdapter;

    protected View mItemBorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wifidisplay);

        initData();
        initView();
    }

    protected void initData() {
        mItemDataList = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            BaseItemData item = new BaseItemData("设备"+i, "未连接");
            mItemDataList.add(item);
        }
    }

    protected void initView() {
        // 初始化ListView
        mCustomListView = findViewById(R.id.list_wfd_devices);
        mCustomListView.initView(mItemDataList.size());
        mCustomListView.setDivider(null);
        mCustomListView.setCacheColorHint(Color.TRANSPARENT);
        mCustomListView.setOnItemClickListener(mItemClickListener);
        mCustomListView.setOnItemSelectedListener(mItemSelectedListener);
        mCustomListView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onLayoutReady();
                        mCustomListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        mCustomAdapter = new CustomAdapter(this, mItemDataList);
        mCustomListView.setAdapter(mCustomAdapter);

        mItemBorder = findViewById(R.id.img_border_item);
    }


    protected AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //enterNewActivity(position - Constants.SETTING_HEAD_COUNT);
            Log.d("zhf_wfd", "onItemClick position="+(position - Constants.SETTING_HEAD_COUNT));
        }
    };

    protected AdapterView.OnItemSelectedListener mItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            synchronized(this){
                mCustomListView.scrollTo(mCustomListView.getScrollX(), 0);
                if (view != null) {
                    //此处选中时调用ListView的移动动画，计算偏移值：通过选中View的top和第三项的高度来计算
                    mCustomListView.smoothScrollBy(view.getTop()
                            - Constants.SETTING_HEAD_COUNT * Constants.SETTING_HEAD_ITEM_HEIGHT
                            - mCustomListView.getScrollY(), 250);
                }
                mCustomAdapter.setSelected(position);
                mCustomAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    protected void onLayoutReady() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mItemBorder.getLayoutParams();
        params.setMargins(mItemBorder.getLeft(),
                mItemBorder.getTop()+
                        Constants.SETTING_HEAD_COUNT * Constants.SETTING_HEAD_ITEM_HEIGHT - (Constants.SETTING_BORDER_ITEM_HEIGHT - Constants.SETTING_ITEM_HEIGHT)/2,
                mItemBorder.getRight(),
                mItemBorder.getBottom());
        mItemBorder.setLayoutParams(params);
    }




}
