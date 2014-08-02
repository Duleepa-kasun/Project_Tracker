package org.wso2.projecttracker.app.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.parser.JSONParser;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.CommentService;

import com.jayway.jsonpath.JsonPath;


/**
 * Servlet implementation class CreateCommentController
 */
@WebServlet("/CreateCommentController")
public class CreateCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCommentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = (String)request.getSession().getAttribute("user");
		String token = (String)request.getSession().getAttribute("token");
		String subtaskId= request.getParameter("subtaskId");
		
		
		
		if(token==null){
			response.sendRedirect("index.jsp");
		}else{
			String comment = request.getParameter("comment");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = Calendar.getInstance().getTime();
			String dateTime = sdf.format(date);
			if(comment ==null){
				response.sendRedirect("admin/project.jsp");
			}else{
				CommentService commentService = new CommentService();
				String message = commentService.createComment(comment, dateTime, username, subtaskId, token);
				if(JsonPath.read(message,"$.message").toString().equalsIgnoreCase("success")){
					response.sendRedirect("admin/comment.jsp?subtaskId="+subtaskId);
				}else{
					System.out.println(JsonPath.read(message,"$.message").toString());
					//response.sendRedirect("admin/project.jsp");
				}
			}
			
		}
		
		
	}

}
