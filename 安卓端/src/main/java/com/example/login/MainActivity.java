package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.login.db.AccountBean;

public class MainActivity extends AppCompatActivity {
    private static String Loginname;

     AccountBean accountBean;
    EditText login,password;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.name);
        password = findViewById(R.id.password);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        try {

                            String s =  (String) msg.obj;
                            System.out.println("+++++"+s+"++");
                            if(s.startsWith("<!doctype html")){
                                Toast.makeText(MainActivity.this,"网址不对",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                JSONObject tmp = new JSONObject((String)msg.obj);
                                Boolean suc = tmp.getBoolean("success");
                                if (suc) {
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(MainActivity.this,homeActivity.class);
                                    startActivity(intent);
                                    Loginname=tmp.getString("name");
                                } else {
                                    String tmp1 = tmp.getString("result");
                                    if (tmp1.equals("error")) {
                                        Toast.makeText(MainActivity.this, "用户名或者密码不对", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "网络状况不好", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        };
    }

    public static void getLogname(AccountBean accountBean) {
        accountBean.setLogname(Loginname);
    }


    public static String getLogname() {
        return Loginname;
    }
    public void click(View view) {
        switch (view.getId()){
            case R.id.login:
                login();

                break;
            case R.id.resident:
               Intent intent=new Intent(MainActivity.this,residentActivity.class);
              startActivity(intent);
                break;

        }
    }



    private void login() {
        String ipadress = "18.113.61.194";
        String url = "http://"+ipadress+":8080/Shopping/loginServlet?name="+login.getText().toString()
                +"&pass="+password.getText().toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        System.out.println(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = handler.obtainMessage();
                JSONObject tmp = new JSONObject();
                try{
                    tmp.put("success",false);
                    tmp.put("result","failure...");
                    msg.obj = tmp.toString();
                    System.out.println(tmp.getString("success")+"----------");
                    msg.what = 1;
                    msg.sendToTarget();
                }catch (JSONException e1){
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String tmp = response.body().string();
                Message msg = handler.obtainMessage();
                msg.obj = tmp;
//                System.out.println("--------------"+tmp+"----------");
                msg.what = 1;
                msg.sendToTarget();
            }
        });
    }

}
