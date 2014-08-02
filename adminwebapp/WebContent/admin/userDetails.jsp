<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>

<html>
    <head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
        <!--Dash board css file-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.css">

        <title>User</title>
    </head>
    <body>
    
    <%
    	JSONObject user = (JSONObject)request.getAttribute("user");
   
    %>
    <%
	if(session.getAttribute("token")==null){
		response.sendRedirect("../index.jsp");
	}else{

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

                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                    <div class="row">
                        <div class="col-md-8">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">User Details</h3>
									
                                </div>
                                <div class="panel-body">
                                    <table class="table borderless">
                                        
                                        <tr>
                                            <td>Id</td>
                                            <td><%=request.getParameter("userId")%></td>
                                        </tr>
                                        <tr>
                                            <td>Name</td>
                                            <td><%=JsonPath.read(user.toString(),"$.user.name")%></td>
                                        </tr>
                                        <tr>
                                            <td>Email</td>
                                            <td><%=JsonPath.read(user.toString(),"$.user.email")%></td>
                                        </tr>
                                        <tr>
                                            <td>Role</td>
                                            <td><%=JsonPath.read(user.toString(),"$.user.role")%></td>
                                        </tr>
                                        
                                    </table>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
		<%} %>
    </body>
</html>

