package com.example.login.db;

import android.os.Handler;
import android.os.Message;

import com.example.login.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServerManager {
    private static String Loginname;


    public static void Get() {
        String ipadress = "18.113.61.194";
        String url = "http://"+ipadress+":8080/Shopping/getServlet";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        System.out.println(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Message msg = handler.obtainMessage();
                JSONObject tmp = new JSONObject();
                try{
                    tmp.put("success",false);
                    tmp.put("result","failure...");
//                    msg.obj = tmp.toString();
//                    msg.what = 1;
//                    msg.sendToTarget();
                }catch (JSONException e1){
                    e1.printStackTrace();
                }


            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String tmp = response.body().string();
//                Message msg = handler.obtainMessage();
//                msg.obj = tmp;
////                System.out.println("--------------"+tmp+"----------");
//                msg.what = 1;
//                msg.sendToTarget();
            }
        });
    }


}
