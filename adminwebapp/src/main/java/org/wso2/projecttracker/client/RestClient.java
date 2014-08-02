package org.wso2.projecttracker.client;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {
	
	 public static JSONObject sendRequest(String httpMethod, String addUrl, String query) throws IOException, JSONException {

         String charset = "UTF-8";
         URLConnection con = new URL(addUrl).openConnection();
         HttpURLConnection connection = (HttpURLConnection) con;
         connection.setRequestMethod(httpMethod);
         connection.setRequestProperty("Accept-Charset", charset);
         connection.setRequestProperty("Content-Type", "application/xml");

         if(!httpMethod.equals("GET") && !httpMethod.equals("DELETE")){
             connection.setDoOutput(true);
             OutputStream output = null;
             try {
                 output = connection.getOutputStream();
                 if(query!=null){
                     output.write(query.getBytes(charset));
                 }
             } finally {
                 if (output != null) {
                     try {
                         output.close();
                     } catch (IOException logOrIgnore) {
                     }
                 }
             }
         }

 // HttpURLConnection httpConn = (HttpURLConnection) connection;
         InputStream response;

         if (connection.getResponseCode() >= 400) {
             response = connection.getErrorStream();
         } else {
             response = connection.getInputStream();
         }

         String out = "{}";
         if (response != null) {
             StringBuilder sb = new StringBuilder();
             byte[] bytes = new byte[1024];
             int len;
             while ((len = response.read(bytes)) != -1) {
                 sb.append(new String(bytes, 0, len));
             }

             if (!sb.toString().trim().isEmpty()) {
                 out = sb.toString();
             }
         }
         JSONObject jsonObject = new JSONObject(out);

         return jsonObject;
     }
	 
	 public static JSONObject sendRequest(String httpMethod, String addUrl,
	            String query,String token) throws IOException, JSONException {

	        String charset = "UTF-8";
	        URLConnection con = new URL(addUrl).openConnection();
	        HttpURLConnection connection = (HttpURLConnection) con;
	        connection.setRequestMethod(httpMethod);
	        connection.setRequestProperty("Content-Type","application/xml");
	        connection.setRequestProperty("Authorization",token);
	        if (!httpMethod.equals("GET") && !httpMethod.equals("DELETE")) {
	            connection.setDoOutput(true);
	            // connection.setRequestProperty("Accept-Charset", charset);
	            // connection.setRequestProperty("Content-Type","application/json;charset=" + charset);
	            OutputStream output = null;
	            try {
	                output = connection.getOutputStream();
	                if (query != null) {
	                    output.write(query.getBytes(charset));
	                }
	            } finally {
	                if (output != null) {
	                    try {
	                        output.close();
	                    } catch (IOException logOrIgnore) {
	                        System.out.print("Error while closing the connection");
	                    }
	                }
	            }
	        }

	        // HttpURLConnection httpConn = (HttpURLConnection) connection;
	        InputStream response;

	        if (connection.getResponseCode() >= 400) {
	            response = connection.getErrorStream();
	        } else {
	            response = connection.getInputStream();
	        }

	        String out = "{}";
	        if (response != null) {
	            StringBuilder sb = new StringBuilder();
	            byte[] bytes = new byte[1024];
	            int len;
	            while ((len = response.read(bytes)) != -1) {
	                sb.append(new String(bytes, 0, len));
	            }

	            if (!sb.toString().trim().isEmpty()) {
	                out = sb.toString();
	            }
	        }
	        JSONObject jsonObject = new JSONObject(out);

	        return jsonObject;
	    }
	 
	

}
