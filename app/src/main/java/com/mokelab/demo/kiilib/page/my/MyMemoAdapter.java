package com.mokelab.demo.kiilib.page.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mokelab.demo.kiilib.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.fkmsoft.libs.kiilib.entities.android.AndroidKiiObject;

/**
 * Adapter for my memo
 */
class MyMemoAdapter extends ArrayAdapter<AndroidKiiObject> {
    private final LayoutInflater mInflater;

    public MyMemoAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_my_memo, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        AndroidKiiObject item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mTitleText.setText(item.optString("title", ""));
        holder.mDescText.setText(item.optString("desc", ""));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.text_title)TextView mTitleText;
        @Bind(R.id.text_desc)TextView mDescText;

        ViewHolder(View root) {
            ButterKnife.bind(this, root);
        }
    }
}
