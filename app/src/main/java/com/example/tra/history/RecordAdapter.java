package com.example.tra.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tra.R;

import java.util.List;

public class RecordAdapter extends ArrayAdapter {
    //定义一个整型，记录‘显示记录的文本’的id
    private int resourceId;

    public RecordAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);   //实例化一个对象
        TextView showRecord = (TextView)view.findViewById(R.id.search_content_tv);
        String record = (String)getItem(position);     //获取当前的记录字符串
        showRecord.setText(record);
        return view;
    }
}



