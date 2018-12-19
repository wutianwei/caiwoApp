package com.caiwo.caiwoapp.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caiwo.caiwoapp.R;

public class MessageXtAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageXtHolder holder;
        if (convertView == null) {
            holder = new MessageXtHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_messagext, null);
            holder.adapter_messagext_detail = convertView.findViewById(R.id.adapter_messagext_detail);
            holder.adapter_messagext_time = convertView.findViewById(R.id.adapter_messagext_time);
            holder.adapter_messagext_title = convertView.findViewById(R.id.adapter_messagext_title);
            convertView.setTag(holder);
        } else {
            holder = (MessageXtHolder) convertView.getTag();
        }
        return convertView;
    }

    public class MessageXtHolder {
        private TextView adapter_messagext_title;
        private TextView adapter_messagext_time;
        private TextView adapter_messagext_detail;

    }
}
