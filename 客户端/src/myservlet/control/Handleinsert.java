package myservlet.control;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONObject;
public class Handleinsert extends HttpServlet {
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
			String typename = request.getParameter("typename");
			String logname = request.getParameter("logname");
			String sImageId = request.getParameter("sImageId");
			String money = request.getParameter("money");
			String beizhu = request.getParameter("beizhu");
			String time = request.getParameter("time");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String day = request.getParameter("day");
			String kind = request.getParameter("kind");
			PrintWriter out = null;
			if (kind != null && money != null) {
				Connection conn = conMysql();
				PreparedStatement stmt = null;
				try {
					stmt = (PreparedStatement) conn.prepareStatement("insert into account (cyk_typename,cyk_logname,cyk_sImageId,cyk_money,cyk_beizhu,cyk_time,cyk_year,cyk_month,cyk_day,cyk_kind) values(?,?,?,?,?,?,?,?,?,?)");
					stmt.setString(1, typename);
					stmt.setString(2, logname);
					stmt.setString(3, sImageId);
					stmt.setString(4, money);
					stmt.setString(5, beizhu);
					stmt.setString(6, time);
					stmt.setString(7, year);
					stmt.setString(8, month);
					stmt.setString(9, day);
					stmt.setString(10, kind);						
					int status =  stmt.executeUpdate();
					if(status == 1) {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=utf-8");
						JSONObject json = new JSONObject();
						json.put("success", true);
						json.put("money", money);
						out= response.getWriter();
						out.write(json.toString());
					}
					else {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=utf-8");
						JSONObject json = new JSONObject();
						json.put("success", false);
						json.put("result","error");
						out= response.getWriter();
						out.write(json.toString());
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
		}
	
}
