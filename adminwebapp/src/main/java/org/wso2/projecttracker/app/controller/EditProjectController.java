package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.ProjectService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class EditProjectController
 */
@WebServlet("/EditProjectController")
public class EditProjectController extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String projectId=request.getParameter("projectId");
		String projectTitle = request.getParameter("edit_project_title");
		String shortName = request.getParameter("edit_project_shortname");
		String projectDescription  = request.getParameter("edit_project_description");
		String startDate = request.getParameter("edit_project_startdate");
		String endDate = request.getParameter("edit_project_enddate");
		
		
		
		String token = (String)request.getSession().getAttribute("token");
		
		ProjectService projectService = new ProjectService();
		JSONObject projectMessage = projectService.editProject(projectId,projectTitle, shortName, projectDescription, startDate, endDate, token);

		
		String s = JsonPath.read(projectMessage.toString(), "$.message").toString();
		
		if(s.equalsIgnoreCase("SUCCESS")){
			request.getSession().setAttribute("project_edit", "SUCCESS");
			response.sendRedirect("admin/project.jsp");
		}else{
			request.getSession().setAttribute("project_edit", "ERROR");
			response.sendRedirect("admin/project.jsp");

		}
	}

}
