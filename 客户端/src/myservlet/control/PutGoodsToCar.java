package myservlet.control;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import mybean.data.Login;
 

public class PutGoodsToCar extends HttpServlet {
 
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
 
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request,response);
	}
 
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		String goods=request.getParameter("java");
		System.out.println(goods);
		Login loginBean=null;
		HttpSession session=request.getSession(true);
		try{
			loginBean=(Login) session.getAttribute("loginBean");
			boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");
			LinkedList<String> car=loginBean.getCar();
			car.add(goods);
			speakSomeMess(request,response,goods);
		}catch(Exception e){}
	}
 
	private void speakSomeMess(HttpServletRequest request,
			HttpServletResponse response, String goods) {
		response.setContentType("text/html;charset=utf-8");
		try{			
		}catch(Exception e){}
	}
}