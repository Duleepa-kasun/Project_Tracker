package org.wso2.projecttracker.app.model;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;

import com.jayway.jsonpath.JsonPath;

public class UserService {
	public String createUser(String username, String password, String email, String role, String name){
		//String servicePathUsername = AppUtil.getServicePath("user.getUserByUsername");
	//	String servicePathEmail = AppUtil.getServicePath("user.getUserByEmail");
	//	String servicePathCreateUser = AppUtil.getServicePath("user.create");
		
		String servicePathUsername ="https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/searchUser/";
		String servicePathEmail = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/getUserByEmail/";
		String servicePathCreateUser = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/user/";
		
		String usernamePath = servicePathUsername.concat(username);
		String emailPath = servicePathEmail.concat(email+"/");
		String s1 = null;
		String s2 = null;
		String s3 = null;
		try {
			JSONObject jsonObjectUsername = RestClient.sendRequest("GET", usernamePath, "");
			JSONObject jsonObjectEmail = RestClient.sendRequest("GET", emailPath, "");
	
			
			s1 = JsonPath.read(jsonObjectUsername.toString(), "$.users").toString();
			s2 = JsonPath.read(jsonObjectEmail.toString(),"$.user" ).toString();
			if(!s1.trim().equalsIgnoreCase("")||!s2.trim().equalsIgnoreCase("")){
				return "EXISTS";
			}else{
				String payload = "<user><username>"+username+"</username>"
						+ "<password>"+password+"</password>"
								+ "<role>"+role+"</role>"
										+ "<name>"+name+"</name>"
												+ "<email>"+email+"</email>"
														+ "</user>";
				JSONObject jsonObjectCreated = RestClient.sendRequest("POST", servicePathCreateUser, payload);
				s3 = JsonPath.read(jsonObjectCreated.toString(), "$.message");
				return s3;
			}
			
			
		} catch (JSONException e) {
			return e.toString();
		} catch (IOException e) {
			return e.toString();
		}
	}
	
	public JSONObject getAllUsers(String token){
		//String getAllServicePath  = AppUtil.getServicePath("user.getAll");
		String getAllServicePath  = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/getAllUsers/";
		JSONObject allUsers = null;
		try {
			allUsers = RestClient.sendRequest("GET", getAllServicePath, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allUsers;
	}
	
	public JSONObject getUserById(String id,String token){
		//String getUserByIdPath = AppUtil.getServicePath("user.getUserById")+id;
		
		String getUserByIdPath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/getUserById/"+id;
		
		JSONObject user = null;
		try {
			user = RestClient.sendRequest("GET", getUserByIdPath, "",token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public JSONObject deleteUser(String id, String token) {
		//String deleteUserPath = AppUtil.getServicePath("user.delete")
		String deleteUserPath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/user/"	+ id;
		JSONObject user = null;
		try {
			user = RestClient.sendRequest("DELETE", deleteUserPath, "", token);
			System.out.println(user.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public JSONObject getUserByEmail(String email,String token){
		//String serviceEndpoint = AppUtil.getServicePath("user.getUserByEmail")+email;
		
		String serviceEndpoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/getUserByEmail/"+email;
		
		JSONObject userObject = null;
		
		try {
			userObject = RestClient.sendRequest("GET", serviceEndpoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userObject;
	}

}
