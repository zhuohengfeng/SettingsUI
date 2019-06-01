package com.ryan.settingsui.ui;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryan.settingsui.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<BaseItemData> mDatas = new ArrayList<>();
    private Context mContext;
    private int mSelectedPos = 0;

    public CustomAdapter(Context context, List<BaseItemData> datas) {
        mContext = context;
        clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public BaseItemData getItem(int position) {
        int size = mDatas.size();
        if (position >= 0 && position < size) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        mDatas.clear();
    }

    public void add(BaseItemData data) {
        mDatas.add(data);
    }

    public void addAll(List<BaseItemData> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int enableTextColor = mContext.getResources().getColor(R.color.enable_text, null);
        int disableTextColor = mContext.getResources().getColor(R.color.disable_text, null);
        BaseItemData item = mDatas.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT);

            convertView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.activity_wifidisplay_item, null);

            viewHolder = new ViewHolder();
            viewHolder.mRelativeLayout = convertView.findViewById(R.id.ll_wfd_row);
            viewHolder.mName = convertView.findViewById(R.id.tv_wfd_name);
            viewHolder.mContent = convertView.findViewById(R.id.tv_wfd_content);

            convertView.setLayoutParams(param);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mName.setText(item.getTitle());
        viewHolder.mContent.setText(item.getContent());

        if (mSelectedPos == position) {
            viewHolder.mRelativeLayout.setPadding(Constants.ITEM_ENABLE_PADDING_LEFT, 0, 0, 0);
            viewHolder.mName.setTextColor(enableTextColor);
            viewHolder.mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.enable_text_size));
        }
        else {
            viewHolder.mRelativeLayout.setPadding(Constants.ITEM_DISABLE_PADDING_LEFT, 0, 0, 0);
            viewHolder.mName.setTextColor(disableTextColor);
            viewHolder.mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.disable_text_size));
        }

        return convertView;
    }

    public void setSelected(int position) {
        mSelectedPos = position - Constants.SETTING_HEAD_COUNT;
    }

    public int getSelected() {
        return mSelectedPos;
    }


    class ViewHolder {
        RelativeLayout mRelativeLayout;
        TextView mName;
        TextView mContent;
    }

}

