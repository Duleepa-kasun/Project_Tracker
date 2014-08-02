package org.wso2.projecttracker.dao;

import org.wso2.projecttracker.bean.User;
import org.wso2.projecttracker.bean.Users;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

public interface UserDAO {
    public List<User> getAll();
    public Users login(User user);
    public String createUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException;
    public String editUser(User user);
    public User getUserById(Integer userId);
    public Users searchUser(String match);
    public String deleteUser(Integer userId);
    public User getUserByUsername(String username);
    public String setToken(String token,Integer Id);
	public User getUserByToken(String userToken);
	public User getUserByEmail(String email);
    
}
