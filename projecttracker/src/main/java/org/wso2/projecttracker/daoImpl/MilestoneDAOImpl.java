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

import org.wso2.projecttracker.bean.Milestone;
import org.wso2.projecttracker.bean.Project;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.MilestoneDAO;
import org.wso2.projecttracker.dao.ProjectDAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MilestoneDAOImpl implements MilestoneDAO {

    public List<Milestone> getAllMilestones() {
        List<Milestone> milestones = new ArrayList<Milestone>();
        String sql = "SELECT * FROM Milestone";
        Connection connection = DatabaseConf.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Milestone milestone = new Milestone();
                milestone.setMilestoneId(resultSet.getInt("milestoneId"));
                milestone.setName(resultSet.getString("name"));
                milestone.setDescription(resultSet.getString("description"));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                milestone.setEndDate(smdf.parse(resultSet.getString("endDate")));
                //get the project
                ProjectDAO projectDAO = new ProjectDAOImpl();
                Project project = projectDAO.getProjectById(resultSet.getInt("projectId"));
                milestone.setProject(project);
                milestones.add(milestone);
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
        return milestones;
    }

    public Milestone getMilestoneById(Integer milestoneId) {
        Milestone milestone = new Milestone();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Milestone WHERE milestoneId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,milestoneId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                milestone.setMilestoneId(resultSet.getInt("milestoneId"));
                milestone.setName(resultSet.getString("name"));
                milestone.setDescription(resultSet.getString("description"));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                milestone.setEndDate(smdf.parse(resultSet.getString("endDate")));
                //get the project
                ProjectDAO projectDAO = new ProjectDAOImpl();
                Project project = projectDAO.getProjectById(resultSet.getInt("projectId"));
                milestone.setProject(project);
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
        return milestone;
    }

    public Milestone getMilestoneByName(String name) {
        Milestone milestone = new Milestone();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Milestone WHERE name=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                milestone.setMilestoneId(resultSet.getInt("milestoneId"));
                milestone.setName(resultSet.getString("name"));
                milestone.setDescription(resultSet.getString("description"));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                milestone.setEndDate(smdf.parse(resultSet.getString("endDate")));
                //get the project
                ProjectDAO projectDAO = new ProjectDAOImpl();
                Project project = projectDAO.getProjectById(resultSet.getInt("projectId"));
                milestone.setProject(project);
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
        return milestone;
    }

    public List<Milestone> getMilestonesByProjectId(Integer projectId) {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT m.* FROM Milestone m INNER JOIN Project p ON m.projectId=p.projectId  WHERE m.projectId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Milestone milestone = new Milestone();
                milestone.setMilestoneId(resultSet.getInt("milestoneId"));
                milestone.setName(resultSet.getString("name"));
                milestone.setDescription(resultSet.getString("description"));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                milestone.setEndDate(smdf.parse(resultSet.getString("endDate")));
                //get the project
                ProjectDAO projectDAO = new ProjectDAOImpl();
                Project project = projectDAO.getProjectById(resultSet.getInt("projectId"));
                milestone.setProject(project);
                milestones.add(milestone);
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
        	}
        	if(connection!=null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return milestones;
    }

    public Milestone getProjectMilestone(Integer projectId, String name) {
        Milestone milestone = new Milestone();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Milestone WHERE projectId=? AND name=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.setString(2, name);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                milestone.setMilestoneId(resultSet.getInt("milestoneId"));
                milestone.setName(resultSet.getString("name"));
                milestone.setDescription(resultSet.getString("description"));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                milestone.setEndDate(smdf.parse(resultSet.getString("endDate")));
                //get the project
                ProjectDAO projectDAO = new ProjectDAOImpl();
                Project project = projectDAO.getProjectById(resultSet.getInt("projectId"));
                milestone.setProject(project);
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
        return milestone;
    }

    public List<Milestone> searchMilestoneByName(String name) {
        List<Milestone> milestones = new ArrayList<Milestone>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Milestone WHERE name like ?";
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Milestone milestone = new Milestone();
                milestone.setMilestoneId(resultSet.getInt("milestoneId"));
                milestone.setName(resultSet.getString("name"));
                milestone.setDescription(resultSet.getString("description"));
                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                milestone.setEndDate(smdf.parse(resultSet.getString("endDate")));
                //get the project
                ProjectDAO projectDAO = new ProjectDAOImpl();
                Project project = projectDAO.getProjectById(resultSet.getInt("projectId"));
                milestone.setProject(project);
                milestones.add(milestone);
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
        return milestones;
    }

    public String createMilestone(Milestone milestone) {
        String sql = "INSERT INTO Milestone(name,description,projectId,endDate) " +
                "VALUES (?,?,?,?)";
        String createMilestone = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,milestone.getName());
            statement.setString(2,milestone.getDescription());
            statement.setInt(3, milestone.getProject().getProjectId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(4,sdf.format(milestone.getEndDate()));
            statement.executeUpdate();
            createMilestone = "SUCCESS";
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
        return createMilestone;
    }

    public String editMilestone(Milestone milestone) {
        String editMilestone = "UNSUCCESS";
        String sql = "UPDATE Milestone SET name = ? , description = ?, endDate = ?" +
                " WHERE milestoneId = ?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,milestone.getName());
            statement.setString(2,milestone.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(3,sdf.format(milestone.getEndDate()));
            statement.setInt(4,milestone.getMilestoneId());
            statement.executeUpdate();
            editMilestone = "SUCCESS";
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
        return editMilestone;


    }

    public String deleteMilestone(Integer milestoneId) {
        String sql = "DELETE FROM Milestone Where milestoneId = ?";
        String deleteMilestone = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,milestoneId);
            statement.executeUpdate();
            deleteMilestone = "SUCCESS";
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
        return  deleteMilestone;
    }
}
