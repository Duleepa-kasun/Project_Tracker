package org.wso2.projecttracker.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.UserProjectService;
import org.wso2.projecttracker.app.model.UserService;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class AssignUserProjectController
 */
@WebServlet("/AssignUserProjectController")
public class AssignUserProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AssignUserProjectController() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String token = (String)request.getSession().getAttribute("token");
		String projectId = request.getParameter("user_projectId");
		
		if(email==null||email.trim().equalsIgnoreCase("")){
			response.sendRedirect("admin/project_details.jsp");
		}else{
			UserProjectService userProjectService = new UserProjectService();
			UserService userService = new UserService();
			JSONObject user = userService.getUserByEmail(email, token);
			
			String userId = JsonPath.read(user.toString(),"$.user.userId").toString();
			String message = userProjectService.assignUser(projectId, userId, token);
			request.getSession().setAttribute("user_assign", JsonPath.read(message.toString(), "$.message"));
			response.sendRedirect("admin/project_details.jsp");

		}
	}

}
