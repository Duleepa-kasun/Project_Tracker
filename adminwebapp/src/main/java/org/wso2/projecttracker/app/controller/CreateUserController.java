package org.wso2.projecttracker.app.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.projecttracker.app.model.UserService;

/**
 * Servlet implementation class CreateUserController
 */
@WebServlet("/CreateUserController")
public class CreateUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("user_name");
		String username = request.getParameter("user_username");
		String password = request.getParameter("user_password");
		String confirm_password = request.getParameter("user_confirm_password");
		String email = request.getParameter("user_email");
		String role = request.getParameter("user_role");
		
		if(username ==null || username.trim().equalsIgnoreCase("") || name==null || name.trim().equalsIgnoreCase("")
				||password==null || password.trim().equalsIgnoreCase("")||confirm_password==null || confirm_password.trim().equalsIgnoreCase("")
				||email==null || email.trim().equalsIgnoreCase("")||role==null || role.trim().equalsIgnoreCase("")){
			request.getSession().setAttribute("user_input_error", "input fileld is / are empty");
			response.sendRedirect("admin/user.jsp");
		}else if(!password.equalsIgnoreCase(confirm_password)){
			request.getSession().setAttribute("user_input_error", "password values does not match");
			response.sendRedirect("admin/user.jsp");
		}
		else{
			UserService userService = new UserService();
			String s = userService.createUser(username, confirm_password, email, role, username);
			response.setContentType("text/html");
			response.getWriter().print(s);
			if(s==null){
				response.setContentType("text/html");
				response.getWriter().print("null");
			}
			else if(s.equalsIgnoreCase("SUCCESS")){
				request.getSession().setAttribute("user_input_error", null);
				response.sendRedirect("admin/user.jsp");
			}else if(s.equalsIgnoreCase("EXISTS")){
				request.getSession().setAttribute("user_input_error", "username or email already exists");
				response.sendRedirect("admin/user.jsp");
			}else if(s.equalsIgnoreCase("UNSUCCESS")){
				request.getSession().setAttribute("user_input_error", "fail");
				response.sendRedirect("admin/user.jsp");
			}
		}
		
		
	}

}
