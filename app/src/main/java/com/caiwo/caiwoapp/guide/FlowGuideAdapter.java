package com.caiwo.caiwoapp.guide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.caiwo.caiwoapp.R;

import java.util.ArrayList;
import java.util.List;

public class FlowGuideAdapter extends BaseAdapter {
    
    private LayoutInflater inflater;
    private List<Integer> photos = new ArrayList<Integer>();
//    private List<String> bgcolor = new ArrayList<String>();

    private Context context;
    OnPhotoItemClickListener  listener;

    public FlowGuideAdapter(Context context, List<Integer> photos) {
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.photos = photos;
//        this.bgcolor = bgcolor;
    }
    
    public void setOnPhotoItemClickListener(OnPhotoItemClickListener l) {
        this.listener = l;
    }

    @Override
    public int getCount() {     
        return this.photos.size();
    }

    @Override
    public Integer getItem(int position) {
        return this.photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.flow_guide_item, null);
            viewHolder = new ViewHolder(convertView, context);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int res = photos.get(position);
        viewHolder.photo.setImageResource(res);
//        viewHolder.rootbg.setBackgroundColor(Color.parseColor(bgcolor.get(position)));
        viewHolder.photo.setOnClickListener(new OnPhotoClick(position));
        return convertView;
    }
    
    static class ViewHolder {
        ImageView photo;
        RelativeLayout rootbg;
        public ViewHolder(View view, final Context context) {
            photo = (ImageView) view.findViewById(R.id.photo1);
            rootbg = (RelativeLayout) view.findViewById(R.id.rootbg);
            view.setTag(this);
        }
    }
    
    public class OnPhotoClick implements View.OnClickListener {
        int position;
        public OnPhotoClick(int p){
            position = p;
        }
        @Override
        public void onClick(View arg0) {
            if (listener != null) {
                listener.onClick(position);
            }
        }
        
    }
    
    public static interface OnPhotoItemClickListener {
        public void onClick(int position);
    }

}
