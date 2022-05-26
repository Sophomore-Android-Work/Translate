package com.example.tra.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tra.R;

import java.util.List;

public class individuation_Adapter extends BaseAdapter {
    private List<individuation_data> list;
    private Context context;
    public individuation_Adapter(List<individuation_data> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder myHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.individuation_list_item,null,false);
            myHolder = new MyHolder();
            myHolder.imageView = view.findViewById(R.id.imageview);
            myHolder.textView = view.findViewById(R.id.textview);
            view.setTag(myHolder);
        }else{
            myHolder = (MyHolder) view.getTag();
        }
        myHolder.imageView.setBackgroundColor(list.get(i).getBackGroundColor());
        myHolder.textView.setText(list.get(i).getTitle());
        return view;
    }
    class MyHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
