package com.developer.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		RequestDispatcher dispatcher=null;
		Connection con=null;
		PrintWriter out=response.getWriter();
		
		
		String uname=request.getParameter("name");
		String upwd=request.getParameter("pass");
		String reupwd=request.getParameter("re_pass");
		String uemail=request.getParameter("email");
		String umobile=request.getParameter("contact");
		
		if(uname==null || uname.equals("")) {
			request.setAttribute("status", "invalidUname");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		if(upwd == null || upwd.equals("")) {
			request.setAttribute("status", "invalidUpwd");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}else if(!upwd.equals(reupwd)) {
			request.setAttribute("status", "invalidConfirmPwd");
		
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		if(uemail == null || uemail.equals("")) {
			request.setAttribute("status","invalidEmail");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		if(umobile == null || umobile.equals("")) {
			request.setAttribute("status", "invalidMobile");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if(umobile.length()>10) {
			request.setAttribute("status", "invalidMobileLength");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
	   
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	         con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","root");
			PreparedStatement pst = con.prepareStatement("insert into signup(uname,upwd,uemail,umobile) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			
			int rowcount = pst.executeUpdate();
			dispatcher =request.getRequestDispatcher("registration.jsp");
			
			if(rowcount >0) {
				request.setAttribute("status", "Success");
			}else {
				request.setAttribute("status", "Failed");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
