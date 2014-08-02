package org.wso2.projecttracker.daoImpl;

import org.wso2.projecttracker.bean.Project;
import org.wso2.projecttracker.bean.User;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.UserProjectDAO;

import java.text.ParseException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class UserProjectDAOImpl implements UserProjectDAO {

    public List<Project> getProjectListByUser(Integer userId) {
        List<Project> projects = new ArrayList<Project>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT p.* FROM Project_User pu JOIN Project p  ON pu.projectId=p.projectId  WHERE userId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Project project = new Project();
                project.setProjectId(resultSet.getInt("projectId"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setShortName(resultSet.getString("shortName"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                project.setStartDate(smdf.parse(resultSet.getString("startDate")));
                project.setEndDate(smdf.parse(resultSet.getString("endDate")));
                project.setStatus(resultSet.getString("status"));
                projects.add(project);
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

        return projects;
    }

    public List<User> getUserListByProject(Integer projectId) {
        List<User> users = new ArrayList<User>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT u.* FROM Project_User pu JOIN User u ON pu.userId=u.userId  WHERE projectId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                user.setUserId(resultSet.getInt("userId"));

                users.add(user);
            }

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
        return users;
    }

    public String assigningProjectToUser(Integer userId, Integer projectId) {

        String sql = "INSERT INTO Project_User(projectId,userId) VALUES(?,?)";
        Connection connection = DatabaseConf.getConnection();
        String status="UNSUCCESS";
        PreparedStatement statement = null;
        try {
        	statement = connection.prepareStatement(sql);
        	statement.setInt(1, projectId);
        	statement.setInt(2, userId);

        	statement.executeUpdate();
            status = "SUCCESS";

        } catch (SQLException e) {
            status = "UNSUCCESS";
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
        return status;
    }

    public String deleteUserProjectByUser(Integer userId) {
        String sql = "DELETE FROM Project_User Where userId = ?";
        String deleteUserProject = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            statement.executeUpdate();
            deleteUserProject = "SUCCESS";
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
        return  deleteUserProject;
    }

    public String deleteUserProjectByProject(Integer projectId) {
        String sql = "DELETE FROM Project_User Where projectId = ?";
        String deleteUserProject = "UNSUCCESS";
        PreparedStatement statement = null;
        Connection connection = DatabaseConf.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            statement.executeUpdate();
            deleteUserProject = "SUCCESS";
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
        return  deleteUserProject;
    }


 public String removeUserFromProject(Integer projectId, Integer userId) {
    String sql = "DELETE FROM Project_User Where projectId = ? AND userId = ?";
        String deleteUserProject = "UNSUCCESS";
        PreparedStatement statement = null;
        Connection connection = DatabaseConf.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            statement.setInt(2, userId);
            statement.executeUpdate();
            deleteUserProject = "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        if(statement!=null){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        if(connection!=null){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        }
        return  deleteUserProject;
    }
}
