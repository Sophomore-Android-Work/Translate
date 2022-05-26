package com.example.tra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.tra.history.Tindividuation;
import com.example.tra.pojo.Mydict;
import com.example.tra.pojo.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class QueryWordsActivity extends AppCompatActivity {

    EditText etWord ;
    Button queryWord,Scheme;
    TextView tvExplain;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return false;
        }
    }){
        public void handleMessage(Message msg) {
            Bundle s=msg.getData();

            //解析出数据
//            String name=s.getString("name");
            String word = s.getString("word");
            String explain = s.getString("explain");

            //UI线程，把数据在UI显示出来
            tvExplain.setText("word:"+word+"   explain:"+explain);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_words);

        etWord = (EditText) findViewById(R.id.query_etWord);
        queryWord = (Button) findViewById(R.id.query_btnQuery);
        tvExplain = (TextView) findViewById(R.id.query_tv_explain);
        Scheme = findViewById(R.id.Scheme);

        Scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QueryWordsActivity.this, Tindividuation.class);
                startActivity(intent);
            }
        });

        queryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = etWord.getText().toString();
                if(word.isEmpty()){
                    Toast.makeText(QueryWordsActivity.this,"请输入单词！",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url=new URL(Constant.URL_Word + "?word=" + etWord.getText().toString());
                                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String result="";
                                String line=null;
                                while((line=reader.readLine())!=null){
                                    result+=line+"\n";
                                }
                                Log.d("JSON","RESULT"+result);
                                if(!"".equals(result)){
                                    Mydict mydict= JSON.parseObject(result, Mydict.class);
                                    Message message=handler.obtainMessage();
                                    Bundle u=new Bundle();
                                    //解析JSON格式数据,把类似于键值对类似的数据传递出去
//                                    u.putString("name",user.getName());
//                                    u.putString("sex",user.getSex());
//                                    u.putString("phone",user.getPhone());
//                                    u.putString("account",user.getAccount());
//                                    u.putString("password",user.getPassword());
                                    u.putString("word",mydict.getWord());
                                    u.putString("explain",mydict.getExplain());
                                    message.setData(u);
                                    message.sendToTarget();
                                }
                                reader.close();
                                conn.disconnect();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

    }
}