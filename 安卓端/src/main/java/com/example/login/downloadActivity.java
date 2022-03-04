package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.login.db.AccountBean;
import com.example.login.db.DBManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class downloadActivity extends AppCompatActivity {

    static Handler handler;
    private String Loginname;
    private int count;
    List<Map> allData=new ArrayList<>();

    List<Map> DATA=new ArrayList<>();
    private static SQLiteDatabase db;
    static DBManager dbManager = new DBManager();
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        dbManager.initDB(this);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        try {
                            String s =  (String) msg.obj;

                            if(s.startsWith("<!doctype html")){

                            }
                            else {
                                JSONObject tmp = new JSONObject((String)msg.obj);
                                Boolean suc = tmp.getBoolean("success");
                                if (suc) {
                                     count = tmp.getInt("count");
                                    if(count!=0){
                                        System.out.println("count is "+count);
                                        for(int i=1;i<=count;i++){
                                            String map=tmp.getString(i+"");
                                            HashMap hashMap= JSON.parseObject(map,HashMap.class);
                                            allData.add(hashMap);

//                                            insertItemToAccounttb(allData);
//                                            Toast.makeText(downloadActivity.this,"下载数据成功",Toast.LENGTH_SHORT).show();
                                        }
                                       dbManager.insertItemToAccounttb(allData);
                                        System.out.println(allData);

                                    }
                                } else {
                                    String tmp1 = tmp.getString("result");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        };
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.download:
                get();
               Intent it = new Intent(this, homeActivity.class);  //跳转界面
                startActivity(it);
                break;
            case R.id.go:
                Intent it2 = new Intent(this, homeActivity.class);  //跳转界面
                startActivity(it2);

        }
    }



    public static void get() {
        String ipadress = "18.113.61.194";
        String url = "http://"+ipadress+":8080/Shopping/getServlet";
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

