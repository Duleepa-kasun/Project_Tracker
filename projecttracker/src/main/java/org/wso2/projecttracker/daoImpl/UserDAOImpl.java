package org.wso2.projecttracker.daoImpl;

import org.wso2.projecttracker.security.Authorization;
import org.wso2.projecttracker.bean.User;
import org.wso2.projecttracker.bean.Users;
import org.wso2.projecttracker.config.DatabaseConf;
import org.wso2.projecttracker.dao.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements UserDAO {

    public List<User> getAll() {
        List<User> users = new ArrayList<User>();
        Connection connection = DatabaseConf.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * from User";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
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

    public Users login(User userLogin) {
        List<User> users = new ArrayList<User>();
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement statement =null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT * from User WHERE username=? AND password=? ";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userLogin.getUsername());
            statement.setString(2, userLogin.getPassword());
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

        Users list = new Users();
        list.setUsers(users);
        return list;

    }

    public String createUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "INSERT INTO User(name,username,password,email,role) VALUES(?,?,?,?,?)";
        Connection con = DatabaseConf.getConnection();
        String status;
        PreparedStatement statement = null;
        
        try {
        	statement = con.prepareStatement(sql);
        	statement.setString(1, user.getName());
        	statement.setString(2, user.getUsername());
        	statement.setString(3, Authorization.encrypt(user.getPassword()));
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());

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
        	}if(con!=null){
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return status;
    }

    public String editUser(User user) {
        String sql = "UPDATE User SET name = ? , username = ? , password = ? , email = ? , role= ? WHERE userId = ?";
        Connection con = DatabaseConf.getConnection();
        String status;
        PreparedStatement statement = null;
        try {
        	statement = con.prepareStatement(sql);
        	statement.setString(1, user.getName());
        	statement.setString(2, user.getUsername());
        	statement.setString(3, user.getPassword());
        	statement.setString(4, user.getEmail());
        	statement.setString(5, user.getRole());

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
        	}if(con!=null){
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }

        return status;
    }

    public User getUserById(Integer userId) {
        String sql = "SELECT * FROM User WHERE userId = ?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
        			preparedStatement.close();
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
        return user;
    }

    public Users searchUser(String match) {
        String sql = "SELECT * FROM User WHERE username LIKE ?";
        Connection connection = DatabaseConf.getConnection();
        List<User> users = new ArrayList<User>();
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + match + "%");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
					preparedStatement.close();
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

        Users userList = new Users();
        userList.setUsers(users);
        return userList;

    }

    public String deleteUser(Integer userId) {
        String status = null;
        String sql = "DELETE FROM User WHERE userId= ?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            //getting user info of the user to be deleted
            String username = this.getUserById(userId).getUsername();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();
            status = "SUCCESS";

        } catch (SQLException e) {
            status = "UNSUCCESS";
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
					preparedStatement.close();
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
    
    
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username=?";
        Connection connection = DatabaseConf.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               
                user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
                user.setPassword(resultSet.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
					preparedStatement.close();
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

        return user;

    }

	public String setToken(String token, Integer Id) {
		String sql = "UPDATE User SET token=? WHERE userId = ?";
		Connection con = DatabaseConf.getConnection();
        PreparedStatement preparedStatement = null;
        String status;
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, token);
            preparedStatement.setInt(2, Id);
            preparedStatement.executeUpdate();
            status = "SUCCESS";
        } catch (SQLException e) {
            status = "UNSUCCESS";
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}if(con!=null){
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }

        return status;
	}


	public User getUserByToken(String userToken) {
		String sql = "SELECT * FROM User WHERE token= ?";
		Connection connection = DatabaseConf.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userToken);
            
            resultSet = preparedStatement.executeQuery();
            	
            while (resultSet.next()) {             
                user.setRole(resultSet.getString("role"));
                user.setToken(resultSet.getString("token"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
					preparedStatement.close();
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

        return user;
	}
    



	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM User WHERE email= ?";
		Connection connection = DatabaseConf.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            
            resultSet = preparedStatement.executeQuery();
            	
            while (resultSet.next()) {             
            	user.setUserId(resultSet.getInt("userId"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	if(preparedStatement!=null){
        		try {
					preparedStatement.close();
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

        return user;
	}
	
    
}
