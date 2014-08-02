package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.projecttracker.app.model.UserProjectService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class DeleteProjectUserController
 */
@WebServlet("/DeleteProjectUserController")
public class DeleteProjectUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteProjectUserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("delete_project_user_id");
		String projectId=request.getParameter("delete_project_id");
		String token = request.getParameter("token");
		
		UserProjectService userProjectService=new UserProjectService();
		String result=userProjectService.removeUserFromProject(userId, projectId, token);
		
		String message = JsonPath.read(result.toString(),"$.message");
		
		if (message.equalsIgnoreCase("SUCCESS")) {
			request.getSession().setAttribute("project_user_delete", "SUCCESS");
		}

		response.sendRedirect("admin/project_details.jsp");
		

	}

}
