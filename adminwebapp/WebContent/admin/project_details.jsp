<%@page import="org.wso2.projecttracker.app.model.UserProjectService"%>
<%@page import="org.wso2.projecttracker.app.model.UserService"%>
<%@page import="com.jayway.jsonpath.ReadContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.wso2.projecttracker.app.model.MilestoneService"%>
<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>


<!-- Style Sheets -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
<!--Dash board css file-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/dashboard.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/datepicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrapValidator.css">
<style type="text/css">
.borderless tbody tr td,.borderless thead tr th {
	border: none;
}

.member_remove {
	float: right;
}

.member_remove:hover {
	cursor: pointer;
}
</style>

<style type="text/css">
.typeahead,.tt-query,.tt-hint {
	width: 396px;
	height: 37px;
	padding: 8px 12px;
	font-size: 16px;
	line-height: 30px;
	border: 2px solid #ccc;
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
	outline: none;
}

.twitter-typeahead {
	width: 260px;
	margin-top: 4px;
}

.typeahead {
	background-color: #fff;
}

.typeahead:focus {
	border: 2px solid #0097cf;
}

.tt-query {
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}

.tt-hint {
	color: #999
}

.tt-dropdown-menu {
	width: 422px;
	margin-top: 12px;
	padding: 8px 0;
	background-color: #fff;
	border: 1px solid #ccc;
	border: 1px solid rgba(0, 0, 0, 0.2);
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
	-webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
	-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
	box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
}

.tt-suggestion {
	padding: 3px 20px;
	font-size: 16px;
	line-height: 22px;
}

.tt-suggestion.tt-cursor {
	color: #fff;
	background-color: #0097cf;
}

.tt-suggestion p {
	margin: 0;
}

.gist {
	font-size: 12px;
}
</style>


<title>Project Details</title>


</head>


