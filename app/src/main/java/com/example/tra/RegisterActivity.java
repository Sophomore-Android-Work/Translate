package com.example.tra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private static int checkCode = 6666;
    EditText edAccount;
    EditText edPwd;
    EditText edUsername;
    EditText edSex;
    EditText edPhone;
    Button btnGetCheck;//获取验证码的按钮
    EditText edCheck;//填写验证码的edittext
    Button btnRegiter;
    TextView textView;//显示状态码用的

    String account ;
    String password;
    String username;
    String sex ;
    String phone ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        btnGetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(RegisterActivity.this,"验证码已经发送"+checkCode,Toast.LENGTH_SHORT).show();
                initView();
                btnRegiter.setVisibility(View.VISIBLE);
            }
        });

        btnRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = edAccount.getText().toString();
                password = edPwd.getText().toString();
                username = edUsername.getText().toString();
                sex = edSex.getText().toString();
                phone = edPhone.getText().toString();
                if(edCheck.getText().toString().equals("6666")){
                    register(account,password,username,sex,phone);
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initView(){
        edAccount = (EditText) findViewById(R.id.reg_account);
        edPwd = (EditText) findViewById(R.id.reg_password);
        edUsername = (EditText) findViewById(R.id.reg_username);
        edSex = (EditText) findViewById(R.id.reg_sex);
        edPhone = (EditText) findViewById(R.id.reg_phone);
        btnGetCheck = (Button) findViewById(R.id.reg_getCheck);
        edCheck = (EditText) findViewById(R.id.reg_check);
        btnRegiter = (Button) findViewById(R.id.reg_register);
        btnRegiter.setVisibility(View.INVISIBLE);
        textView = (TextView) findViewById(R.id.tv_info);

    }

    private void register(String account, String password,String username,String sex,String phone) {
        if (account.isEmpty()||password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"账号或者密码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            String registerUrlStr = Constant.URL_Register + "?userAccount=" + account
                    + "&userPassword=" + password
                    +"&userName=" + username
                    +"&sex=" + sex
                    +"&phone=" + phone;
            Log.d("JSON","指令："+registerUrlStr);
            RegisterActivity.Login_AsyncTask login_asyncTask =new RegisterActivity.Login_AsyncTask();
            login_asyncTask.execute(registerUrlStr);
        }

    }


    class Login_AsyncTask extends AsyncTask<String, Integer, String> {

        public Login_AsyncTask() {
            Log.d("JSON","验证前");

        }

        @Override
        public void onPreExecute() {
            Log.w("JSON", "开始验证.........");
        }

        /**
         * @param params 这里的params是一个数组，即AsyncTask在激活运行是调用execute()方法传入的参数
         */
        @Override
        public String doInBackground(String... params) {
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            try {
//                URL url = new URL(params[0]); // 声明一个URL
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                connection.setConnectTimeout(80000); // 设置连接建立的超时时间
                connection.setReadTimeout(80000); // 设置网络报文收发超时时间
                BufferedReader reader =new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString(); // 这里返回的结果就作为onPostExecute方法的入参
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 如果在doInBackground方法，那么就会立刻执行本方法
            // 本方法在UI线程中执行，可以更新UI元素，典型的就是更新进度条进度，一般是在下载时候使用
        }

        /**
         * 运行在UI线程中，所以可以直接操作UI元素
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON",s);//打印服务器返回标签
            //flag=true;
            switch (s){
                //判断返回的状态码，并把对应的说明显示在UI
                case "100":
                    textView.setText(s+"：登录失败，密码不匹配或账号未注册");
                    break;
                case "200":
                    textView.setText(s+"：登录成功");
                    break;
                case "300":
                    textView.setText(s+"：该账号已存在");
                    break;
                case "400":
                    textView.setText(s+"：注册成功");
                    break;
                case "500":
                    textView.setText(s+"：注册失败");
                    break;
                default:
                    textView.setText("异常");
            }
            Log.d("JSON","验证后");
        }
    }



}