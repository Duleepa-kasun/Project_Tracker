package org.wso2.projecttracker.app.model;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;

public class ProjectService {
	public JSONObject createProject(String title , String shortName, String projectDescription, String startDate, String endDate, String token){
//		String createServicePath = AppUtil.getServicePath("project.createProjectServices");
		String createServicePath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/projectservices/project";
		
		JSONObject projectMessage = null;
	
		String payload = "<project>"
				+ "<title>"+title+"</title>"
				+ "<description>"+projectDescription+"</description>"
				+ "<shortName>"+shortName+"</shortName>"
				+ "<startDate>"+startDate+"</startDate>"
				+ "<endDate>"+endDate+"</endDate>"
				+ "<status>pending</status>"
				+ "</project>";
		
		try {
			projectMessage = RestClient.sendRequest("POST", createServicePath, payload,token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectMessage;
	}
	
	
	public JSONObject getAllProjects(String token){
	//	String serviceEndpointPath = AppUtil.getServicePath("project.getAll");
		
		String serviceEndpointPath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/projectservices/getAllProjects";
		
		JSONObject allProjects = null;
		try {
			allProjects = RestClient.sendRequest("GET", serviceEndpointPath, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allProjects;
	}
	

	public JSONObject getProjectById(String token,String projectId){
//		String serviceEndpointPath = AppUtil.getServicePath("project.getById")+projectId;
		String serviceEndpointPath ="https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/projectservices/getProjectById/"+projectId;
		
		JSONObject project = null;
		try {
			project = RestClient.sendRequest("GET", serviceEndpointPath, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return project;
	}
	public JSONObject editProject(String projectId,String title, String shortName,
			String projectDescription, String startDate, String endDate,
			String token) {
		//String createServicePath = AppUtil.getServicePath("project.editProject");
		
		String createServicePath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/projectservices/project";
		
		JSONObject projectMessage = null;

		String payload = "<project><projectId>"+projectId+"</projectId>" + "<title>" + title + "</title>"
				+ "<description>" + projectDescription + "</description>"
				+ "<shortName>" + shortName + "</shortName>" + "<startDate>"
				+ startDate + "</startDate>" + "<endDate>" + endDate
				+ "</endDate>" + "<status>pending</status>" + "</project>";

		try {
			projectMessage = RestClient.sendRequest("PUT", createServicePath,
					payload, token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectMessage;
	}
	
	public JSONObject deleteProject(String id,String token){
		//String deleteProjectPath = AppUtil.getServicePath("project.delete")+ id;
		
		String deleteProjectPath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/projectservices/project/"+ id;
		
		
		JSONObject project = null;
		try {
			project = RestClient.sendRequest("DELETE", deleteProjectPath, "", token);
			System.out.println(project.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}
}
