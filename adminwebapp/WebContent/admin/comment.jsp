<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="com.jayway.jsonpath.ReadContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.wso2.projecttracker.app.model.CommentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
      String token =(String)session.getAttribute("token");
	  if(token==null){
		  response.sendRedirect("../index.jsp");
	  }else{

%>
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
                            <h3 class="panel-title">Comments</h3>
                        </div>
                        <div class="panel-body">
                        
                        <%
                        	String subtaskId = request.getParameter("subtaskId");
                        	if(subtaskId==null){
                        		response.sendRedirect("project.jsp");
                        	}else{
                        		
                        %>
                        
                            <form class="form-horizontal" id="add_comment_form" method="post" action="../CreateCommentController">
								<input type="hidden" value="<%=subtaskId%>" name="subtaskId"/>
                                <!-- Textarea -->
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="comment">Comment</label>
                                    <div class="col-lg-6">                   
                                        <textarea id="comment" name="comment" ></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-2 control-label"></label>
                                    <div class="col-lg-6">
                                        <button id="add_comment" name="add_comment" type="submit" class="btn btn-primary">Add</button>
                                    </div>
                                </div>

                            </form>

                            <fieldset>
                            <legend>Recent Comments</legend>
                            	<%
                            	CommentService commentService = new CommentService();
                                JSONObject comments = commentService.getCommentsBySubtaskId(subtaskId, token);
                                ReadContext readContext=JsonPath.parse(comments.toString());
                                if(!comments.toString().equalsIgnoreCase("{"+'"'+"comments"+'"'+":"+'"'+'"'+"}")){
                         		ArrayList<Object> commentList = null;
                         		Object singleObject = null;
                            	%>
                            	<% 
                                if(readContext.read("$.comments.comment[*]").toString().charAt(0)=='{'){
                                	singleObject = readContext.read("$.comments.comment[*]");
                                	String cId  = JsonPath.read(singleObject.toString(),"$.commentId").toString();
                                %>
                               
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <div>
                                            <div class="mic-info">
                                                <span class="glyphicon glyphicon-user"></span> <a href="#"><%=JsonPath.read(singleObject.toString(),"$.username").toString() %></a> on <span class="glyphicon glyphicon-calendar"></span>
                                                 <%=JsonPath.read(singleObject.toString(),"$.dateTime").toString() %>
                                            </div>
                                        </div>
                                        <div class="comment-text">
                                            <%=JsonPath.read(singleObject.toString(),"$.comment").toString() %>
                                        </div>
                                    </li>
                                </ul>
                                <%}else if(readContext.read("$.comments.comment[*]").toString().charAt(0)=='['){ 
                                	commentList = readContext.read("$.comments.comment[*]");
                                   	for (int j=0;j<commentList.size();j++){
                                   		String cId = JsonPath.read(commentList.get(j).toString(), "$.commentId").toString();                                		
                                %>
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <div>
                                            <div class="mic-info">
                                                <span class="glyphicon glyphicon-user"></span> <a href="#"><%=JsonPath.read(commentList.get(j).toString(),"$.username").toString() %></a> on <span class="glyphicon glyphicon-calendar"></span>
                                                 <%=JsonPath.read(commentList.get(j).toString(),"$.dateTime").toString() %>
                                            </div>
                                        </div>
                                        <div class="comment-text">
                                            <%=JsonPath.read(commentList.get(j).toString(),"$.comment").toString() %>
                                        </div>
                                    </li>
                                </ul>
                                
                                <%}}} %>
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
        </script>

        <script src="${pageContext.request.contextPath}/js/bootstrapValidator.js"></script>

        <script>
            $(document).ready(function() {
                $('#add_comment_form').bootstrapValidator({
                    // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        comment: {
                            message: 'The comment is not valid',
                            validators: {
                                notEmpty: {
                                    message: 'The comment is required and cannot be empty'
                                }
                            }
                        }
                    }
                });
            });
        </script>
		<%
                        	}
	  		}
		%>
    </body>
</html>
