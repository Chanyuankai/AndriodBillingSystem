package myservlet.control;

import java.sql.*;

public class MyDataBase {
    //MySQL 8.0 ���ϰ汾 - JDBC �����������ݿ� URL
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://127.0.0.1/shop";
    // ���ݿ���û��������룬��Ҫ�����Լ�������
    final String USER = "root";
    final String PASS = "Asd88232301";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs =null;
    PreparedStatement pst=null;
    public MyDataBase() {
        try {
            Class.forName(JDBC_DRIVER);  // ע�� JDBC ����
            conn = DriverManager.getConnection(DB_URL,USER,PASS); // ������
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertDB(String t2,String t3) {
        String sql = "insert into login values (?,?)";
        try {
            pst = conn.prepareStatement(sql);//����ִ��SQL����ѯ����sql������Ԥ���봦��
       
            pst.setString(2, t2);
            pst.setString(3, t3);
      
            pst.executeUpdate();
        } catch (SQLException e) {
            // TODO �Զ����ɵ� catch ��
            e.printStackTrace();
        }
    }

    public void close() {
        // �ر���Դ
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}