<body>
	<%
		if(session.getAttribute("token")==null){
			response.sendRedirect("../index.jsp");
		}else{
	%>

	<%
		String token = (String) request.getSession().getAttribute("token");
	%>>

	<!--Top Navigation Bar-->
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Project Tracker</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">Project</a></li>
				</ul>
			</div>
		</div>
	</div>


	<!--Side Bar Content-->
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
						<h3 class="panel-title">Project Details</h3>

					</div>
					<div class="panel-body">
						<%
							JSONObject project = (JSONObject) request.getSession().getAttribute("project");
																						if(project==null){
																							response.sendRedirect("project.jsp");
																						}else{
						%>
						<script type="text/javascript">
							var project_id =
						"<%=JsonPath.read(project.toString(), "$.project.projectId").toString()%>";
						</script>

						<table class="table borderless">
							<tr>
								<td>Project Title</td>
								<td><%=JsonPath.read(project.toString(), "$.project.title").toString()%></td>
							</tr>
							<tr>
								<td>Short Name</td>
								<td><%=JsonPath.read(project.toString(), "$.project.shortName")
					.toString()%></td>
							</tr>
							<tr>
								<td>Description</td>
								<td><%=JsonPath
					.read(project.toString(), "$.project.description")
					.toString()%></td>
							</tr>
							<tr>
								<td>Start Date</td>
								<td><%=JsonPath.read(project.toString(), "$.project.startDate")
					.toString()%></td>
							</tr>
							<tr>
								<td>End Date</td>
								<td><%=JsonPath.read(project.toString(), "$.project.endDate")
					.toString()%></td>
							</tr>
							<tr>
								<td>Status</td>
								<td><%=JsonPath.read(project.toString(), "$.project.status")
					.toString()%></td>
							</tr>
						</table>

					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Project Team</h3>
					</div>
					<div class="panel-body">
						<%
							if(session.getAttribute("user_assign") != null && ((String)session.getAttribute("user_assign")).equalsIgnoreCase("SUCCESS") ){
																							out.print("<div class='alert alert-success' role='alert'>User has been assigned succesfully ! </div>");
																							session.setAttribute("user_assign", null);
																							
																						}else if(session.getAttribute("user_assign") != null&&((String)session.getAttribute("user_assign")).equalsIgnoreCase("UNSUCCESS")){
																							out.print("<div class='alert alert-danger' role='alert'>A problem has been occurred while you assign user ! </div>");
																							session.setAttribute("user_assign", null);
																							
																						}
						%>
						<form action="../AssignUserProjectController" method="POST">
							<div class="input-group custom-search-form" id="the-basics">
								<input type="hidden" name="user_projectId"
									value="<%=JsonPath.read(project.toString(), "$.project.projectId").toString()%>" />
								<input type="text" class="form-control typeahead "
									placeholder="Email" name="email"> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>

							</div>
							<!-- get project users -->
							<%
								UserProjectService userProjectService = new UserProjectService();
																							JSONObject projectUsers =  userProjectService.getProjectUsers(JsonPath.read(project.toString(), "$.project.projectId").toString(), token);
																							ReadContext context = JsonPath.parse(projectUsers);
																							ArrayList<Object> projectUsersList = null;
																							Object projectUser = null;
																							
																							String s = JsonPath.read(projectUsers.toString(),"$.users").toString();
																							
																							
																							 if(!s.trim().equalsIgnoreCase("")){
																								if(JsonPath.read(projectUsers.toString(),"$.users.user[*]").toString().charAt(0)=='{'){
																									projectUser = JsonPath.read(projectUsers.toString(),"$.users.user[*]").toString();
																								}else if(JsonPath.read(projectUsers.toString(),"$.users.user[*]").toString().charAt(0)=='['){
																									projectUsersList = JsonPath.read(projectUsers.toString(),"$.users.user[*]");
																								}
																							}
							%>

							<br />
							<button type="submit" class="btn btn-info">Assign</button>
							<br /> <br />
						</form>
						
						<%
							if(session.getAttribute("project_user_delete") != null && ((String)session.getAttribute("project_user_delete")).equalsIgnoreCase("SUCCESS") ){
							out.print("<div class='alert alert-success' role='alert'>User has been removed from project succesfully ! </div>");
							session.setAttribute("project_user_delete", null);
						}
						%>
						
						<ul class="list-group" id="contact-list">
							<%
								if(projectUsersList!=null){
																												for(Object u : projectUsersList){
							%>
							<li class="list-group-item">
								<div class="col-xs-12 col-sm-3">
									<img
										src="${pageContext.request.contextPath}/resources/user.png"
										alt="Scott Stevens" class="img-responsive img-circle" />
								</div>
								<div class="col-xs-12 col-sm-9">
									<span class="name"><%=JsonPath.read(u.toString(),"$.email").toString()%></span>

									<a href="#"
										id="#delete_user_project_link<%=JsonPath.read(u.toString(),"$.userId").toString()%>"
										data-toggle="modal"
										data-target="#delete_user_project<%=JsonPath.read(u.toString(),"$.userId").toString()%>"
										title="Remove" class="tooltip_element deleteProjectUserModel"><span
										class="glyphicon glyphicon-remove member_remove"></span></a> <br />
								</div>
								<div class="clearfix"></div>
							</li>

							<!--Delete Project User Modal-->
							<div class="modal fade"
								id="delete_user_project<%=JsonPath.read(u.toString(),"$.userId").toString()%>"
								tabindex="-1" role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content"
										id="delete_user_project_content<%=JsonPath.read(u.toString(),"$.userId").toString()%>"></div>
								</div>
							</div>
							<%
								}
																											}else if(projectUser!=null){
							%>
							<li class="list-group-item">
								<div class="col-xs-12 col-sm-3">
									<img
										src="${pageContext.request.contextPath}/resources/user.png"
										alt="Scott Stevens" class="img-responsive img-circle" />
								</div>
								<div class="col-xs-12 col-sm-9">
									<span class="name"><a href="#"><%=JsonPath.read(projectUser.toString(),"$.email").toString()%></a></span>
									<span class="glyphicon glyphicon-remove member_remove"></span>
									<br />
								</div>
								<div class="clearfix"></div>
							</li>

							<!--Delete Project User Modal-->
							<div class="modal fade"
								id="delete_user_project<%=JsonPath.read(projectUser.toString(),"$.userId").toString()%>"
								tabindex="-1" role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content"
										id="delete_user_project_content<%=JsonPath.read(projectUser.toString(),"$.userId").toString()%>">cf</div>
								</div>
							</div>
							<%
								}
							%>

						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-8">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3
							class="panel-tinet.minidev.json.JSONObject cannot be cast to java.util.ArrayListtle">Milestones</h3>
					</div>
					<div class="panel-body">
						<fieldset>
							<legend>Add New Milestone</legend>

							<form class="form-horizontal" id="add_milestone_form"
								action="CreateMilestoneController" method="post">
								<!-- Text input-->
								<div class="form-group">
									<label class="col-lg-4 control-label" for="milestone_name">Milestone
										Name</label>
									<div class="col-lg-7">
										<input id="milestone_name" name="milestone_name"
											placeholder="Name of the milestone"
											class="input form-control" required="" type="text">
									</div>
								</div>

								<!-- Text input-->
								<div class="form-group">
									<label class="col-lg-4 control-label" for="milestone_date">Date</label>
									<div class="col-lg-7">
										<input id="milestone_date" name="milestone_date"
											class="input form-control" type="text">
									</div>
								</div>

								<!-- Text input-->
								<div class="form-group">
									<label class="col-lg-4 control-label"
										for="milestone_description">Description</label>
									<div class="col-lg-7">
										<textarea id="milestone_description"
											name="milestone_description"></textarea>
									</div>
								</div>

								<input type="hidden" name="projectId"
									value="<%=JsonPath.read(project.toString(), "$.project.projectId")
					.toString()%>">


								<div class="form-group">
									<label class="col-lg-4 control-label"></label>
									<div class="col-lg-7">
										<button id="add_milestone" type="submit" name="add_milestone"
											class="btn btn-primary">Add</button>
									</div>
								</div>
							</form>
						</fieldset>

						<table class="table table-bordered">
							<%
								MilestoneService milestoneService = new MilestoneService();
																												String projectId = JsonPath.read(project.toString(),"$.project.projectId").toString();
																																
																												JSONObject milestones = milestoneService.getAllMilestones(token,projectId);
																																																									
																																																			
																												if(!milestones.toString().equalsIgnoreCase("{"+'"'+"milestones"+'"'+":"+'"'+'"'+"}")){
																												ReadContext con = JsonPath.parse(milestones.toString());
																																
																												ArrayList<Object> milestoneList = null;
																												Object singleResult= null;
																																
																												if(con.read("$.milestones.milestone[*]").toString().charAt(0)=='{'){
																													singleResult = con.read("$.milestones.milestone[*]");
																												}
																												else if(con.read("$.milestones.milestone[*]").toString().charAt(0)=='['){
																													milestoneList = con.read("$.milestones.milestone[*]");
																												}
							%>
							<tr>
								<th style="text-align: center">Name</th>
								<th style="text-align: center">Date</th>
								<th style="text-align: center">Description</th>
							</tr>
							<%
								if(milestoneList!=null){
																																												for(Object milestone :milestoneList ) {
																																													String mileId = JsonPath.read(milestone.toString(),"$.milestoneId").toString();
							%>
							<tr>

								<td><form name="<%="form"+mileId%>" method="post"
										action="TaskDetailsController">
										<a href="<%="form"+mileId%>'"
											onclick="document.forms['<%="form"+mileId%>'].submit(); return false;"><%=JsonPath.read(milestone.toString(),"$.name").toString()%></a>
										<input type="hidden" name="milestoneId"
											value="<%=JsonPath.read(milestone.toString(),"$.milestoneId").toString()%>" />
										<input type="hidden" name="projectId" value="<%=projectId%>" />
									</form></td>
								<td><%=JsonPath.read(milestone.toString(),"$.endDate").toString()%></td>
								<td><%=JsonPath.read(milestone.toString(),"$.description").toString()%></td>
							</tr>
							<%
								}
																																											}if(singleResult!=null){
																																												String mileId1 = JsonPath.read(singleResult.toString(),"$.milestoneId").toString();
							%>
							<tr>

								<td><form name="<%="form"+mileId1%>" method="post"
										action="TaskDetailsController">
										<a href="#"
											onclick="document.forms['<%="form"+mileId1%>'].submit(); return false;"><%=JsonPath.read(singleResult.toString(),"$.name").toString()%></a>
										<input type="hidden" name="milestoneId"
											value="<%=JsonPath.read(singleResult.toString(),"$.milestoneId").toString()%>" />
										<input type="hidden" name="projectId" value="<%=projectId%>" />
									</form></td>
								<td><%=JsonPath.read(singleResult.toString(),"$.endDate").toString()%></td>
								<td><%=JsonPath.read(singleResult.toString(),"$.description").toString()%></td>
							</tr>



							<%
								}
																																									        }
							%>
						</table>
					</div>

					<!-- <div id="the-basics">
						<input class="typeahead" type="text" placeholder="States of USA"
							style="height: 40px">
					</div> -->

				</div>
			</div>
		</div>
	</div>

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script
		src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		$('#milestone_date').datepicker({
			'format' : 'yyyy-mm-dd'
		});
		$('.tooltip_element').tooltip();

		$('#milestone_date').on(
				'change',
				function(e) {
					// Validate the date when user change it
					$('#add_milestone_form').bootstrapValidator(
							'revalidateField', 'milestone_date');
				});

		$('.deleteProjectUserModel')
				.on(
						"click",
						function() {

							var projectUserId = parseInt(this.id.replace(
									"#delete_user_project_link", ""));

							var txt = '<form class="form-horizontal" id="delete_project_user_form'+ projectUserId +'" action="../DeleteProjectUserController" method="POST">'
									+ '<div class="modal-header">'
									+ '<button type="button" class="close" data-dismiss="modal">'
									+ '<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>'
									+ '</button>'
									+ '<h4 class="modal-title">Delete Project User</h4>'
									+ '</div>'
									+ '<div class="modal-body">'
									+ 'Are you sure you want to delete ?'
									+ '<input type="hidden" value="'+projectUserId+'" id="delete_project_user_id'+projectUserId+'" name="delete_project_user_id" >'
									+'<input type="hidden" value="'+project_id+'" id="delete_project_id'+project_id+'" name="delete_project_id" >'
									+ '</div>'
									+ '<div class="modal-footer">'
									+ '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'
									+ '<button type="submit" class="btn btn-danger" >Delete</button>'
									+ '</div>' +

									'</div>' +

									'</form>';

							$('#delete_user_project_content' + projectUserId)
									.html(txt);
						});
	</script>

	<script
		src="${pageContext.request.contextPath}/js/bootstrapValidator.js"></script>

	<script>
		$(document)
				.ready(
						function() {
							$('#add_milestone_form')
									.bootstrapValidator(
											{
												// To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													milestone_name : {
														message : 'The milestone name is not valid',
														validators : {
															notEmpty : {
																message : 'The Milestone name is required and cannot be empty'
															}
														}
													},
													milestone_date : {
														validators : {
															notEmpty : {
																message : 'The Milestone date is required'
															},
															date : {
																format : 'YYYY-MM-DD',
																message : 'The Milestone date is not valid'
															}
														}
													}
												}
											});
						});
	</script>
	<script src="${pageContext.request.contextPath}/js/typeahead.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {

			var substringMatcher = function() {
				strs = null;

				$.get('../UserSearchController', function(responseText) {
					strs = responseText;
				});

				return function findMatches(q, cb) {
					var matches, substrRegex;

					// an array that will be populated with substring matches
					matches = [];

					// regex used to determine if a string contains the substring `q`
					substrRegex = new RegExp(q, 'i');

					// iterate through the pool of strings and for any string that
					// contains the substring `q`, add it to the `matches` array
					$.each(strs, function(i, str) {
						if (substrRegex.test(str)) {
							// the typeahead jQuery plugin expects suggestions to a
							// JavaScript object, refer to typeahead docs for more info
							matches.push({
								value : str
							});
						}
					});

					cb(matches);
				};
			};

			$('#the-basics .typeahead').typeahead({
				hint : true,
				highlight : true,
				minLength : 1
			}, {
				name : 'strs',
				displayKey : 'value',
				source : substringMatcher()
			});
		});
	</script>

	<%
		}
		}
	%>
</body>
</html>
