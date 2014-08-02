package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.projecttracker.app.model.TaskService;

/**
 * Servlet implementation class CreateTaskController
 */
@WebServlet("/CreateTaskController")
public class CreateTaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTaskController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String token = (String) request.getSession().getAttribute("token");
		if (token == null) {
			response.sendRedirect("index.jsp");
		} else {
			String projectId = request.getParameter("projectId");
			String milestoneId = request.getParameter("milestoneId");
			String task = request.getParameter("task");
			if (projectId == null || milestoneId == null || task == null) {
				//response.sendRedirect("admin/project.jsp");
				response.setContentType("text/html");
				response.getWriter().print(projectId+"  "+milestoneId+" "+" "+task);
			} else {
				TaskService taskService = new TaskService();
				taskService.createTask(token, task, projectId, milestoneId);
				response.sendRedirect("admin/task.jsp?projectId=" + projectId
						+ "&milestoneId=" + milestoneId);
			}
		}

	}

}
