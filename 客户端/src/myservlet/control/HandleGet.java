package myservlet.control;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONObject;
public class HandleGet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		link();
		}
		void link() {
			try {
			
				Class.forName("com.mysql.jdbc.Driver");
			}catch(ClassNotFoundException e) {
				System.out.println("Not find mysql driver");
				e.printStackTrace();
			}
		}
		Connection conMysql() {
			Connection conn = null;
			String url = "jdbc:mysql://127.0.0.1/shop";
			try {
				conn = (Connection) DriverManager.getConnection(url,"root","Asd88232301");
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			link();
			PrintWriter out = null;
				Connection conn = conMysql();
				List<Map> result=new ArrayList<>();
				PreparedStatement stmt = null;
				try {
					stmt = (PreparedStatement) conn.prepareStatement("select cyk_typename,cyk_logname,cyk_sImageId,cyk_money,cyk_beizhu,cyk_time,cyk_time,cyk_year,cyk_month,cyk_day,cyk_kind from account");					
					ResultSet rs = stmt.executeQuery();
					if(rs.next()) {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=utf-8");
						JSONObject json = new JSONObject();
						json.put("success", true);						
						int id=1;
						while(rs.next()) {
							ResultSetMetaData rsmd=rs.getMetaData();
							int columnCount=rsmd.getColumnCount();
							Map map=new HashMap();
							for(int i=0;i<columnCount;i++) {
								map.put(rsmd.getColumnName(i+1).toLowerCase(),rs.getObject(i+1));
								
							}
							result.add(map);	
							json.put(id++,map);
						}
						json.put("count", id-1);
						out= response.getWriter();
						out.write(json.toString());
					    for(Map c:result) {
					    	System.out.println(c);
					    }
					}						
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
		}
	
}
