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

/**
 * Servlet implementation class GetUserController
 */
@WebServlet("/GetUserController")
public class GetUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		UserService userService = new UserService();

		String token = (String) request.getSession().getAttribute("token");
		JSONObject user = userService.getUserById(userId, token);
		response.setContentType("text/html");
		response.getWriter().print(user.toString());
		request.setAttribute("user", user);
		request.setAttribute("isValied", "true");
		//response.sendRedirect("admin/userDetails.jsp");
		request.getRequestDispatcher("admin/userDetails.jsp").forward(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getParameter("userId");
		UserService userService = new UserService();

		String token = (String) req.getSession().getAttribute("token");
		JSONObject user = userService.getUserById(userId, token);

		PrintWriter out = resp.getWriter();
		out.print(user.toString());
	}

}
