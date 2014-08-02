package org.wso2.projecttracker.app.model;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;

public class SubTaskService {
	public JSONObject getAllSubtasksByTaskId(String taskId,String token){
		//String serviceEndPoint = AppUtil.getServicePath("subtask.getSubTasksByTask")+taskId;
		
		String serviceEndPoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/subtaskservice/getSubtasksByTaskId/"+taskId;
		
		JSONObject tasks =null;
		try {
			tasks = RestClient.sendRequest("GET", serviceEndPoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;
	}
	
	public String createSubtask(String subtask,String description, String startDate,String endDate,String taskId,String token){
		JSONObject createResponse = null;
//		String serviceEndpoint = AppUtil.getServicePath("create.subtask");
		String serviceEndpoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/subtaskservice/subtask/";
		
		String payload = "<subtask>"
				+ "<subTask>"+subtask+"</subTask>"
						+ "<description>"+description+"</description>"
								+ "<startDate>"+startDate+"</startDate>"
										+ "<endDate>"+endDate+"</endDate>"
												+ "<task>"
												+ "<taskId>"+taskId+"</taskId>"
												+ "</task>"
				+ "</subtask>";
		try {
			createResponse = RestClient.sendRequest("POST", serviceEndpoint, payload, token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return createResponse.toString();
	}

}
