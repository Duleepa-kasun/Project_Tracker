package org.wso2.projecttracker.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.UserService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class UserSearchController
 */
@WebServlet("/UserSearchController")
public class UserSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
	    String query = request.getParameter("query");
	    String token = (String)request.getSession().getAttribute("token");
	    
	    UserService service=new UserService();
	    JSONObject users=service.getAllUsers(token);
	    out.print( JsonPath.read(users.toString(),"$.users.user[*].email").toString());
	}

}
