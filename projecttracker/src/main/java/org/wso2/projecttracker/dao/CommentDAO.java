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

import org.wso2.projecttracker.bean.Comment;

import java.util.List;

public interface CommentDAO {
    public List<Comment> getAllCommentsBySubtaskId(Integer subtaskId);
    public List<Comment> getCommentsByUsername(String username);
    public Comment getComment(Integer commentId);
    public String createComment(Comment comment);
    public String editComment(Comment comment);
    public String removeComment(Integer integer);
}
