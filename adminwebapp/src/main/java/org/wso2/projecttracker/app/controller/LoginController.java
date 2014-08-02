package org.wso2.projecttracker.app.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.wso2.projecttracker.app.model.Authenticator;
import org.wso2.projecttracker.app.util.AppUtil;

import com.jayway.jsonpath.JsonPath;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email == null || email.trim().equalsIgnoreCase("")
				|| password == null || password.trim().equalsIgnoreCase("")
				|| password.length() < 8) {
			request.setAttribute("inputError", "Invalied login");
			response.sendRedirect("index.jsp");
		} else {
			request.setAttribute("inputError", null);
			Authenticator authenticator = new Authenticator();
			JSONObject user = authenticator.authenticate(email, password);
			if (user == null||user.toString().equalsIgnoreCase("{}")||user.toString().equalsIgnoreCase("{"+'"'+"code"+'"'+":"+'"'+'"'+"}")) {
				response.sendRedirect("index.jsp");
			} else {
				String s = JsonPath.read(user.toString(), "$.code").toString();
				if (s.equalsIgnoreCase("unauthorized")) {
					response.sendRedirect("index.jsp");
				} else {
					String token = null;
					String [] codeRes= s.split(":");
					if(!codeRes[1].equalsIgnoreCase("admin")){
						response.sendRedirect("index.jsp");
					}
					try {
						token = AppUtil.encrypt(email + password + codeRes[0]);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// out.print(s);
					request.getSession().setAttribute("user", email);
					request.getSession().setAttribute("token", token);
					// request.getRequestDispatcher("admin/dashboard.jsp").forward(request,
					// response);
					response.sendRedirect("admin/dashboard.jsp");
				}
			}
		}

	}
}
