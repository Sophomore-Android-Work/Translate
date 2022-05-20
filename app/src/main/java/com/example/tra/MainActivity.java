package com.example.tra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tra.dao.RecordsDao;
import com.example.tra.history.RecordAdapter;
import com.example.tra.service.GetValues;
import com.example.tra.service.TransApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "20220316001127882";
    private static final String SECURITY_KEY = "HXSCfrvdqfLwz0_88U0B";
    private EditText mean;
    private EditText in;
    private Button sure;
    private Button btnLogin;//登录按钮

    private Spinner  spinnerCardNumber_begin;
    private static final String[] m_languages = { "英语", "文言文", "粤语", "中文简体","中文繁体","日语","法语","韩语","西班牙语","泰语","阿拉伯语","俄语","德语" };
    private static final String[] begin_languages = { "英语", "文言文", "粤语", "中文简体","中文繁体","日语","法语","韩语","西班牙语","泰语","阿拉伯语","俄语","德语" };


    private RecordsDao recordsDao =new RecordsDao(MainActivity.this);
    private List<String> recordsList;
    private TextView clearRecords_tv;//清楚历史记录
    private RecordAdapter recordsAdapter;
    private ListView records_lv ;//存放历史记录的list
//    String record  = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        begin_string();
        String str ="auto";
        String s= translate(str);
        initView(str,s);
        bindAdapter();

    }

    public void initView(String str,String lan) {
        mean=(EditText) findViewById(R.id.mean);
        in=(EditText) findViewById(R.id.in);
        sure=(Button) findViewById(R.id.sure);
        records_lv=(ListView)findViewById(R.id.search_record_lv) ;
        clearRecords_tv = (TextView)findViewById(R.id.clearReocrds_tv);
        recordsList = recordsDao.getRecordsList();
        recordsAdapter = new RecordAdapter(MainActivity.this,R.layout.list_item,recordsList);
        clearRecords_tv = (TextView)findViewById(R.id.clearReocrds_tv);
        btnLogin = (Button)findViewById(R.id.btn_login);//找到btn_login


        records_lv.setAdapter(recordsAdapter);

        sure.setOnClickListener((v)->{
            final String s=in.getText().toString();

           recordsDao.addRecords(s);
            Toast.makeText(MainActivity.this,"添加了",Toast.LENGTH_LONG);


            if(s==null){
                Toast.makeText(MainActivity.this,"null!!!",Toast.LENGTH_LONG);
            }
            final TransApi api=new TransApi(APP_ID,SECURITY_KEY);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    eJosn(api.getTransResult(s,str,lan));
                }
            }).start();

            initView(str,lan);
        });

        //登录按钮的点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //点击后将数据放入edittext
        records_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myClicked = recordsList.get(position);
                in.setText(myClicked);
            }
        });

        clearRecords_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    recordsList.clear();
                    recordsDao.clearRecords();
                    recordsAdapter.notifyDataSetChanged();
                    ckeckRecords();
            }
        });

        ckeckRecords();


    }

    public void eJosn(String json) {
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray object=jsonObject.getJSONArray("trans_result");
            JSONObject jsonObject3=(JSONObject) object.get(0);
            String s=jsonObject3.getString("dst");
            mean.setText(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 绑定适配器
     */
    void bindAdapter(){
        recordsList = recordsDao.getRecordsList();    //获取搜索记录的数组
        reversedRecords();
        ckeckRecords();                                //检查是否有记录
        recordsAdapter = new RecordAdapter(MainActivity.this,R.layout.list_item,recordsList);
        records_lv.setAdapter(recordsAdapter);
    }

    /**
     * 该函数用于反转搜索记录
     */
    private void reversedRecords(){
        String temp = "";
        int size = (recordsList.size())/2;
        int foot = recordsList.size()-1;
        //下面的循环实现数组首尾置换
        for (int i=0;i<size;i++){
            foot = foot - i;
            temp = recordsList.get(i);
            recordsList.set(i,recordsList.get(foot));
            recordsList.set(foot,temp);
        }
    }

    private void ckeckRecords(){
        if (recordsList.size() > 0){
            clearRecords_tv.setVisibility(View.VISIBLE);
        }else {

            clearRecords_tv.setVisibility(View.GONE);
        }
    }

    /**
     * 创建一个“历史记录”列表的监听器对象
     * 点击记录，可把该记录添加到搜索框里
     */
    AdapterView.OnItemClickListener lvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String myClicked = recordsList.get(position);
            in.setText(myClicked);
        }
    };

    /**
     * 创建一个“清除历史记录”的监听器对象
     */
    View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recordsList.clear();
            recordsDao.clearRecords();
            recordsAdapter.notifyDataSetChanged();
            ckeckRecords();
        }
    };

    public String begin_string (){


        final String[] lan_begin = new String[1];
        Spinner spinnerCardNumber_begin = (Spinner) findViewById(R.id.spinner_begin);
        ArrayAdapter<String> adapter_begin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, begin_languages);


        spinnerCardNumber_begin.setAdapter(adapter_begin);
        spinnerCardNumber_begin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(begin_languages[i].equals("英语"))
                {
                    lan_begin[0] ="en";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("文言文"))
                {
                    lan_begin[0] ="wyw";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("粤语"))
                {
                    lan_begin[0] ="yue";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("中文简体"))
                {
                    lan_begin[0] ="zh";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("中文繁体"))
                {
                    lan_begin[0] ="cht";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("日语"))
                {
                    lan_begin[0] ="jp";
                    // initView(str,lan);
                }
                if(begin_languages[i].equals("韩语"))
                {
                    lan_begin[0] ="kor";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("法语"))
                {
                    lan_begin[0] ="fra";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("西班牙语"))
                {
                    lan_begin[0] ="spa";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("泰语"))
                {
                    lan_begin[0] ="th";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("阿拉伯语"))
                {
                    lan_begin[0] ="ara";
                    // initView(str,lan);
                }
                if(begin_languages[i].equals("俄语"))
                {
                    lan_begin[0] ="ru";
                    //initView(str,lan);
                }
                if(begin_languages[i].equals("德语"))
                {
                    lan_begin[0] ="de";
                    //initView(str,lan);
                }
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return lan_begin[0] ;
    }

    public String translate(String str){


        Spinner spinnerCardNumber = (Spinner)findViewById(R.id.spinner);
        final String[] lan = new String[1];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_languages);

        spinnerCardNumber.setAdapter(adapter);

        spinnerCardNumber.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if(m_languages[arg2].equals("英语"))
                {
                    lan[0] ="en";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("文言文"))
                {
                    lan[0] ="wyw";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("粤语"))
                {
                    lan[0] ="yue";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("中文简体"))
                {
                    lan[0] ="zh";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("中文繁体"))
                {
                    lan[0] ="cht";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("日语"))
                {
                    lan[0] ="jp";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("韩语"))
                {
                    lan[0] ="kor";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("法语"))
                {
                    lan[0] ="fra";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("西班牙语"))
                {
                    lan[0] ="spa";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("泰语"))
                {
                    lan[0] ="th";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("阿拉伯语"))
                {
                    lan[0] ="ara";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("俄语"))
                {
                    lan[0] ="ru";
                    initView(str, lan[0]);
                }
                if(m_languages[arg2].equals("德语"))
                {
                    lan[0] ="de";
                    initView(str, lan[0]);
                }
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        return lan[0];
    }


}