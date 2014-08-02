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

import org.wso2.projecttracker.bean.Task;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.TasksDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TasksDAOImpl implements TasksDAO {

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<Task>();
        Connection connection = DatabaseConf.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM Task";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Task task = new Task();
                MilestoneDAOImpl milestoneDAO =new MilestoneDAOImpl();
                ProjectDAOImpl projectDAO =new ProjectDAOImpl();
                task.setMilestone(milestoneDAO.getMilestoneById(resultSet.getInt("milestoneId")));
                task.setProject(projectDAO.getProjectById(resultSet.getInt("projectId")));
                task.setTask(resultSet.getString("task"));
                task.setTaskId(resultSet.getInt("taskId"));
                tasks.add(task);
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
        return tasks;
    }

    public List<Task> getAllbyProjectId(int projectId) {
        List<Task> tasks = new ArrayList<Task>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Task WHERE projectId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Task task = new Task();
                MilestoneDAOImpl milestoneDAO =new MilestoneDAOImpl();
                ProjectDAOImpl projectDAO =new ProjectDAOImpl();
                task.setMilestone(milestoneDAO.getMilestoneById(resultSet.getInt("milestoneId")));
                task.setProject(projectDAO.getProjectById(resultSet.getInt("projectId")));
                task.setTask(resultSet.getString("task"));
                task.setTaskId(resultSet.getInt("taskId"));
                tasks.add(task);
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
        return tasks;
    }

    public List<Task> getAllbyMilestoneId(int milestoneId) {
        List<Task> tasks = new ArrayList<Task>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Task WHERE milestoneId=?";
        PreparedStatement statement = null;
        ResultSet resultSet =null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,milestoneId);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Task task = new Task();
                MilestoneDAOImpl milestoneDAO =new MilestoneDAOImpl();
                ProjectDAOImpl projectDAO =new ProjectDAOImpl();
                task.setMilestone(milestoneDAO.getMilestoneById(resultSet.getInt("milestoneId")));
                task.setProject(projectDAO.getProjectById(resultSet.getInt("projectId")));
                task.setTask(resultSet.getString("task"));
                task.setTaskId(resultSet.getInt("taskId"));
                tasks.add(task);
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
        return tasks;

    }

    public Task getTaskById(int taskId) {
        Task task =new Task();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Task WHERE taskId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,taskId);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                MilestoneDAOImpl milestoneDAO =new MilestoneDAOImpl();
                ProjectDAOImpl projectDAO =new ProjectDAOImpl();
                task.setMilestone(milestoneDAO.getMilestoneById(resultSet.getInt("milestoneId")));
                task.setProject(projectDAO.getProjectById(resultSet.getInt("projectId")));
                task.setTask(resultSet.getString("task"));
                task.setTaskId(resultSet.getInt("taskId"));
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
        return task;
    }

    public Task getTaskByName(String name) {
        Task task =new Task();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Task WHERE task=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                MilestoneDAOImpl milestoneDAO =new MilestoneDAOImpl();
                ProjectDAOImpl projectDAO =new ProjectDAOImpl();
                task.setMilestone(milestoneDAO.getMilestoneById(resultSet.getInt("milestoneId")));
                task.setProject(projectDAO.getProjectById(resultSet.getInt("projectId")));
                task.setTask(resultSet.getString("task"));
                task.setTaskId(resultSet.getInt("taskId"));
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
        return task;
    }

    public List<Task> searchTaskByName(String name) {
        List<Task> tasks = new ArrayList<Task>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Task WHERE task like ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Task task = new Task();
                MilestoneDAOImpl milestoneDAO =new MilestoneDAOImpl();
                ProjectDAOImpl projectDAO =new ProjectDAOImpl();
                task.setMilestone(milestoneDAO.getMilestoneById(resultSet.getInt("milestoneId")));
                task.setProject(projectDAO.getProjectById(resultSet.getInt("projectId")));
                task.setTask(resultSet.getString("task"));
                task.setTaskId(resultSet.getInt("taskId"));
                tasks.add(task);
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
        return tasks;
    }

    public String createTask(Task task) {
        String sql = "INSERT INTO Task(task,projectId,milestoneId) " +
                "VALUES (?,?,?)";
        String createTask = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,task.getTask());
            statement.setInt(2, task.getProject().getProjectId());
            statement.setInt(3, task.getMilestone().getMilestoneId());
            statement.executeUpdate();
            createTask = "SUCCESS";

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
        return createTask;
    }

    public String editTask(Task task) {
        String sql = "UPDATE Task SET task = ?" +
                " WHERE taskId = ?";
        String editTask = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,task.getTask());
            statement.setInt(2, task.getTaskId());
            statement.executeUpdate();
            editTask = "SUCCESS";

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
        return editTask;
    }

    public String deleteTask(Integer taskId) {
        String sql = "DELETE FROM Task Where taskId = ?";
        String deleteTask = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,taskId);
            statement.executeUpdate();
            deleteTask = "SUCCESS";
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
        return  deleteTask;
    }
}
