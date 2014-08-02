package org.wso2.projecttracker.service;

import org.wso2.projecttracker.bean.User;
import org.wso2.projecttracker.bean.Users;
import org.wso2.projecttracker.dao.UserDAO;
import org.wso2.projecttracker.daoImpl.UserDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

@Path("/userservice/")
public class UserService {


	  @GET
	    @Path("/getAllUsers/")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getAllUsers(@HeaderParam("Authorization") String token){
	    	//Authorization authorization = new Authorization();
	        if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
	    	
	            UserDAO userDAO = new UserDAOImpl();
	            Users users =new Users();
	            users.setUsers(userDAO.getAll());
	            return Response.ok(users).type(MediaType.APPLICATION_JSON).build();

	    }


	    @POST
	    @Path("/login/")
	    @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_XML)
	    public Response login(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException{
	        if(user.getUsername()==null||user.getPassword()==null){
	            throw new WebApplicationException(
	                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
	                            .entity("mandatory parameter is missing")
	                            .build());
	        }
	        else {
			//Authorization authorization = new Authorization();
	        	String code= Authorization.valid(user);
	        	if(code.equals("auth_fail")){
	        		return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        	}
	        	return Response.ok("{"+ '"'+"code"+'"'+":"+ '"'+code+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
	        	}

	        
	    }
	    
	    
    @GET
    @Path("/getUserById/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") Integer userId, @HeaderParam("Authorization") String token){
      
        if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(userId==null){
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("userId parameter is mandatory")
                            .build());
        }
        else {
            UserDAO userDAO = new UserDAOImpl();
            User user = userDAO.getUserById(userId);
            return Response.ok(user).type(MediaType.APPLICATION_JSON).build();

        }

    }

    @GET
    @Path("/searchUser/{match}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUserByUsername(@PathParam("match")String match,@HeaderParam("Authorization") String token){
        if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(match==null){
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("username parameter is mandatory")
                            .build());
        }
        else {
            UserDAO userDAO = new UserDAOImpl();
            Users users = userDAO.searchUser(match);
            return Response.ok(users).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createUser(User user,@HeaderParam("Authorization") String token) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(user.getUsername()==null||user.getPassword()==null||user.getEmail()==null||user.getName()==null||user.getRole()==null){
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build());
        }else {
            UserDAO userDAO = new UserDAOImpl();
            String message = userDAO.createUser(user);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editUser(User user,@HeaderParam("Authorization") String token){
        if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if (user.getUsername()==null||user.getPassword()==null||user.getEmail()==null||user.getName()==null||user.getRole()==null||user.getUserId()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        } else {
            UserDAO userDAO = new UserDAOImpl();
            User userRetrieved = userDAO.getUserById(user.getUserId());
            String message;
            if (userRetrieved.getUserId() != null) {
                message = userDAO.editUser(user);
            } else {
                message = "Project not found";
            }
            return Response.ok("{" + '"' + "message" + '"' + ":" + '"' + message + '"' + "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("userId") Integer userId,@HeaderParam("Authorization") String token) {
        if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if (userId == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("userId parameter is mandatory")
                            .build()
            );
        } else {
            UserDAO userDAO = new UserDAOImpl();
            String message = userDAO.deleteUser(userId);
            return Response.ok("{" + '"' + "message" + '"' + ":" + '"' + message + '"' + "}").build();
        }
    }
    
    @GET
    @Path("/getUserByEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("email")String email,@HeaderParam("Authorization") String token){
        if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(email==null){
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("username parameter is mandatory")
                            .build());
        }
        else {
            UserDAO userDAO = new UserDAOImpl();
            User user = userDAO.getUserByEmail(email);
            return Response.ok(user).type(MediaType.APPLICATION_JSON).build();
        }
    }

}
