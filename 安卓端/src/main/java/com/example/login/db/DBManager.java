package com.example.login.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * 负责管理数据库的类
 *   主要对于表当中的内容进行操作，增删改查
 * */
public class DBManager {

    private static SQLiteDatabase db;
    /* 初始化数据库对象*/
    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);  //得到帮助类对象
        db = helper.getWritableDatabase();      //得到数据库对象
    }

    /*
     * 向记账表当中插入一条元素
     * */
    public static void insertItemToAccounttb(List<Map> mapList){

        for(Map c:mapList) {
            ContentValues values = new ContentValues();
            values.put("typename", c.get("cyk_typename").toString());
            values.put("logname",c.get("cyk_logname").toString());
            values.put("sImageId", (Integer) c.get("cyk_sImageId"));
            values.put("beizhu", c.get("cyk_beizhu").toString());
            values.put("money", c.get("cyk_money").toString());
            values.put("time", c.get("cyk_time").toString());
            values.put("year", (Integer) c.get("cyk_year"));
            values.put("month",(Integer) c.get("cyk_month"));
            values.put("day", (Integer) c.get("cyk_day"));
            values.put("kind", (Integer) c.get("cyk_kind"));
            db.insert("myaccounttb", null, values);
            System.out.println("成功");
        }
    }

    /**
     * 读取数据库当中的数据，写入内存集合里
     *   kind :表示收入或者支出
     * */
    public static List<TypeBean>getTypeList(int kind){
        List<TypeBean>list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from ntypetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
//        循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);
        }
        return list;
    }

    /*
     * 向记账表当中插入一条元素
     * */
    public static void insertItemToAccounttb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("logname",bean.getLogname());
        values.put("sImageId",bean.getsImageId());
        values.put("beizhu",bean.getBeizhu());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("myaccounttb",null,values);
    }

    /**
     * POST方式提交表单
     */
    public static void postForm(AccountBean bean) {
        String ipadress = "18.113.61.194";
        String url = "http://"+ipadress+":8080/Shopping/insertServlet";
        OkHttpClient okHttpClient = new OkHttpClient();

        //普通表单并没有指定Content-Type，这是因为FormBody继承了RequestBody，它已经指定了数据类型为application/x-www-form-urlencoded。
        //表单提交
        FormBody formBody = new FormBody.Builder()
                .add("typename",bean.getTypename())
              .add("logname",bean.getLogname())
                .add("sImageId", String.valueOf(bean.getsImageId()))
                .add("beizhu", bean.getBeizhu())
                .add("money", bean.getMoney())
                .add("time",bean.getTime())
                .add("year", String.valueOf(bean.getYear()))
                .add("month", String.valueOf(bean.getMonth()))
                .add("day", String.valueOf(bean.getDay()))
                .add("kind", String.valueOf(bean.getKind()))
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
    /*
     * 获取记账表当中某一天的所有支出或者收入情况
     * */
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day,String logname){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from myaccounttb where year=? and month=? and day=? and logname=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", logname + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id,logname ,typename,sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 获取记账表当中某一月的所有支出或者收入情况
     * */
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year,int month,String logname){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from myaccounttb where year=? and month=? and logname=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", logname + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id,logname, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    /**
     * 获取某一天的支出或者收入的总金额   kind：支出==0    收入===1
     * */
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from myaccounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /**
     * 获取某一月的支出或者收入的总金额   kind：支出==0    收入===1
     * */
    public static float getSumMoneyOneMonth(int year,int month,int kind,String logname){
        float total = 0.0f;
        String sql = "select sum(money) from myaccounttb where year=? and month=? and kind=? and logname=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + "", logname + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /** 统计某月份支出或者收入情况有多少条  收入-1   支出-0*/
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from myaccounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }
    /**
     * 获取某一年的支出或者收入的总金额   kind：支出==0    收入===1
     * */
    public static float getSumMoneyOneYear(int year,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from myaccounttb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    /*
     * 根据传入的id，删除accounttb表当中的一条数据
     * */
    public static int deleteItemFromAccounttbById(int id){
        int i = db.delete("myaccounttb", "id=?", new String[]{id + ""});
        return i;
    }
    /**
     * 根据备注搜索收入或者支出的情况列表
     * */
    public static List<AccountBean>getAccountListByRemarkFromAccounttb(String beizhu){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from myaccounttb where beizhu like '%"+beizhu+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String logname = cursor.getString(cursor.getColumnIndex("logname"));
            String bz = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, logname,typename, sImageId, bz, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    /**
     * 查询记账的表当中有几个年份信息
     * */
    public static List<Integer>getYearListFromAccounttb(){
        List<Integer>list = new ArrayList<>();
        String sql = "select distinct(year) from myaccounttb order by year asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        return list;
    }

    /*
     * 删除accounttb表格当中的所有数据
     * */
    public static void deleteAllAccount(){
        String sql = "delete from myaccounttb";
        db.execSQL(sql);
        System.out.println("shcnhul1");
    }


}
