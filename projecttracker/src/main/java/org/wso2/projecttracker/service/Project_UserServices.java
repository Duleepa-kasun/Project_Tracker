package org.wso2.projecttracker.service;

import org.wso2.projecttracker.bean.User;
import org.wso2.projecttracker.bean.Users;
import org.wso2.projecttracker.bean.Project;
import org.wso2.projecttracker.bean.Projects;
import org.wso2.projecttracker.dao.UserProjectDAO;
import org.wso2.projecttracker.daoImpl.UserProjectDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.util.List;

@Path("/project_user/")
public class Project_UserServices {

    @GET
    @Path("/getProjectListByUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectListByUser(@PathParam("userId")Integer userId,@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(userId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("userId parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }
        UserProjectDAO userProjectDAO = new UserProjectDAOImpl();

        List<Project> projectList = userProjectDAO.getProjectListByUser(userId);
        Projects projects = new Projects();
        projects.setProjects(projectList);
        return Response.ok(projects).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getUserListByProject/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserListByProject(@PathParam("projectId")Integer projectId,@HeaderParam("Authorization") String token) {
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(projectId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory").type(MediaType.APPLICATION_JSON)
                            .build()
            );
        }
        UserProjectDAO userProjectDAO = new UserProjectDAOImpl();

        List<User> userList = userProjectDAO.getUserListByProject(projectId);
        Users users = new Users();
        users.setUsers(userList);
        return Response.ok(users).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/project_User/{projectId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject(@PathParam("projectId")Integer projectId, @PathParam("userId")Integer userId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(userId==null||projectId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("mandatory parameter is missing")
                            .build()
            );
        }else{
            UserProjectDAO userProjectDAO = new UserProjectDAOImpl();
            String message = userProjectDAO.assigningProjectToUser(userId,projectId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/projectId/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProject_UserByProjectId(@PathParam("projectId")Integer projectId,@HeaderParam("Authorization") String token){
        
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
            UserProjectDAO userProjectDAO = new UserProjectDAOImpl();
            String message = userProjectDAO.deleteUserProjectByProject(projectId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProject_UserByUserId(@PathParam("userId")Integer userId,@HeaderParam("Authorization") String token){
    	
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(userId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("userId parameter is mandatory")
                            .build()
            );
        }else{
            UserProjectDAO userProjectDAO = new UserProjectDAOImpl();
            String message = userProjectDAO.deleteUserProjectByProject(userId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }


    @DELETE
    @Path("/userproject/{userId}/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProject_UserByUserId(@PathParam("userId")Integer userId,@PathParam("projectId")Integer projectId,@HeaderParam("Authorization") String token){
       
        if(Authorization.getuserlevel(token)<2){
            return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }

        if(userId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("userId parameter is mandatory")
                            .build()
            );
        }else{
            UserProjectDAO userProjectDAO = new UserProjectDAOImpl();
            String message = userProjectDAO.removeUserFromProject(projectId, userId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

}
