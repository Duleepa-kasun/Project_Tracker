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

import org.wso2.projecttracker.bean.Comment;
import org.wso2.projecttracker.bean.Comments;
import org.wso2.projecttracker.dao.CommentDAO;
import org.wso2.projecttracker.daoImpl.CommentDAOImpl;
import org.wso2.projecttracker.security.Authorization;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;
import java.util.List;




@Path("/commentService/")
public class CommentService {
    @GET
    @Path("/getCommentsBySubtaskId/{subtaskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsBySubtaskId(@PathParam("subtaskId")Integer subtaskId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(subtaskId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("subtaskId parameter is mandatory")
                            .build()
            );
        }else{
            CommentDAO commentDAO = new CommentDAOImpl();
            List<Comment> commentList = commentDAO.getAllCommentsBySubtaskId(subtaskId);
            Comments comments =  new Comments();
            comments.setComments(commentList);
            return Response.ok(comments).type(MediaType.APPLICATION_JSON).build();
        }
    }



    @GET
    @Path("/getCommentsByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByUsername(@PathParam("username")String username,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(username==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        }else{
            CommentDAO commentDAO = new CommentDAOImpl();
            List<Comment> commentList = commentDAO.getCommentsByUsername(username);
            Comments comments =  new Comments();
            comments.setComments(commentList);
            return Response.ok(comments).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/getComment/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@PathParam("commentId")Integer commentId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<1){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(commentId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        }else{
            CommentDAO commentDAO = new CommentDAOImpl();
            Comment comment = commentDAO.getComment(commentId);
            return Response.ok(comment).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createComment(Comment comment,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(comment.getComment()==null||comment.getUsername()==null||comment.getDateTime()==null||comment.getSubtask().getSubtaskId()==null) {
        	throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        }else{
            CommentDAO commentDAO = new CommentDAOImpl();
            String message = commentDAO.createComment(comment);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }


    @PUT
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editComment(Comment comment,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(comment.getCommentId()==null||comment.getComment()==null||comment.getUsername()==null||comment.getDateTime()==null||comment.getSubtask().getSubtaskId()==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("projectId parameter is mandatory")
                            .build()
            );
        }else{
            CommentDAO commentDAO = new CommentDAOImpl();
            Comment commentRetrieved  = commentDAO.getComment(comment.getCommentId());
            String message;
            if(commentRetrieved.getCommentId()!=null) {
                message = commentDAO.editComment(comment);
            }else {
                message ="Comment not found";

            }
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+ "}").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/comment/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("commentId")Integer commentId,@HeaderParam("Authorization") String token){
        
    	if(Authorization.getuserlevel(token)<2){
        	return Response.status(401).entity("{"+ '"'+"code"+'"'+":"+ '"'+"Unauthorized"+'"'+ "}").build();
        }
    	
    	if(commentId==null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("commentId parameter is mandatory")
                            .build()
            );
        }else{
            CommentDAO commentDAO = new CommentDAOImpl();
            String message = commentDAO.removeComment(commentId);
            return Response.ok("{"+ '"'+"message"+'"'+":"+ '"'+message+'"'+"}").build();
        }
    }

}
