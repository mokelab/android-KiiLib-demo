package com.mokelab.demo.kiilib.page.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mokelab.demo.kiilib.R;

/**
 * Adapter for menu
 */
public class MainMenuAdapter extends ArrayAdapter<Void> {
    private final LayoutInflater mInflater;

    public MainMenuAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_main_menu, null);
        }
        ImageView iconView = (ImageView) convertView.findViewById(R.id.icon);
        TextView labelView = (TextView) convertView.findViewById(R.id.text_label);
        switch (position) {
        case 0:
            iconView.setImageResource(R.drawable.ic_description_black_48dp);
            labelView.setText(R.string.menu_label_my_memo);
            break;
        case 1:
            iconView.setImageResource(R.drawable.ic_group_black_48dp);
            labelView.setText(R.string.menu_label_group);
            break;
        }

        return convertView;
    }
}
