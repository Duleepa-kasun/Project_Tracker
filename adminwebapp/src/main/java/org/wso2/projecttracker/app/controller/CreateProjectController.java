package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.parser.JSONParser;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.ProjectService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class CreateProjectController
 */
@WebServlet("/CreateProjectController")
public class CreateProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProjectController() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String projectTitle = request.getParameter("project_title");
		String shortName = request.getParameter("short_name");
		String projectDescription  = request.getParameter("project_description");
		String startDate = request.getParameter("project_start_date");
		String endDate = request.getParameter("project_end_date");
		
		String token = (String)request.getSession().getAttribute("token");
		ProjectService projectService = new ProjectService();
		JSONObject projectMessage = projectService.createProject(projectTitle, shortName, projectDescription, startDate, endDate,token);
		String s = JsonPath.read(projectMessage.toString(), "$.message").toString();
		if(s.equalsIgnoreCase("SUCCESS")){
			request.getSession().setAttribute("project_create", "SUCCESS");
			response.sendRedirect("admin/project.jsp");
		}else{
			request.getSession().setAttribute("project_create", "ERROR");
			response.sendRedirect("admin/project.jsp");
		}
	}

}
