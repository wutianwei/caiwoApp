package com.caiwo.caiwoapp.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.caiwo.caiwoapp.R;

public class MainAdapter extends BaseAdapter {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainHolder holder;
        if (convertView == null) {
            holder = new MainHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_item, null);
            holder.adapter_main_time = convertView.findViewById(R.id.adapter_main_time);
            holder.adapter_main_img = convertView.findViewById(R.id.adapter_main_img);
            holder.adapter_yewuleixing = convertView.findViewById(R.id.adapter_yewuleixing);
            holder.adapter_edu = convertView.findViewById(R.id.adapter_edu);
            holder.adapter_diyawu = convertView.findViewById(R.id.adapter_diyawu);
            holder.adapter_diyashunwei = convertView.findViewById(R.id.adapter_diyashunwei);
            holder.adapter_genzong_state = convertView.findViewById(R.id.adapter_genzong_state);
            holder.adapter_detail_state = convertView.findViewById(R.id.adapter_detail_state);
            convertView.setTag(holder);
        } else {
            holder = (MainHolder) convertView.getTag();
        }
        return convertView;
    }

    public class MainHolder {
        private TextView adapter_main_time;
        private ImageView adapter_main_img;
        private TextView adapter_yewuleixing, adapter_edu, adapter_diyawu, adapter_diyashunwei;
        private TextView adapter_genzong_state, adapter_detail_state;
    }
}
