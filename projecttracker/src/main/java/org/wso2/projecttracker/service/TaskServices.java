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

import org.wso2.projecttracker.bean.Task;
import org.wso2.projecttracker.bean.Tasks;
import org.wso2.projecttracker.dao.TasksDAO;
import org.wso2.projecttracker.daoImpl.TasksDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.util.List;


@Path("/taskservices/")
public class TaskServices {
    @GET
    @Path("/getAllTasks/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks(@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
        TasksDAO tasksDAO = new TasksDAOImpl();
        List<Task> taskList = tasksDAO.getAll();
        Tasks tasks = new Tasks();
        tasks.setTasks(taskList);
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/getAllbyProjectId/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllbyProjectId(@PathParam("projectId") Integer projectId,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	
    	if (projectId == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            List<Task> taskList = tasksDAO.getAllbyProjectId(projectId);
            Tasks tasks = new Tasks();
            tasks.setTasks(taskList);
            return Response.ok(tasks).build();
        }
    }


    @GET
    @Path("/getAllbyMilestoneId/{milestoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllbyMilestoneId(@PathParam("milestoneId") Integer milestoneId,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	
    	if (milestoneId == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("milestoneId parameter is mandatory")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            List<Task> taskList = tasksDAO.getAllbyMilestoneId(milestoneId);
            Tasks tasks = new Tasks();
            tasks.setTasks(taskList);
            return Response.ok(tasks).build();
        }
    }


    @GET
    @Path("/getTaskById/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskById(@PathParam("taskId") Integer taskId,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	 
        if (taskId == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("taskId parameter is mandatory")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            Task task = tasksDAO.getTaskById(taskId);
            return Response.ok(task).build();
        }
    }

    @GET
    @Path("/getTaskByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskByName(@PathParam("name") String name,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	 
        if (name == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("name parameter is mandatory")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            Task task = tasksDAO.getTaskByName(name);
            return Response.ok(task).build();
        }
    }

    @GET
    @Path("/searchTaskByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTaskByName(@PathParam("name") String name,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<1){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	
    	if (name == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("name parameter is mandatory")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            List<Task> taskList = tasksDAO.searchTaskByName(name);
            Tasks tasks = new Tasks();
            tasks.setTasks(taskList);
            return Response.ok(tasks).build();
        }
    }


    @POST
    @Path("/task/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createTask(Task task,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<2){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	
        if (task.getProject() == null || task.getTask() == null || task.getMilestone() == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        } else {

            TasksDAO tasksDAO = new TasksDAOImpl();
            String message = tasksDAO.createTask(task);
            return Response.ok("{" + '"' + "message" + '"' + ":" + '"' + message + '"' + "}").type(MediaType.APPLICATION_JSON).build();
        }
    }


    @DELETE
    @Path("/task/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("taskId") Integer taskId,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<2){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	 
    	if (taskId == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("taskId parameter is mandatory")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            String message = tasksDAO.deleteTask(taskId);
            return Response.ok("{" + '"' + "message" + '"' + ":" + '"' + message + '"' + "}").build();
        }
    }


    @PUT
    @Path("/task/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editTask(Task task,@HeaderParam("Authorization") String token) {
    	 if(Authorization.getuserlevel(token)<2){
	        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
	        }
    	 
    	if (task.getProject() == null || task.getMilestone() == null || task.getTask() == null || task.getTaskId() == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        } else {
            TasksDAO tasksDAO = new TasksDAOImpl();
            Task taskRetrieved = tasksDAO.getTaskById(task.getTaskId());
            String message;
            if (taskRetrieved.getTaskId() != null) {
                message = tasksDAO.editTask(task);
            } else {
                message = "Project not found";
            }
            return Response.ok("{" + '"' + "message" + '"' + ":" + '"' + message + '"' + "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

}
