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
 * Servlet implementation class DeleteUserController
 */
@WebServlet("/DeleteUserController")
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String user_id = request.getParameter("delete_user_id");
		String token = (String) request.getSession().getAttribute("token");

		UserService userservice = new UserService();

		JSONObject result = userservice.deleteUser(user_id, token);

		String status = JsonPath.read(result.toString(), "$.message")
				.toString();
		if (status.equalsIgnoreCase("SUCCESS")) {
			request.getSession().setAttribute("user_delete", "SUCCESS");
		}

		response.sendRedirect("admin/user.jsp");
	}

}
