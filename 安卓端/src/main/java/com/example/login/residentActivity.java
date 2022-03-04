package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class residentActivity extends AppCompatActivity {
    EditText loginname,pwd;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);
        loginname=findViewById(R.id.logname);
        pwd=findViewById(R.id.pwd);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Toast.makeText(residentActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(residentActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public void click(View view){
        postForm();
        Intent intent=new Intent(residentActivity.this,MainActivity.class);
        startActivity(intent);

    }
    /**
     * POST方式提交表单
     */
    private void postForm() {
        String ipadress = "18.113.61.194";
        String url = "http://"+ipadress+":8080/Shopping/registerServlet";
        OkHttpClient okHttpClient = new OkHttpClient();

        //普通表单并没有指定Content-Type，这是因为FormBody继承了RequestBody，它已经指定了数据类型为application/x-www-form-urlencoded。
        //表单提交
        FormBody formBody = new FormBody.Builder()
                .add("name", loginname.getText().toString())
                .add("pass", pwd.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("提交","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("提交","成功");
                }
            }
        });
    }

}
