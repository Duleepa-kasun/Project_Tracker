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

import org.wso2.projecttracker.bean.Milestone;
import org.wso2.projecttracker.bean.Milestones;
import org.wso2.projecttracker.dao.MilestoneDAO;
import org.wso2.projecttracker.daoImpl.MilestoneDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.util.List;


@Path("/milestoneservice/")
public class MilestoneServices {

    @GET
    @Path("/getAllMilestones/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMilestones(@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
        List<Milestone> milestoneList = milestoneDAO.getAllMilestones();
        Milestones milestones = new Milestones();
        milestones.setMilestones(milestoneList);
        return Response.ok(milestones).build();
    }


    @GET
    @Path("/getMilestoneById/{milestoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMilestoneById(@PathParam("milestoneId")Integer milestoneId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(milestoneId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("milestoneId parameter is mandatory")
                            .build()
            );
        }else{
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            Milestone milestone = milestoneDAO.getMilestoneById(milestoneId);
            return Response.ok(milestone).build();
        }
    }

    @GET
    @Path("/getMilestoneByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMilestoneByName(@PathParam("name")String name,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(name==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("name parameter is mandatory")
                            .build()
            );
        }else{
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            Milestone milestone = milestoneDAO.getMilestoneByName(name);
            return Response.ok(milestone).build();
        }
    }

    @GET
    @Path("/getMilestonesByProjectId/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMilestonesByProjectId(@PathParam("projectId")Integer projectId,@HeaderParam("Authorization") String token) {

    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
        if(projectId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        }
        else{
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            List<Milestone> milestoneList = milestoneDAO.getMilestonesByProjectId(projectId);
            Milestones milestones = new Milestones();
            milestones.setMilestones(milestoneList);
            return Response.ok(milestones).build();
        }
    }

    @GET
    @Path("/getProjectMilestone/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectMilestone(@QueryParam("projectId")Integer projectId,@QueryParam("name")String name,@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if (projectId == null || name == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }
        else
        {
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            Milestone milestone = milestoneDAO.getProjectMilestone(projectId,name);
            return Response.ok(milestone).build();
        }
    }

    @GET
    @Path("/searchMilestoneByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMilestoneByName(@PathParam("name")String name,@HeaderParam("Authorization") String token) {

    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
        if(name==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("name parameter is mandatory")
                            .build()
            );
        }
        else{
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            List<Milestone> milestoneList = milestoneDAO.searchMilestoneByName(name);
            Milestones milestones = new Milestones();
            milestones.setMilestones(milestoneList);
            return Response.ok(milestones).build();
        }
    }

    @POST
    @Path("/milestone/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createMilestone(Milestone milestone,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(milestone.getProject()==null||milestone.getEndDate()==null||milestone.getName()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            String message = milestoneDAO.createMilestone(milestone);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/milestone/{milestoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMilestone(@PathParam("milestoneId")Integer milestoneId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(milestoneId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("milestoneId parameter is mandatory")
                            .build()
            );
        }else{
            MilestoneDAO milestoneDAO =new MilestoneDAOImpl();
            String message = milestoneDAO.deleteMilestone(milestoneId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").build();
        }
    }


    @PUT
    @Path("/milestone/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editMilestone(Milestone milestone,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(milestone.getProject()==null||milestone.getMilestoneId()==null||milestone.getEndDate()==null||milestone.getName()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{
            MilestoneDAO milestoneDAO = new MilestoneDAOImpl();
            Milestone milestoneRetrieved = milestoneDAO.getMilestoneById(milestone.getMilestoneId());
            String message;
            if(milestoneRetrieved.getMilestoneId()!=null) {
                if (milestone.getDescription() != null || !milestone.getDescription().trim().equalsIgnoreCase("")) {
                    milestone.setDescription(milestoneRetrieved.getDescription());
                }
                message = milestoneDAO.editMilestone(milestone);
            }else {
                message ="Project not found";

            }
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }


}
