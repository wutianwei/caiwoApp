package com.caiwo.caiwoapp.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caiwo.caiwoapp.R;

public class MessageAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 15;
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
        MessageHolder holder;
        if (convertView == null) {
            holder = new MessageHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message, null);
            holder.adapter_message_icon = convertView.findViewById(R.id.adapter_message_icon);
            holder.message_unread_num = convertView.findViewById(R.id.message_unread_num);
            holder.adapter_message_name = convertView.findViewById(R.id.adapter_message_name);
            holder.adapter_message_unread = convertView.findViewById(R.id.adapter_message_unread);
            convertView.setTag(holder);
        } else {
            holder = (MessageHolder) convertView.getTag();
        }

        return convertView;
    }

    public class MessageHolder {
        private TextView message_unread_num;
        private ImageView adapter_message_icon;
        private TextView adapter_message_name;
        private TextView adapter_message_unread;
    }
}
