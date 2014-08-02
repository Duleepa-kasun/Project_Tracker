package org.wso2.projecttracker.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.MilestoneService;

/**
 * Servlet implementation class CreateMilestoneController
 */
@WebServlet("/CreateMilestoneController")
public class CreateMilestoneController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMilestoneController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String milestoneName = request.getParameter("milestone_name");
		String milestoneDate = request.getParameter("milestone_date");
		String milestoneDescription  = request.getParameter("milestone_description");

		String projectId = request.getParameter("projectId");
		
		String token = (String)request.getSession().getAttribute("token");
		if(milestoneName==null||milestoneName.trim().equals("")
				||milestoneDate==null||milestoneDate.trim().equals("")
				||milestoneDescription==null||milestoneDescription.trim().equals("")
				){
			response.sendRedirect("admin/project.jsp");
			
		}else{
			MilestoneService milestoneService = new MilestoneService();
			milestoneService.createMilestone(milestoneName, milestoneDate, milestoneDescription, token, projectId);
			request.getRequestDispatcher("admin/project_details.jsp").forward(request, response);
			
		}
		
	}

}
