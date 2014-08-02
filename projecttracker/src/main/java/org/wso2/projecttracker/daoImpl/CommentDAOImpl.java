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

package org.wso2.projecttracker.daoImpl;

import org.wso2.projecttracker.bean.Comment;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.CommentDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class CommentDAOImpl implements CommentDAO{

    public List<Comment> getAllCommentsBySubtaskId(Integer subtaskId) {
        List<Comment> comments = new ArrayList<Comment>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Comment WHERE subtaskId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	statement = connection.prepareStatement(sql);
            statement.setInt(1,subtaskId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Comment comment = new Comment();
                comment.setCommentId(resultSet.getInt("commentId"));
                comment.setComment(resultSet.getString("comment"));
                comment.setUsername(resultSet.getString("username"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                comment.setDateTime(smdf.parse(resultSet.getString("dateTime")));
                //get the subtask
                SubtaskDAOImpl subTaskDAO = new SubtaskDAOImpl();
                comment.setSubtask(subTaskDAO.getSubtaskById(resultSet.getInt("subtaskId")));

                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(resultSet!=null){
        		try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return comments;
    }

    public List<Comment> getCommentsByUsername(String username) {
        List<Comment> comments = new ArrayList<Comment>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Comment WHERE username=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Comment comment = new Comment();
                comment.setCommentId(resultSet.getInt("commentId"));
                comment.setComment(resultSet.getString("comment"));
                comment.setUsername(resultSet.getString("username"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                comment.setDateTime(smdf.parse(resultSet.getString("dateTime")));
                //get the subtask
                SubtaskDAOImpl subTaskDAO = new SubtaskDAOImpl();
                comment.setSubtask(subTaskDAO.getSubtaskById(resultSet.getInt("subtaskId")));

                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(resultSet!=null){
        		try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return comments;
    }

    public Comment getComment(Integer commentId) {
        Comment comment = new Comment();
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM Comment WHERE commentId=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,commentId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                comment.setCommentId(resultSet.getInt("commentId"));
                comment.setComment(resultSet.getString("comment"));
                comment.setUsername(resultSet.getString("username"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                comment.setDateTime(smdf.parse(resultSet.getString("dateTime")));
                //get the subtask
                SubtaskDAOImpl subTaskDAO = new SubtaskDAOImpl();
                comment.setSubtask(subTaskDAO.getSubtaskById(resultSet.getInt("subtaskId")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(resultSet!=null){
        		try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return comment;
    }

    public String createComment(Comment comment) {
        String sql = "INSERT INTO Comment(comment,username,subtaskId,dateTime) " +
                "VALUES (?,?,?,?)";
        String createComment = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,comment.getComment());
            statement.setString(2,comment.getUsername());
            statement.setInt(3, comment.getSubtask().getSubtaskId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            statement.setString(4,sdf.format(comment.getDateTime()));
            statement.executeUpdate();
            createComment = "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	
        }
        return createComment;
    }

    public String editComment(Comment comment) {
        String sql = "UPDATE Comment SET comment = ? , username = ?, dateTime = ?" +
                " WHERE commentId = ?";
        String editComment = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
        	statement = connection.prepareStatement(sql);
            statement.setString(1,comment.getComment());
            statement.setString(2,comment.getUsername());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            statement.setString(3,sdf.format(comment.getDateTime()));
            statement.setInt(4,comment.getCommentId());
            statement.executeUpdate();
            editComment = "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return editComment;
    }

    public String removeComment(Integer commentId) {
        String sql = "DELETE FROM Comment Where commentId = ?";
        String removeComment = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {         
            statement = connection.prepareStatement(sql);
            statement.setInt(1,commentId);
            statement.executeUpdate();
            removeComment = "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(statement!=null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return  removeComment;
    }
}
