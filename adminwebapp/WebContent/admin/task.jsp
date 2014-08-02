<%@page import="java.util.ArrayList"%>
<%@page import="com.jayway.jsonpath.ReadContext"%>
<%@page import="net.minidev.json.JSONArray"%>
<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.wso2.projecttracker.app.model.TaskService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
        <!--Dash board css file-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.css">

        <style type="text/css">
            .borderless tbody tr td, .borderless thead tr th {
                border: none;
            }
        </style>
        <title>Task Details</title>
    </head>
    <body>
    <%
	if(session.getAttribute("token")==null){
		response.sendRedirect("../index.jsp");
	}else{
		String milestoneId = request.getParameter("milestoneId");
		String projectId = request.getParameter("projectId");
		String token =(String)session.getAttribute("token");
	%>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Project Tracker</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Project</a></li>
                    </ul>
                    <!--                    <form class="navbar-form navbar-right">
                                            <input type="text" class="form-control" placeholder="Search...">
                                        </form>-->
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li class="active"><a href="#">Overview</a></li>
                        <li><a href="#">Reports</a></li>
                        <li><a href="#">Analytics</a></li>
                        <li><a href="#">Export</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Tasks</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="add_task_form" method="post" action="../CreateTaskController">
                               	<input type="hidden" name="projectId" value="<%=projectId %>"/>
                               	<input type="hidden" name="milestoneId" value="<%=milestoneId %>" />
                                <fieldset>

                                    <!-- Form Name -->
                                    <legend>Add New Task</legend>

                                    <!-- Text input-->
                                    <div class="form-group">
                                        <label class="col-lg-4 control-label" for="task">Task</label>
                                        <div class="col-lg-7">
                                            <input id="task" name="task" placeholder="Name of the task" class="input form-control" required="" type="text">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-4 control-label"></label>
                                        <div class="col-lg-7">
                                            <button id="add_task" type="submit" name="add_task" class="btn btn-primary">Add</button>
                                        </div>
                                    </div>

                                </fieldset>
                            </form>
							<%
							TaskService taskService = new TaskService();
							JSONObject taskList = taskService.getAllTasksByMilestone(milestoneId, token);
                            ReadContext g=JsonPath.parse(taskList.toString());
                            if(!taskList.toString().equalsIgnoreCase("{"+'"'+"tasks"+'"'+":"+'"'+'"'+"}")){
                            ArrayList<Object> tasks = null;
							Object singleResult= null;
							%>
                            <fieldset>
                                <legend>Tasks</legend>
                                <table class="table table-bordered">
                                <% 
                                if(g.read("$.tasks.task[*]").toString().charAt(0)=='{'){
                                	singleResult= g.read("$.tasks.task[*]");
                                	String tId = JsonPath.read(singleResult.toString(),"$.taskId").toString();
                                %>
                                    <tr>
                                        <td><form method="get" action="subtask.jsp" name="<%="form"+tId%>">
                                        <a href="#" onclick="document.forms['<%="form"+tId%>'].submit(); return false;">
                                        <%=JsonPath.read(singleResult.toString(), "$.task")%>
                                        <input type="hidden" value="<%=tId%>" name="taskId"/>
                                        </a>
                                        </form></td>
                                    </tr>
                                <%
                                }else if(g.read("$.tasks.task[*]").toString().charAt(0)=='['){
                                   	tasks = g.read("$.tasks.task[*]");
                                   	for (int j=0;j<tasks.size();j++){
                                   		String tId = JsonPath.read(tasks.get(j).toString(), "$.taskId").toString();  		
                                 
                                %>
                                	<tr>
                                        <td><form method="get" action="subtask.jsp" name="<%="form"+tId%>">
                                        <a href="#" onclick="document.forms['<%="form"+tId%>'].submit(); return false;">
                                        <%=JsonPath.read(tasks.get(j).toString(), "$.task")%>
                                        <input type="hidden" value="<%=tId%>" name="taskId"/>
                                        </a>
                                        </form></td>
                                    </tr>
                                <% 
                                    }
                    	            
                                	}
                                }
                                %>
                                </table>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript">
//            $('#milestone_date').datepicker();
        </script>

        <script src="${pageContext.request.contextPath}/js/bootstrapValidator.js"></script>

        <script>
            $(document).ready(function() {
                $('#add_task_form').bootstrapValidator({
                    // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        task: {
                            message: 'The task is not valid',
                            validators: {
                                notEmpty: {
                                    message: 'The Task name is required and cannot be empty'
                                }
                            }
                        }
                    }
                });
            });
        </script>
	<%} %>
    </body>
</html>
