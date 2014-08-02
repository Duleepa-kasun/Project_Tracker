package org.wso2.projecttracker.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.projecttracker.app.model.MilestoneService;

/**
 * Servlet implementation class TaskDetailsController
 */
@WebServlet("/TaskDetailsController")
public class TaskDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskDetailsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("token") == null) {
			response.sendRedirect("index.jsp");
		} else {
			String milestoneId = request.getParameter("milestoneId");
			String projectId = request.getParameter("projectId");
			response.sendRedirect("admin/task.jsp?milestoneId=" + milestoneId+"&projectId="+projectId);
		}
	}

}
