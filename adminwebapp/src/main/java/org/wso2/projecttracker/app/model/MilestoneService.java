package org.wso2.projecttracker.app.model;

import java.io.IOException;


import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;


public class MilestoneService {
	public JSONObject createMilestone(String milestoneName , String milestoneDate, String milestoneDescription,String token, String projectId){
		//String serviceEndPointPath = AppUtil.getServicePath("milestone.createMilestone");
		
		String serviceEndPointPath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/milestoneservice/milestone/";
		
		String payload = "<milestone>"
				+ "<name>"+milestoneName+"</name>"
				+ "<endDate>"+milestoneDate+"</endDate>"
				+ "<description>"+milestoneDescription+"</description>"
				+ "<project>"
				+ "<projectId>"+projectId+"</projectId>"
				+ "</project>"
				+ "</milestone>";
		
		JSONObject milestoneMessage = null;
		
		
		try {
			milestoneMessage = RestClient.sendRequest("POST", serviceEndPointPath, payload, token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return milestoneMessage;
	}
	
	public JSONObject getAllMilestones(String token,String projectId){
		//String serviceEndPointPath = AppUtil.getServicePath("milestone.getAllMilestones")+projectId;
		String serviceEndPointPath = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/milestoneservice/getMilestonesByProjectId/"+projectId;
		
		JSONObject milestones = null;
		try {
			milestones = RestClient.sendRequest("GET", serviceEndPointPath,"" , token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return milestones;
	}
}
