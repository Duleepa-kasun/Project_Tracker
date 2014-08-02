package org.wso2.projecttracker.dao;


import org.wso2.projecttracker.bean.Project;
import org.wso2.projecttracker.bean.User;

import java.util.List;

public interface UserProjectDAO {
    public List<Project> getProjectListByUser(Integer userId);

    public List<User> getUserListByProject(Integer projectId);

    public String assigningProjectToUser(Integer userId, Integer projectId);

    public String deleteUserProjectByUser(Integer userId);

    public String deleteUserProjectByProject(Integer projectId);

    public String removeUserFromProject(Integer projectId,Integer userId);
}