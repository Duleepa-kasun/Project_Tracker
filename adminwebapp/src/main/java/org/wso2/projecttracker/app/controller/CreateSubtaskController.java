package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.projecttracker.app.model.SubTaskService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class CreateSubtaskController
 */
@WebServlet("/CreateSubtaskController")
public class CreateSubtaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSubtaskController() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("taskId");
		String subtask = request.getParameter("sub_task");
		String description = request.getParameter("sub_task_description");
		String startDate = request.getParameter("sub_task_start_date");
		String endDate = request.getParameter("sub_task_end_date");
		String token = (String) request.getSession().getAttribute("token");
		if (token == null) {
			response.sendRedirect("index.jsp");
		} else {
			if (subtask == null || taskId == null || description == null
					|| startDate == null || endDate == null) {
				response.sendRedirect("admin/project.jsp");
			} else {
				SubTaskService subTaskService = new SubTaskService();
				String message = subTaskService.createSubtask(subtask, description, startDate,endDate, taskId, token);
				if(JsonPath.read(message,"$.message").toString().equalsIgnoreCase("success")){
					response.sendRedirect("admin/subtask.jsp?taskId="+taskId);
				}
			}

		}
	}

}
