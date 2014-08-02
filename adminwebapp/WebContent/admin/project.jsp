<%@page import="java.util.ArrayList"%>
<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="com.jayway.jsonpath.ReadContext"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.wso2.projecttracker.app.model.ProjectService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
<!--Dash board css file-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/dashboard.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/datepicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrapValidator.css">

<title>Dashboard</title>
</head>
<body>
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

				<form class="form-horizontal" id="add_project_form"
					action="../CreateProjectController" method="post">
					<fieldset>

						<!-- Form Name -->
						<legend>Add New Project</legend>
						<%
							if(session.getAttribute("project_create") != null && ((String)session.getAttribute("project_create")).equalsIgnoreCase("SUCCESS") ){
							out.print("<div class='alert alert-success' role='alert'>Project has been created succesfully ! </div>");
							session.setAttribute("project_create", null);
						}
						%>
						<!-- Text input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="project_title">Project
								Title</label>
							<div class="col-lg-6">
								<input id="project_title" name="project_title"
									placeholder="Title of the project"
									class="input-xlarge form-control" required="" type="text">

							</div>
						</div>

						<!-- Text input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="short_name">Short
								Name</label>
							<div class="col-lg-6">
								<input id="short_name" name="short_name"
									placeholder="A short searchable name for the project"
									class="input-xlarge form-control" required="" type="text">

							</div>
						</div>

						<!-- Textarea -->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="project_description">Description</label>
							<div class="col-lg-6">
								<textarea id="project_description" name="project_description"></textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label" for="start_date">Start
								Date</label>
							<div class="col-lg-6">
								<input id="project_start_date" name="project_start_date"
									class="input-xlarge date_fields form-control" required=""
									type="text">
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label" for="end_date">End
								Date</label>
							<div class="col-lg-6">
								<input id="project_end_date" name="project_end_date"
									class="input-xlarge date_fields form-control" required=""
									type="text">
							</div>
						</div>

						<!-- Button -->
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<div class="col-lg-6">
								<button id="add" name="add" class="btn btn-lg btn-primary">Add</button>
							</div>
						</div>

					</fieldset>
				</form>

				<h2 class="sub-header">Projects</h2>
				<div class="table-responsive">

					<%
						ProjectService projectService = new ProjectService();
														String token = (String)request.getSession().getAttribute("token");
														JSONObject projects = projectService.getAllProjects(token);
														ReadContext con = JsonPath.parse(projects.toString());
														ArrayList<Object> projectList = con.read("$.projects.project[*]");
														
														if(session.getAttribute("project_delete") != null && ((String)session.getAttribute("project_delete")).equalsIgnoreCase("SUCCESS") ){
															out.print("<div class='alert alert-success' role='alert'>Project has been deleted succesfully ! </div>");
														 session.setAttribute("project_delete", null); 
														}
														
														if(session.getAttribute("project_edit") != null && ((String)session.getAttribute("project_edit")).equalsIgnoreCase("SUCCESS") ){
															out.print("<div class='alert alert-success' role='alert'>Project has been edited succesfully ! </div>");
															session.setAttribute("project_edit", null);
														}
					%>


					<table class="table table-striped">
						<thead>
							<tr>
								<th>Project ID</th>
								<th>Project Title</th>
								<th>Project Description</th>
								<th>Short Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Status</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<%
								for(Object project : projectList){
							%>

							<%
								String pId = JsonPath.read(project.toString(),"$.projectId").toString();
							%>
							<tr>
								<td><form name="<%="form"+pId%>"
										action="../GetProjectDetails" method="post">
										<input name="projectId" type="hidden"
											value=<%=JsonPath.read(project.toString(), "$.projectId").toString()%>><a
											href="#"
											onclick="document.forms['<%="form"+pId%>'].submit(); return false;"><%=JsonPath.read(project.toString(), "$.projectId").toString()%></a>
									</form></td>
								<td><%=JsonPath.read(project.toString(), "$.title").toString()%></td>
								<td><%=JsonPath.read(project.toString(), "$.description").toString()%></td>
								<td><%=JsonPath.read(project.toString(), "$.shortName").toString()%></td>
								<td><%=JsonPath.read(project.toString(), "$.startDate").toString()%></td>
								<td><%=JsonPath.read(project.toString(), "$.endDate").toString()%></td>
								<td><%=JsonPath.read(project.toString(), "$.status").toString()%></td>
								<td><a href="#" id="editproject<%=pId%>"
									data-toggle="modal" data-target="#edit_project<%=pId%>"
									title="Edit" class="tooltip_element editProjectModel"><span
										class="glyphicon glyphicon-edit"></span></a> <a href="#"
									id="deleteproject<%=pId%>" data-toggle="modal"
									data-target="#delete_project<%=pId%>" title="Delete"
									class="tooltip_element deleteProjectModel"><span
										class="glyphicon glyphicon-remove"></span></a></td>
							</tr>

							<!--Edit Project Modal-->
							<div class="modal fade" id="edit_project<%=pId%>" tabindex="-1"
								role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content" id="edit_project_content<%=pId%>"></div>

								</div>
							</div>

							<!--Delete Project Modal-->
							<div class="modal fade" id="delete_project<%=pId%>" tabindex="-1"
								role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content" id="delete_project_content<%=pId%>"></div>
								</div>
							</div>
							<%
								}
							%>
						</tbody>
					</table>
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
		$('.date_fields').datepicker({
			'format' : 'yyyy-mm-dd'
		});
		
		$('.tooltip_element').tooltip();

		$('.date_fields').on(
				'change',
				function(e) {
					// Validate the date when user change it
					$('#add_project_form').bootstrapValidator(
							'revalidateField', 'project_start_date');
					$('#add_project_form').bootstrapValidator(
							'revalidateField', 'project_end_date');
				});

		$('.editProjectModel')
				.on(
						"click",
						function() {
							var projectId = parseInt(this.id.replace(
									"editproject", ""));

							$
									.get(
											'../GetProjectDetails?projectId='
													+ projectId + '',
											function(data) {
												var project = jQuery
														.parseJSON(data);

												var title = project['project'].title;
												var description = project['project'].description;
												var shortName = project['project'].shortName;
												var startDate = project['project'].startDate
														.substring(0, 10);
												var endDate = project['project'].endDate
														.substring(0, 10);
												var status = project['project'].status;

												var txt = '<form class="form-horizontal" id="edit_project_form'+ projectId +'" action="../EditProjectController" method="POST">'
														+ '<div class="modal-header">'
														+ '<button type="button" class="close" data-dismiss="modal">'
														+ '<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>'
														+ '</button>'
														+ '<h4 class="modal-title">Edit Project</h4>'
														+ '</div>'
														+ '<div class="modal-body">'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_project_title'+ projectId +'">Title</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_project_title'+ projectId +'" name="edit_project_title" class="input-xlarge form-control" type="text" value="'+title+'">'
														+ '</div>'
														+ '</div>'

														+ '<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_project_shortname'+ projectId +'">Short Name</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_project_shortname'+ projectId +'" name="edit_project_shortname" class="input-xlarge form-control" type="text" value="'+shortName+'">'
														+ '</div>'
														+ '</div>'

														+ '<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_project_description'+ projectId +'">Description</label>'
														+ '<div class="col-lg-6">'
														+ '<textarea id="edit_project_description'+ projectId +'" name="edit_project_description" class="input-xlarge form-control"  >'
														+ description
														+ '</textarea>'
														+ '</div>'
														+ '</div>'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_project_startdate'+ projectId +'">Start Date</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_project_startdate'+ projectId +'" name="edit_project_startdate" class="input-xlarge form-control" type="text" value="'+startDate+'">'
														+ '</div>'
														+ '</div>'
														+ '<input type="hidden" value="'+projectId+'" name="projectId">'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_project_enddate'+ projectId +'">End Date</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_project_enddate'+ projectId +'" name="edit_project_enddate" class="input-xlarge form-control" type="text" value="'+endDate+'">'
														+ '</div>'
														+ '</div></div>'
														+

														'<div class="modal-footer">'
														+ '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'
														+ '<button type="Submit" class="btn btn-success" >Save Changes</button>'
														+ '</div>' +

														'</form>'; // Create with jQuery

												$(
														'#edit_project_content'
																+ projectId)
														.html(txt);

											});

						});

		$('.deleteProjectModel')
				.on(
						"click",
						function() {
							var projectId = parseInt(this.id.replace(
									"deleteproject", ""));

							var txt = '<form class="form-horizontal" id="delete_project_form'+ projectId +'" action="../DeleteProjectController" method="POST">'
									+ '<div class="modal-header">'
									+ '<button type="button" class="close" data-dismiss="modal">'
									+ '<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>'
									+ '</button>'
									+ '<h4 class="modal-title">Delete Project</h4>'
									+ '</div>'
									+ '<div class="modal-body">'
									+ 'Are you sure you want to delete ?'
									+ '<input type="hidden" value="'+projectId+'" id="delete_project_id'+projectId+'" name="delete_project_id" >'
									+ '</div>'
									+ '<div class="modal-footer">'
									+ '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'
									+ '<button type="submit" class="btn btn-danger" >Delete</button>'
									+ '</div>' +

									'</div>' +

									'</form>';

							$('#delete_project_content' + projectId).html(txt);
						});
	</script>

	<script src="../../js/bootstrapValidator.js"></script>

	<script>
		$(document)
				.ready(
						function() {
							$('#add_project_form')
									.bootstrapValidator(
											{
												// To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													project_title : {
														message : 'The username is not valid',
														validators : {
															notEmpty : {
																message : 'The Project title is required and cannot be empty'
															}
														}
													},
													short_name : {
														message : 'The shortname is not valid',
														validators : {
															notEmpty : {
																message : 'The Project short name is required and cannot be empty'
															}
														}
													},
													project_start_date : {
														validators : {
															notEmpty : {
																message : 'The Project start date is required'
															},
															date : {
																format : 'YYYY-MM-DD',
																message : 'The Project start date is not valid'
															}
														}
													},
													project_end_date : {
														validators : {
															notEmpty : {
																message : 'The Project end date is required'
															},
															date : {
																format : 'YYYY-MM-DD',
																message : 'The Project end date is not valid'
															}
														}
													}
												}
											});
						});
	</script>
	<%
		}
	%>
</body>
</html>
