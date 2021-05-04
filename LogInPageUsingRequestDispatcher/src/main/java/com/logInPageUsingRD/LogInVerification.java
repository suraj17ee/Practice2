package com.logInPageUsingRD;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LogInVerification extends GenericServlet{
	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
	String user=req.getParameter("user");
	String pass=req.getParameter("pass");
	Connection cn=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PrintWriter ab=resp.getWriter();
	try {
		Class.forName("com.mysql.jdbc.Driver");
		cn=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","abcd");
		pst=cn.prepareStatement("select * from loginpageex.reg where user=?");
		pst.setString(1, user);
		rs=pst.executeQuery();
		if(rs.next())
		{
			if(rs.getString("pass").equals(pass))
			{
				ab.println("<html>"+"<body>"
						+"<h1>welcome.....</h1>"
						+"</body>"+"</html>");
			}
			else
			{
				RequestDispatcher rd=req.getRequestDispatcher("LoginNew.html");
				rd.include(req, resp);
				ab.println("<html>"
						+"<body>"
						+"<h1>Check your password</h1>"
						+"</body>"
						+"</html>");
			}
		}
		else
		{
			RequestDispatcher rd=req.getRequestDispatcher("SignupNew.html");
			rd.include(req, resp);
		}	
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}	
	}
}
