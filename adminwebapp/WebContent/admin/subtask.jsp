<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="com.jayway.jsonpath.ReadContext"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.wso2.projecttracker.app.model.SubTaskService"%>
<html>
    <head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
        <!--Dash board css file-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.css">
<%
	if(session.getAttribute("token")==null){
		response.sendRedirect("../index.jsp");
	}else{

%>


<%
	String taskId = request.getParameter("taskId");
	String token = (String)request.getSession().getAttribute("token");
	
	

%>
        <style type="text/css">
            .borderless tbody tr td, .borderless thead tr th {
                border: none;
            }
        </style>
        <title>Sub Tasks</title>
    </head>
    <body>
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
                            <h3 class="panel-title">Sub Tasks</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" action="../CreateSubtaskController" id="add_sub_task_form" method="POST">
                            <input type="hidden" value="<%=taskId%>" name="taskId"/> 
                                <fieldset>

                                    <!-- Form Name -->
                                    <legend>Add New Sub Task</legend>

                                    <!-- Text input-->
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="sub_task">Sub Task</label>
                                        <div class="col-lg-7">
                                            <input id="sub_task" name="sub_task" placeholder="Name of the sub task" class="input form-control" required="" type="text">
                                        </div>
                                    </div>

                                    <!-- Textarea -->
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="sub_task_description">Description</label>
                                        <div class="col-lg-6">                   
                                            <textarea id="sub_task_description" name="sub_task_description" ></textarea>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="sub_task_start_date">Start Date</label>
                                        <div class="col-lg-6">
                                            <input id="sub_task_start_date" name="sub_task_start_date"  class="input-xlarge date_fields form-control" required="" type="text">                     
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="sub_task_end_date">End Date</label>
                                        <div class="col-lg-6">
                                            <input id="sub_task_end_date" name="sub_task_end_date"  class="input-xlarge date_fields form-control" required="" type="text">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label"></label>
                                        <div class="col-lg-6">
                                            <button id="add_sub_task" name="add_sub_task" type="submit" class="btn btn-primary">Add</button>
                                        </div>
                                    </div>

                                </fieldset>
                            </form>

                            <fieldset>
                            	<%
                                	SubTaskService subTaskService = new SubTaskService();
                                    JSONObject subTasks = subTaskService.getAllSubtasksByTaskId(taskId, token);
                                    System.out.print(subTasks.toString());
                                    ReadContext readContext = JsonPath.parse(subTasks.toString());
                                    if(!subTasks.toString().equalsIgnoreCase("{"+'"'+"subtasks"+'"'+":"+'"'+'"'+"}")){
                                		ArrayList<Object> subtaskList = null;
                                		Object singleObject = null;
                                		
                                %>
                                <legend>Sub Tasks</legend>
                                <table class="table table-bordered"> 
                               
                                   <tr>
                                        <th>Sub Task</th>
                                        <th>Description</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                    </tr>
                                    
                               <% 
                                if(readContext.read("$.subtasks.subtask[*]").toString().charAt(0)=='{'){
                                	singleObject= readContext.read("$.subtasks.subtask[*]");
                                	String sbtId = JsonPath.read(singleObject.toString(), "$.subtaskId").toString();
                                %>
                                    <tr>
                                        <td><form method="get" action="comment.jsp" name="<%="form"+sbtId%>">
                                        <a href="#" onclick="document.forms['<%="form"+sbtId%>'].submit(); return false;">
                                        <input type="hidden" value="<%=sbtId%>" name="subtaskId"/>
                                        <%=JsonPath.read(singleObject,"$.subTask") %>
                                        </a>
                                        </form></td>
                                        <td><%=JsonPath.read(singleObject.toString(),"$.description") %></td>
                                        <td><%=JsonPath.read(singleObject.toString(),"$.startDate") %></td>
                                        <td><%=JsonPath.read(singleObject.toString(),"$.endDate") %></td>
                                    </tr>
                                    <%
                                    }else if(readContext.read("$.subtasks.subtask[*]").toString().charAt(0)=='['){
                                    	subtaskList = readContext.read("$.subtasks.subtask[*]");
                                       	for (int j=0;j<subtaskList.size();j++){
                                       		String sbtId = JsonPath.read(subtaskList.get(j).toString(), "$.subtaskId").toString(); 
                                    %>
                                    
                                    <tr>
                                        <td><form method="get" action="comment.jsp" name="<%="form"+sbtId%>">
                                        <a href="#" onclick="document.forms['<%="form"+sbtId%>'].submit(); return false;">
                                        <input type="hidden" value="<%=sbtId%>" name="subtaskId"/>
                                        <%=JsonPath.read(subtaskList.get(j).toString(),"$.subTask") %>
                                        </a>
                                        </form></td>
                                        <td><%=JsonPath.read(subtaskList.get(j).toString(),"$.description") %></td>
                                        <td><%=JsonPath.read(subtaskList.get(j).toString(),"$.startDate") %></td>
                                        <td><%=JsonPath.read(subtaskList.get(j).toString(),"$.endDate") %></td>
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
            $('.date_fields').datepicker({
                'format': 'yyyy-mm-dd'
            });

            $('.date_fields').on('change', function(e) {
                // Validate the date when user change it
                $('#add_sub_task_form').bootstrapValidator('revalidateField', 'sub_task_start_date');
                $('#add_sub_task_form').bootstrapValidator('revalidateField', 'sub_task_end_date');
            });
        </script>

        <script src="${pageContext.request.contextPath}/js/bootstrapValidator.js"></script>

        <script>
            $(document).ready(function() {
                $('#add_sub_task_form').bootstrapValidator({
                    // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        sub_task: {
                            message: 'The sub task is not valid',
                            validators: {
                                notEmpty: {
                                    message: 'The Sub Task name is required and cannot be empty'
                                }
                            }
                        },
                        sub_task_start_date: {
                            validators: {
                                notEmpty: {
                                    message: 'The Sub Task start date is required'
                                },
                                date: {
                                    format: 'YYYY-MM-DD',
                                    message: 'The Sub Task start date is not valid'
                                }
                            }
                        },
                        sub_task_end_date: {
                            validators: {
                                notEmpty: {
                                    message: 'The Sub Task end date is required'
                                },
                                date: {
                                    format: 'YYYY-MM-DD',
                                    message: 'The Sub Task end date is not valid'
                                }
                            }
                        }
                    }
                });
            });
        </script>

    </body>
    <%} %>
</html>
