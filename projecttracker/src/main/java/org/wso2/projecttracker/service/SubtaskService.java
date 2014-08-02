/*
 * Copyright 2014-2015 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.projecttracker.service;

import org.wso2.projecttracker.bean.Subtask;
import org.wso2.projecttracker.bean.Subtasks;
import org.wso2.projecttracker.dao.SubTaskDAO;
import org.wso2.projecttracker.daoImpl.SubtaskDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.util.List;



@Path("/subtaskservice/")
public class SubtaskService {

    @GET
    @Path("/getAllSubtasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubtasks(@HeaderParam("Authorization") String token){
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
        List<Subtask> subtaskList = subTaskDAO.getAll();
        Subtasks subtasks= new Subtasks();
        subtasks.setSubtasks(subtaskList);
        return Response.ok(subtasks).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getSubtasksByTaskId/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubtasksByTaskId(@PathParam("taskId") Integer taskId,@HeaderParam("Authorization") String token){
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(taskId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("taskId parameter is mandatory")
                            .build()
            );
        }else{
            SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
            List<Subtask> subtaskList = subTaskDAO.getAllbyTaskId(taskId);
            Subtasks subtasks= new Subtasks();
            subtasks.setSubtasks(subtaskList);
            return Response.ok(subtasks).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getSubTaskById/{subtaskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubtaskById(@PathParam("subtaskId")Integer subtaskId,@HeaderParam("Authorization") String token) {

    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
        if(subtaskId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("subtaskId parameter is mandatory")
                            .build()
            );
        }
        else{
            SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
            Subtask subtask = subTaskDAO.getSubtaskById(subtaskId);
            return Response.ok(subtask).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getSubtaskByName/{subtaskName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubtaskByName(@PathParam("subtaskName")String subtaskName,@HeaderParam("Authorization") String token) {

    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
        if(subtaskName==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("subtaskName parameter is mandatory")
                            .build()
            );
        }
        else{
            SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
            Subtask subtask = subTaskDAO.getSubtaskByName(subtaskName);
            return Response.ok(subtask).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/searchSubtaskByName/{subtaskName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSubtaskByName(@PathParam("subtaskName")String subtaskName,@HeaderParam("Authorization") String token) {

    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
        if(subtaskName==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("subtaskName parameter is mandatory")
                            .build()
            );
        }
        else{
            SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
            List<Subtask> subtaskList = subTaskDAO.searchSubtaskByName(subtaskName);
            Subtasks subtasks = new Subtasks();
            subtasks.setSubtasks(subtaskList);
            return Response.ok(subtasks).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/subtask/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createSubTask(Subtask subtask,@HeaderParam("Authorization") String token){

    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
        if(subtask.getTask().getTaskId()==null||subtask.getSubTask()==null||subtask.getStartDate()==null||subtask.getEndDate()==null) {
            
        	System.out.println(subtask.getTask().getTaskId()+" "+subtask.getSubTask()+" ");
        	throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{

            SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
            String message = subTaskDAO.createSubtask(subtask);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }



    @DELETE
    @Path("/subtask/{subtaskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubTask(@PathParam("subtaskId")Integer subtaskId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(subtaskId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("taskId parameter is mandatory")
                            .build()
            );
        }else{
            SubTaskDAO subtaskDAO = new SubtaskDAOImpl();
            String message = subtaskDAO.deleteSubtask(subtaskId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").build();
        }
    }


    @PUT
    @Path("/subtask/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editTask(Subtask subtask,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(subtask.getEndDate()==null||subtask.getStartDate()==null||subtask.getSubtaskId()==null||subtask.getTask().getTaskId()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{
            SubTaskDAO subTaskDAO = new SubtaskDAOImpl();
            Subtask subtaskRetrieved = subTaskDAO.getSubtaskById(subtask.getSubtaskId());
            String message;
            if(subtaskRetrieved.getSubtaskId()!=null) {
                message = subTaskDAO.editSubtask(subtask);
            }else {
                message ="Subtask not found";
            }
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }



}
