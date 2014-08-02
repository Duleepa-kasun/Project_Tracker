package org.wso2.projecttracker.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.ProjectService;

/**
 * Servlet implementation class GetProjectDetails
 */
@WebServlet("/GetProjectDetails")
public class GetProjectDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProjectDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub'
		String projectId = request.getParameter("projectId");
		String token = (String)request.getSession().getAttribute("token");
		ProjectService projectService = new ProjectService();
		JSONObject project = projectService.getProjectById(token, projectId);
		request.getSession().setAttribute("project", project);
		response.sendRedirect("admin/project_details.jsp");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String projectId = req.getParameter("projectId");

		String token = (String) req.getSession().getAttribute("token");

		ProjectService projectService = new ProjectService();
		JSONObject project = projectService.getProjectById(token, projectId);
		
		PrintWriter writer=resp.getWriter();
		writer.print(project.toString());
	}

}
