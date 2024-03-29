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


package org.wso2.projecttracker.dao;

import org.wso2.projecttracker.bean.Subtask;

import java.util.List;



public interface SubTaskDAO {

    public List<Subtask> getAll();
    public List<Subtask> getAllbyTaskId(int taskId);
    public Subtask getSubtaskById(int subtaskId);
    public Subtask getSubtaskByName(String name);
    public List<Subtask> searchSubtaskByName(String name);

    public String createSubtask(Subtask subtask);
    public String editSubtask(Subtask subtask);
    public String deleteSubtask(Integer subtaskId);

}
