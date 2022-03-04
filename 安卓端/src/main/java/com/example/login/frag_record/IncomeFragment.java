package com.example.login.frag_record;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.login.MainActivity;
import com.example.login.R;
import com.example.login.db.DBManager;
import com.example.login.db.TypeBean;
import com.example.login.downloadActivity;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends BaseRecordFragment {
    private static Handler handler;
    private static int count;
    List<Map> allData=new ArrayList<>();
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("else");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
       MainActivity.getLogname(accountBean);
       DBManager.insertItemToAccounttb(accountBean);
        DBManager.postForm(accountBean);
    }

//    public static void GET() {
//        String ipadress = "18.113.61.194";
//        String url = "http://"+ipadress+":8080/Shopping/getServlet";
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        System.out.println(url);
//        client.newCall(request).enqueue(new Callback() {
//            List<Map> allData=new ArrayList<>();
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Message msg = handler.obtainMessage();
//                JSONObject tmp = null;
//                try {
//                    tmp = new JSONObject((String) msg.obj);
//                    count=tmp.getInt("count");
//                    if(count!=0){
//                        for(int i=1;i<=count;i++){
//                            String map=tmp.getString(i+"");
//                            HashMap hashMap= JSON.parseObject(map,HashMap.class);
//                            allData.add(hashMap);
//                        }
//                        DBManager.insertItemToAccounttb(allData);
//
//                    }
//
//                } catch (JSONException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String tmp = response.body().string();
//                Message msg = handler.obtainMessage();
//                msg.obj = tmp;
////                System.out.println("--------------"+tmp+"----------");
//                msg.what = 1;
//                msg.sendToTarget();
//            }
//        });
//    }

}
