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

import org.wso2.projecttracker.bean.Subtask;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.SubTaskDAO;
import org.wso2.projecttracker.dao.TasksDAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SubtaskDAOImpl implements SubTaskDAO {

    public List<Subtask> getAll() {
        List<Subtask> subtasks = new ArrayList<Subtask>();
        String sql = "SELECT * FROM Subtask";
        Connection connection = DatabaseConf.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Subtask subtask = new Subtask();
                subtask.setSubTask(resultSet.getString("subTask"));
                subtask.setDescription(resultSet.getString("description"));
                subtask.setSubtaskId(resultSet.getInt("subtaskId"));
                //get the task
                TasksDAO tasksDAO = new TasksDAOImpl();
                subtask.setTask(tasksDAO.getTaskById(resultSet.getInt("taskId")));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                subtask.setEndDate(smdf.parse(resultSet.getString("endDate")));
                subtask.setStartDate(smdf.parse(resultSet.getString("startDate")));
                subtasks.add(subtask);
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
        return subtasks;
    }

    public List<Subtask> getAllbyTaskId(int taskId) {
        List<Subtask> subtasks = new ArrayList<Subtask>();
        
        String sql = "SELECT * FROM Subtask WHERE taskId=?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet  = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,taskId);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Subtask subtask = new Subtask();
                subtask.setSubTask(resultSet.getString("subTask"));
                subtask.setDescription(resultSet.getString("description"));
                subtask.setSubtaskId(resultSet.getInt("subtaskId"));
                //get the task
                TasksDAO tasksDAO = new TasksDAOImpl();
                subtask.setTask(tasksDAO.getTaskById(resultSet.getInt("taskId")));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                subtask.setEndDate(smdf.parse(resultSet.getString("endDate")));
                subtask.setStartDate(smdf.parse(resultSet.getString("startDate")));
                subtasks.add(subtask);
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
        return subtasks;
    }

    public Subtask getSubtaskById(int subtaskId) {
        String sql = "SELECT * FROM Subtask WHERE subtaskId=?";
        Subtask subtask = new Subtask();
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            
            statement = connection.prepareStatement(sql);
            statement.setInt(1,subtaskId);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                subtask.setSubTask(resultSet.getString("subTask"));
                subtask.setDescription(resultSet.getString("description"));
                subtask.setSubtaskId(resultSet.getInt("subtaskId"));
                //get the task
                TasksDAO tasksDAO = new TasksDAOImpl();
                subtask.setTask(tasksDAO.getTaskById(resultSet.getInt("taskId")));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                subtask.setEndDate(smdf.parse(resultSet.getString("endDate")));
                subtask.setStartDate(smdf.parse(resultSet.getString("startDate")));

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
        return subtask;
    }

    public Subtask getSubtaskByName(String name) {
        String sql = "SELECT * FROM Subtask WHERE subTask=?";
        Subtask subtask = new Subtask();
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            while (resultSet.next()){

                subtask.setSubTask(resultSet.getString("subTask"));
                subtask.setDescription(resultSet.getString("description"));
                subtask.setSubtaskId(resultSet.getInt("subtaskId"));
                //get the task
                TasksDAO tasksDAO = new TasksDAOImpl();
                subtask.setTask(tasksDAO.getTaskById(resultSet.getInt("taskId")));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                subtask.setEndDate(smdf.parse(resultSet.getString("endDate")));
                subtask.setStartDate(smdf.parse(resultSet.getString("startDate")));

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
        return subtask;
    }

    public List<Subtask> searchSubtaskByName(String name) {
        List<Subtask> subtasks = new ArrayList<Subtask>();
        String sql = "SELECT * FROM Subtask WHERE subTask like ?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet =null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Subtask subtask = new Subtask();
                subtask.setSubTask(resultSet.getString("subTask"));
                subtask.setDescription(resultSet.getString("description"));
                subtask.setSubtaskId(resultSet.getInt("subtaskId"));
                //get the task
                TasksDAO tasksDAO = new TasksDAOImpl();
                subtask.setTask(tasksDAO.getTaskById(resultSet.getInt("taskId")));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                subtask.setEndDate(smdf.parse(resultSet.getString("endDate")));
                subtask.setStartDate(smdf.parse(resultSet.getString("startDate")));
                subtasks.add(subtask);
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
        return subtasks;
    }

    public String createSubtask(Subtask subtask) {
        String sql = "INSERT INTO Subtask(subTask,startDate,endDate,description,taskId) " +
                "VALUES (?,?,?,?,?)";
        String createSubtask = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement  = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,subtask.getSubTask());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(2,sdf.format(subtask.getStartDate()));
            statement.setString(3,sdf.format(subtask.getEndDate()));
            statement.setString(4,subtask.getDescription());
            statement.setInt(5,subtask.getTask().getTaskId());

            statement.executeUpdate();
            createSubtask = "SUCCESS";
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
        return createSubtask;
    }

    public String editSubtask(Subtask subtask) {
        String editSubtask = "UNSUCCESS";
        String sql = "UPDATE Subtask SET subTask = ? , startDate = ?, endDate = ?, description = ?" +
                " WHERE subtaskId = ?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement  = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,subtask.getSubTask());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(2,sdf.format(subtask.getStartDate()));
            statement.setString(3,sdf.format(subtask.getEndDate()));
            statement.setString(4,subtask.getDescription());
            statement.setInt(5,subtask.getSubtaskId());

            statement.executeUpdate();
            editSubtask = "SUCCESS";
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
        return editSubtask;
    }

    public String deleteSubtask(Integer subtaskId) {
        String sql = "DELETE FROM Subtask Where subtaskId = ?";
        String deleteSubtask = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,subtaskId);
            statement.executeUpdate();
            deleteSubtask = "SUCCESS";
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
        return  deleteSubtask;
    }
}
