package myservlet.control;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONObject;
public class HandleRegister extends HttpServlet {
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
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			PrintWriter out = null;
			if (name != null && password != null) {
				Connection conn = conMysql();
				PreparedStatement stmt = null;
				try {
					stmt = (PreparedStatement) conn.prepareStatement("insert into login (logname,password) values(?,?)");
					stmt.setString(1, name);
					stmt.setString(2, password);
					int status =  stmt.executeUpdate();
					if(status == 1) {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json; charset=utf-8");
						JSONObject json = new JSONObject();
						json.put("success", true);
						json.put("name", name);
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
