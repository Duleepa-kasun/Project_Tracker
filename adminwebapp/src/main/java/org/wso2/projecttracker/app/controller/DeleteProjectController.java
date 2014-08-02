package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.ProjectService;
import org.wso2.projecttracker.app.model.UserService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class DeleteProjectController
 */
@WebServlet("/DeleteProjectController")
public class DeleteProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProjectController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String project_id = request.getParameter("delete_project_id");
		String token = (String) request.getSession().getAttribute("token");

		ProjectService projectService=new ProjectService();
		
		JSONObject result = projectService.deleteProject(project_id, token);

		String status = JsonPath.read(result.toString(), "$.message").toString();
		
		/*System.out.println("**************"+status);*/
		if (status.equalsIgnoreCase("SUCCESS")) {
			request.getSession().setAttribute("project_delete", "SUCCESS");
		}

		response.sendRedirect("admin/project.jsp");
	}

}
