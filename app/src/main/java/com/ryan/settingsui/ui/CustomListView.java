package com.ryan.settingsui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CustomListView extends ListView {
    private static final String TAG = "CustomListView";

    private Context mContext;
    private CustomAdapter mAdapter;

    public FrameLayout mFooterViewGroup;

    public CustomListView(Context context) {
        super(context);
        initDate(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDate(context);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDate(context);
    }

    private void initDate(Context context) {
        mContext = context;
        mFooterViewGroup = new FrameLayout(mContext);
    }

    public void initView(int count) {
        for (int i = 0; i < Constants.SETTING_HEAD_COUNT; i++) {
            View headerView = new View(mContext);
            LayoutParams headerLp = new LayoutParams(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_HEAD_ITEM_HEIGHT);
            headerView.setLayoutParams(headerLp);
            addHeaderView(headerView, null, false);
        }

        for (int i = 0; i < count + 1; i++) {
            LayoutParams footerLp = new LayoutParams(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT);
            if (i == 0) {
                mFooterViewGroup.setLayoutParams(footerLp);
                addFooterView(mFooterViewGroup, null, false);
            }
            else {
                View footerView = new View(mContext);
                footerView.setLayoutParams(footerLp);
                addFooterView(footerView, null, false);
            }
        }
        setPadding(0, 0, 0, 0);
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (CustomAdapter)adapter;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        View view = this.getSelectedView();
        int pos = mAdapter.getSelected();
        int count = mAdapter.getCount();

        if (/*action == KeyEvent.ACTION_DOWN && */view != null) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                int top = view.getTop();
                if ((Constants.SETTING_HEAD_COUNT * Constants.SETTING_HEAD_ITEM_HEIGHT) != top) {
                    return false;
                }
                if (pos <= 0
                        && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    return false;
                }
                if (pos >= count - 1
                        && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    return false;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
