package org.wso2.projecttracker.app.model;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;

public class UserProjectService {
	public String assignUser(String projectId,String userId,String token){
//		String serviceEndPoint = AppUtil.getServicePath("userproject.create")+"/"+projectId+"/"+userId+"/";
		
		String serviceEndPoint ="https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/project_user/project_User"+"/"+projectId+"/"+userId+"/";
		
		JSONObject messageResponse = null;
		try {
			messageResponse = RestClient.sendRequest("POST", serviceEndPoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageResponse.toString();
	}
	
	public JSONObject getProjectByUserId(String userId, String token){
//		String serviceEndpoint = AppUtil.getServicePath("projectuser.getProjects")+userId;
		
		String serviceEndpoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/project_user/getProjectListByUser/"+userId;
		JSONObject projectsByUser = null;
		
		try {
			projectsByUser = RestClient.sendRequest("GET", serviceEndpoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectsByUser;
	}
	
	public String removeUserFromProject(String userId, String projectId, String token){
		//String serviceEndPoint = AppUtil.getServicePath("projectuser.remove")+userId+"/"+projectId;
		
		String serviceEndPoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/project_user/userproject/"+userId+"/"+projectId;
		
		
		JSONObject removeMessage = null;
		
		try {
			removeMessage = RestClient.sendRequest("DELETE", serviceEndPoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return removeMessage.toString();
	}	
	
	public JSONObject getProjectUsers(String projectId,String token){
		//String serviceEndPoint = AppUtil.getServicePath("userproject.getProjectUsers")+projectId;
		
		String serviceEndPoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/project_user/getUserListByProject/"+projectId;
		
		JSONObject users = null;
		
		try {
			users = RestClient.sendRequest("GET", serviceEndPoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

}
