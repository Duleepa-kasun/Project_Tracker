/**
 * Copyright (c) 2014-2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.projecttracker.service;

import org.wso2.projecttracker.bean.Project;
import org.wso2.projecttracker.bean.Projects;
import org.wso2.projecttracker.dao.ProjectDAO;
import org.wso2.projecttracker.daoImpl.ProjectDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.util.List;

@Path("/projectservices/")
public class ProjectService {

    @GET
    @Path("/getAllProjects/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjects(@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	ProjectDAO projectDAO = new ProjectDAOImpl();
        List<Project> projectList = projectDAO.getAll();
        Projects projects = new Projects();
        projects.setProjects(projectList);
        return Response.ok(projects).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getProjectById/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectById(@PathParam("projectId")Integer projectId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(projectId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }else{
            ProjectDAO projectDAO = new ProjectDAOImpl();
            Project project = projectDAO.getProjectById(projectId);
            return Response.ok(project).build();
        }
    }

    @GET
    @Path("/getProjectByShortName/{shortName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectByShortName(@PathParam("shortName")String shortName,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(shortName==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }else {
            ProjectDAO projectDAO = new ProjectDAOImpl();
            Project project = projectDAO.getProjectByShortName(shortName);
            return Response.ok(project).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getProjectByTitle/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectByTitle(@PathParam("title")String title,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(title==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }else {
            ProjectDAO projectDAO = new ProjectDAOImpl();
            Project project = projectDAO.getProjectByTitle(title);
            return Response.ok(project).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getProjectsByStatus/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsByStatus(@PathParam("status")String status,@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(status==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }else {
            ProjectDAO projectDAO = new ProjectDAOImpl();
            List<Project> projectList = projectDAO.getProjectByStatus(status);
            Projects projects = new Projects();
            projects.setProjects(projectList);
            return Response.ok(projects).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/searchProjectsByShortName/{shortName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProjectsByShortName(@PathParam("shortName")String shortName,@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(shortName==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("shortName parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }else {
            ProjectDAO projectDAO = new ProjectDAOImpl();
            List<Project> projectList = projectDAO.searchProjectByShortName(shortName);
            Projects projects = new Projects();
            projects.setProjects(projectList);
            return Response.ok(projects).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/searchProjectsByTitle/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProjectsByTitle(@PathParam("title")String title,@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(title==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("title parameter is mandatory")
                            .build()
            );
        }else {
            ProjectDAO projectDAO = new ProjectDAOImpl();
            List<Project> projectList = projectDAO.searchProjectByTitle(title);
            Projects projects = new Projects();
            projects.setProjects(projectList);
            return Response.ok(projects).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProject(@PathParam("projectId")Integer projectId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(projectId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        }else{
            ProjectDAO projectDAO = new ProjectDAOImpl();
            String message = projectDAO.deleteProject(projectId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+"}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/project/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createProject(Project project,@HeaderParam("Authorization") String token){
        //String title, String description,String shortName,String startDate,String endDate,String status
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(project.getTitle()==null||project.getShortName()==null||project.getStartDate()==null||project.getEndDate()==null||project.getStatus()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{
            ProjectDAO projectDAO = new ProjectDAOImpl();
            String message = projectDAO.createProject(project);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/project/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editProject(Project project,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(project.getTitle()==null||project.getShortName()==null||project.getStartDate()==null||project.getEndDate()==null||project.getStatus()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{
            ProjectDAO projectDAO = new ProjectDAOImpl();
            Project projectRetrieved = projectDAO.getProjectById(project.getProjectId());
            String message;
            if(projectRetrieved.getProjectId()!=null) {
                if (project.getDescription() != null || !project.getDescription().trim().equalsIgnoreCase("")) {
                    project.setDescription(projectRetrieved.getDescription());
                }
                message = projectDAO.editProject(project);
            }else {
                message ="Project not found";

            }
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

}
