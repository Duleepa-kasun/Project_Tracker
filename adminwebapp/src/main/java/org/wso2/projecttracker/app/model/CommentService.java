package org.wso2.projecttracker.app.model;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;

public class CommentService {
	
	public String createComment(String comment,String dateTime,String username,String subtaskId,String token){
		//String serviceEndpoint = AppUtil.getServicePath("create.comment");
		String serviceEndpoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/commentService/comment/";
		
		JSONObject createResponse = null;
		String payload = "<comment><comment>"+comment+"</comment>"
				+ "<username>"+username+"</username>"
						+ "<subtask><subtaskId>"+subtaskId+"</subtaskId></subtask>"
								+ "<dateTime>"+dateTime+"</dateTime></comment>";
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
	
	public JSONObject getCommentsBySubtaskId(String subtaskId,String token){
		//String serviceEndpoint = AppUtil.getServicePath("comment.getCommentsBySubtask")+subtaskId;
		String serviceEndpoint = "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/commentService/getCommentsBySubtaskId/"+subtaskId;
		
		JSONObject comments = null;
		
		try {
			comments = RestClient.sendRequest("GET", serviceEndpoint, "", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
}
