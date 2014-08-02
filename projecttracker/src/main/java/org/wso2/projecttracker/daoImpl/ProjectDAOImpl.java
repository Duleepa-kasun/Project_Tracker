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

import org.wso2.projecttracker.bean.Project;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.ProjectDAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class ProjectDAOImpl implements ProjectDAO {
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<Project>();
        Connection connection = DatabaseConf.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM Project";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

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
        } finally{
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

    public Project getProjectById(Integer projectId) {
        Project project = new Project();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Project WHERE projectId=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                project.setProjectId(resultSet.getInt("projectId"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setShortName(resultSet.getString("shortName"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                project.setStartDate(smdf.parse(resultSet.getString("startDate")));
                project.setEndDate(smdf.parse(resultSet.getString("endDate")));
                project.setStatus(resultSet.getString("status"));
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
        return project;
    }

    public Project getProjectByShortName(String shortName) {
        Project project = new Project();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Project WHERE shortName=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, shortName);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                project.setProjectId(resultSet.getInt("projectId"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setShortName(resultSet.getString("shortName"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                project.setStartDate(smdf.parse(resultSet.getString("startDate")));
                project.setEndDate(smdf.parse(resultSet.getString("endDate")));
                project.setStatus(resultSet.getString("status"));
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
        return project;
    }

    public List<Project> getProjectByStatus(String status) {
        List<Project> projects = new ArrayList<Project>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Project WHERE status=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,status);
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

    public List<Project> searchProjectByShortName(String match) {
        List<Project> projects = new ArrayList<Project>();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Project WHERE shortName like ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+match+"%");
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

    public String createProject(Project project) {
        String sql = "INSERT INTO Project(title,description,shortName,startDate,endDate,status) " +
                "VALUES (?,?,?,?,?,?)";
        String createProject = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,project.getTitle());
            statement.setString(2,project.getDescription());
            statement.setString(3,project.getShortName());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(4,sdf.format(project.getStartDate()));
            statement.setString(5,sdf.format(project.getEndDate()));
            statement.setString(6,project.getStatus());
            statement.executeUpdate();
            createProject = "SUCCESS";
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
        return createProject;
    }

    public String editProject(Project project) {
        String sql = "UPDATE Project SET title = ? , description = ?, shortName = ?, startDate = ?, endDate = ?, status = ?" +
                " WHERE projectId = ?";
        String editProject = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement  = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,project.getTitle());
            statement.setString(2,project.getDescription());
            statement.setString(3,project.getShortName());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(4,sdf.format(project.getStartDate()));
            statement.setString(5,sdf.format(project.getEndDate()));
            statement.setString(6,project.getStatus());
            statement.setInt(7,project.getProjectId());
            statement.executeUpdate();
            editProject = "SUCCESS";
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

        return  editProject;
    }

    public Project getProjectByTitle(String title) {
        Project project = new Project();
        Connection connection = DatabaseConf.getConnection();
        String sql = "SELECT * FROM Project WHERE title=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                project.setProjectId(resultSet.getInt("projectId"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setShortName(resultSet.getString("shortName"));

                //date format
                SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
                project.setStartDate(smdf.parse(resultSet.getString("startDate")));
                project.setEndDate(smdf.parse(resultSet.getString("endDate")));
                project.setStatus(resultSet.getString("status"));
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
        return project;
    }

    public List<Project> searchProjectByTitle(String match) {
        List<Project> projects = new ArrayList<Project>();
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            
            String sql = "SELECT * FROM Project WHERE title like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+match+"%");
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

    public String deleteProject(Integer projectId) {
        String sql = "DELETE FROM Project Where projectId = ?";
        String deleteProject = "UNSUCCESS";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement = null;
        try {
        	statement = connection.prepareStatement(sql);
            statement.setInt(1,projectId);
            statement.executeUpdate();
            deleteProject = "SUCCESS";
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
        return  deleteProject;
    }
}
