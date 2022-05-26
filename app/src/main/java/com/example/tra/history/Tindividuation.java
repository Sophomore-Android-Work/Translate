package com.example.tra.history;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tra.Constant;
import com.example.tra.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tindividuation extends AppCompatActivity {
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individuation);
        listview = findViewById(R.id.individuation_list);
        List<individuation_data> list =createIData();
        individuation_Adapter adapter = new individuation_Adapter(list,Tindividuation.this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(Tindividuation.this)
                        .setTitle("切 换 主 题")
                        .setMessage("确定要修改主题为：" + list.get(position).getTitle()+"?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.e("asdasdasdasd",list.get(position).getBackGroundColor()+"");
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            int SchemeId = list.get(position).getBackGroundColor();
//                                            Log.e("!!!","gggggggggggggggg");
//                                            URL url=new URL(Constant.URL_Update + "?userAccount=" + Final_user.getUser_Account() + "&SchemeId=" + SchemeId);
//                                            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
//                                            Log.e("!!!","11111112222222");
//                                            conn.setRequestMethod("GET");
//                                            BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                                            String result="";
//                                            String line=null;
//                                            while((line=reader.readLine())!=null){
//                                                result+=line+"\n";
//                                            }
//                                            Log.d("JSON","RESULT"+result);
//                                            if(!"".equals(result)){
////                                                Mydict mydict= JSON.parseObject(result, Mydict.class);
////                                                Message message=handler.obtainMessage();
////                                                Bundle u=new Bundle();
////                                                u.putString("word",mydict.getWord());
////                                                u.putString("explain",mydict.getExplain());
////                                                message.setData(u);
////                                                message.sendToTarget();
//                                            }
//                                            reader.close();
//                                            conn.disconnect();
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }).start();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                dialog = builder.create();
                dialog.show();
            }
        });
    }
    public List<individuation_data> createIData(){
        List<individuation_data> list =new ArrayList<individuation_data>();
        individuation_data data = new individuation_data();
        data.setTitle("默认");
        data.setBackGroundColor(0);
        list.add(data);
        data = new individuation_data();
        data.setBackGroundColor(Color.parseColor("#FFB6C1"));
        data.setTitle("浅粉色");
        list.add(data);
        data = new individuation_data();
        data.setTitle("淡蓝色");
        data.setBackGroundColor(Color.parseColor("#87CEFA"));
        list.add(data);
        return list;
    }
}
