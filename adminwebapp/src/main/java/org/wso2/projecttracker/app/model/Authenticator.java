package org.wso2.projecttracker.app.model;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.projecttracker.app.util.AppUtil;
import org.wso2.projecttracker.client.RestClient;



public class Authenticator {
	public JSONObject authenticate(String email,String password){
		String query = "<user><username>"+email+"</username><password>"+password+"</password></user>";
		JSONObject jsonObject = null;
		try {
			String path = AppUtil.getServicePath("user.login");
			jsonObject = RestClient.sendRequest("POST", "https://appserver.dev.cloud.wso2.com/t/irshadhassan/webapps/projecttracker-1.0.4/services/projects/userservice/login/", query);
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
		
	}

}
