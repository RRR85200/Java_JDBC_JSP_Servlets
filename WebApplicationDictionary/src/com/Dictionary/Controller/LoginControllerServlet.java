package com.Dictionary.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.Dictionary.dao.AccesDBValidation;

/*
 * 
 */

@WebServlet("/LoginControllerServlet")

public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*mapping this to Context.xml which has all the JDBC connection and handling max & min users
	 *  context.xml contains a JNDI(Java Naming and Directory Interface) Resource, of the type javax.sql.DataSource.
	 */
	@Resource(name = "jdbc/WebApplicationDictionary")
	
	DataSource dbSource ;
	AccesDBValidation UserServletConn ;
	
	@Override
	public void init(){
		/*Instantiate WordDefDBUtil to establish connection before taking in the appropriate method
		* */	
		UserServletConn = new AccesDBValidation(dbSource);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();  

		String id= request.getParameter("userId");
		String pass= request.getParameter("passWord");

		
		HttpSession session = request.getSession();
		
		if(session!=null){
			session.setAttribute("name", id);
		
		try {
			if(UserServletConn.validateUser(id, pass)){

				if(UserServletConn.adminAccess()){	
					
					RequestDispatcher rd = request.getRequestDispatcher("AdminControllerServlet");
					rd.forward(request, response);
					System.out.println("Accessing as Admin");
				}
				else{
					RequestDispatcher rd = request.getRequestDispatcher("word_search_engine.jsp");
					rd.forward(request, response);
					System.out.println("Accessing as user");
				}
			}
			
			else{  
				out.print("<p style=\"color:red\">Sorry username or password error</p>");  
				RequestDispatcher rd=request.getRequestDispatcher("LoginPage.jsp");  
				rd.include(request,response);  
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		out.close();

	}


	}
	
	

	}


