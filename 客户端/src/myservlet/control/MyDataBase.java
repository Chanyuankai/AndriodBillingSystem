package myservlet.control;

import java.sql.*;

public class MyDataBase {
    //MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://127.0.0.1/shop";
    // 数据库的用户名与密码，需要根据自己的设置
    final String USER = "root";
    final String PASS = "Asd88232301";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs =null;
    PreparedStatement pst=null;
    public MyDataBase() {
        try {
            Class.forName(JDBC_DRIVER);  // 注册 JDBC 驱动
            conn = DriverManager.getConnection(DB_URL,USER,PASS); // 打开链接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertDB(String t2,String t3) {
        String sql = "insert into login values (?,?)";
        try {
            pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
       
            pst.setString(2, t2);
            pst.setString(3, t3);
      
            pst.executeUpdate();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public void close() {
        // 关闭资源
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}
