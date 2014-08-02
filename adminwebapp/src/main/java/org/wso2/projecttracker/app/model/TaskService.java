package org.wso2.projecttracker.app.model;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;
import com.jayway.jsonpath.JsonPath;




public class TaskService {
	
	public JSONObject getAllTasksByMilestone(String milestoneId,String token){
//		String serviveEndPoint = AppUtil.getServicePath("milestone.getTasksByMilestones")+milestoneId;
		String serviveEndPoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/taskservices/getAllbyMilestoneId/"+milestoneId;
		
		JSONObject tasks = null;
		try {
			tasks = RestClient.sendRequest("GET", serviveEndPoint, "", token);
			} catch (JSONException e) {
		} catch (IOException e) {

		}
		return tasks;
	}
	
	public String createTask(String token,String task,String projectId,String milestoneId){
		//String serviceEndpoint = AppUtil.getServicePath("task.createTask");

		String serviceEndpoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/taskservices/task/";
		
String payload	="<task><task>"+task+"</task>"
		+ "<project>"
		+ "<projectId>"+projectId+"</projectId></project>	"
				+ "<milestone><milestoneId>"+milestoneId+"</milestoneId></milestone>	</task>";
		
		JSONObject created = null;
		try {
			created = RestClient.sendRequest("POST", serviceEndpoint, payload, token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String createdRespone = JsonPath.read(created.toString(), "$.message").toString();
		return createdRespone;
		
	}
	
	

}
