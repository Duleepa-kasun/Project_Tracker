<%@page import="java.util.ArrayList"%>
<%@page import="com.jayway.jsonpath.JsonPath"%>
<%@page import="com.jayway.jsonpath.ReadContext"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.wso2.projecttracker.app.model.UserService"%>
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
	href="${pageContext.request.contextPath}/css/bootstrapValidator.css">

<title>User</title>
</head>

<%
	if(session.getAttribute("token")==null){
		response.sendRedirect("../index.jsp");
	}else{

%>
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

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<form class="form-horizontal" id="add_user_form"
					action="../CreateUserController" method="post">
					<fieldset>

						<!-- Form Name -->
						<legend>Add New User</legend>
						<%
							if (request.getSession().getAttribute("user_input_error") != null) {
						%>
						<div style="color: red">
							<%=(String) request.getSession().getAttribute(
						"user_input_error")%>
						</div>
						<%
							}
						%>

						<!-- Text input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="user_name">Name</label>
							<div class="col-lg-6">
								<input id="user_name" name="user_name"
									placeholder="Name of the user"
									class="input-xlarge form-control" required="" type="text">

							</div>
						</div>

						<!-- Text input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="user_username">Username</label>
							<div class="col-lg-6">
								<input id="user_username" name="user_username"
									placeholder="Username for this user"
									class="input-xlarge form-control" required="" type="text">

							</div>
						</div>

						<!-- Password input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="user_password">Password</label>
							<div class="col-lg-6">
								<input id="user_password" name="user_password"
									placeholder="Password for this user"
									class="input-xlarge form-control" required="" type="password">

							</div>
						</div>

						<!-- Password input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="user_confirm_password">Confirm
								Password</label>
							<div class="col-lg-6">
								<input id="user_confirm_password" name="user_confirm_password"
									placeholder="Confirm the password"
									class="input-xlarge form-control" required="" type="password">
							</div>
						</div>

						<!-- Email input-->
						<div class="form-group">
							<label class="col-lg-2 control-label" for="user_email">Email</label>
							<div class="col-lg-6">
								<div class="input-group">
									<input id="user_email" name="user_email"
										placeholder="Email address of the user"
										class="input-xlarge form-control" required="" type="email">
									<div class="input-group-addon">
										<span class="glyphicon glyphicon-envelope"></span>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label" for="user_role">Role</label>
							<div class="col-lg-6">
								<select class="form-control" id="user_role" name="user_role">
									<option>admin</option>
									<option>user</option>
								</select>
							</div>
						</div>



						<!-- Button -->
						<div class="form-group">
							<label class="col-lg-2 control-label"></label>
							<div class="col-lg-6">
								<button id="add_user" type="submit" name="add_user"
									class="btn btn-lg btn-primary">Add</button>
							</div>
						</div>

					</fieldset>
				</form>

				<h2 class="sub-header">Users</h2>

				<%
					UserService userService = new UserService();
					String token = (String)request.getSession().getAttribute("token");
					JSONObject users = userService.getAllUsers(token);
					ReadContext con = JsonPath.parse(users.toString());
					ArrayList<Object> userList = con.read("$.users.user[*]");
					
					
					if(session.getAttribute("user_delete") != null && ((String)session.getAttribute("user_delete")).equalsIgnoreCase("success") ){
						out.print("<div class='alert alert-success' role='alert'>User has been deleted succesfully ! </div>");
						session.setAttribute("user_delete", null);
					}
					
				%>

				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Email</th>
								<th>Role</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<% for(Object user : userList){
						%>
					
							<tr>
								<% String uId = JsonPath.read(user.toString(), "$.userId").toString(); %>
								<td><form id="form1" name="<%="form"+uId%>" method="post" action="../GetUserController"><input name="userId" type="hidden" value=<%=JsonPath.read(user.toString(), "$.userId").toString() %>><a href="#" onclick="document.forms['<%="form"+uId%>'].submit(); return false;"><%=JsonPath.read(user.toString(), "$.userId").toString() %></a></form></td>
								<td><%=JsonPath.read(user.toString(), "$.name").toString() %></td>
								<td><%=JsonPath.read(user.toString(), "$.email").toString() %></td>
								<td><%=JsonPath.read(user.toString(), "$.role").toString() %></td>
								<td><a href="#" id="edituser<%=uId%>" data-toggle="modal"
									data-target="#edit_user<%=uId%>" title="Edit"
									class="tooltip_element editUserModel"><span
										class="glyphicon glyphicon-edit"></span></a> <a href="#"
									id="deleteuser<%=uId%>" data-toggle="modal"
									data-target="#delete_user<%=uId%>" title="Delete"
									class="tooltip_element deleteUserModel"><span
										class="glyphicon glyphicon-remove"></span></a>
										
										</td>
							</tr>
							
							<!--Edit User Modal-->
							<div class="modal fade" id="edit_user<%=uId%>" tabindex="-1"
								role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content" id="edit_user_content<%=uId%>"></div>

								</div>
							</div>

							<!--Delete User Modal-->
							<div class="modal fade" id="delete_user<%=uId%>" tabindex="-1"
								role="dialog" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content" id="delete_user_content<%=uId%>"></div>
								</div>
							</div>
							
							
							<%} %>
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
	<script type="text/javascript">
		$('.tooltip_element').tooltip();
	</script>
	<script
		src="${pageContext.request.contextPath}/js/bootstrapValidator.js"></script>

	<script>
		$(document)
				.ready(
						function() {
							$('#add_user_form')
									.bootstrapValidator(
											{
												// To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													user_name : {
														message : 'The name is not valid',
														validators : {
															notEmpty : {
																message : 'The Name is required and cannot be empty'
															}
														}
													},
													user_username : {
														message : 'The username is not valid',
														validators : {
															notEmpty : {
																message : 'The Username is required and cannot be empty'
															}
														}
													},
													user_email : {
														message : 'The email is not valid',
														validators : {
															notEmpty : {
																message : 'The Email is required and cannot be empty'
															},
															emailAddress : {
																message : 'The Email is not valid'
															}
														}
													},
													user_password : {
														message : 'The password is not valid',
														validators : {
															notEmpty : {
																message : 'The Password is required and cannot be empty'
															},
															stringLength : {
																min : 8,
																message : 'The Password should atleast contain 8 characters'
															},
															identical : {
																field : 'user_confirm_password',
																message : 'The password and its confirm are not the same'
															}
														}
													},
													user_confirm_password : {
														message : 'The confirm password is not valid',
														validators : {
															notEmpty : {
																message : 'The Password is required and cannot be empty'
															},
															stringLength : {
																min : 8,
																message : 'The Password should atleast contain 8 characters'
															},
															identical : {
																field : 'user_password',
																message : 'The password and its confirm are not the same'
															}
														}
													}
												}
											});
						});
	</script>
	
	<script type="text/javascript">
		$('.editUserModel')
				.on(
						"click",
						function() {
							var userId = parseInt(this.id.replace("edituser",
									""));

							$
									.get(
											'../GetUserController?userId='
													+ userId + '',
											function(data) {
												var user = jQuery
														.parseJSON(data);

												var name = user['user'].name;
												var username = user['user'].username;
												var email = user['user'].email;
												var role = user['user'].role;

												var txt = '<form class="form-horizontal" id="edit_user_form'+ userId +'" >'
														+ '<div class="modal-header">'
														+ '<button type="button" class="close" data-dismiss="modal">'
														+ '<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>'
														+ '</button>'
														+ '<h4 class="modal-title">Edit User</h4>'
														+ '</div>'
														+ '<div class="modal-body">'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_user_name'+ userId +'">Name</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_user_name'+ userId +'" name="edit_user_name'+ userId +'"'+
									' class="input-xlarge form-control"'+
									' type="text" value="'+name+'">'
														+

														'</div>'
														+ '</div>'
														+ '<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_user_username'+ userId +'">Username</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_user_username'+ userId +'" name="edit_user_username"'+
							   					'class="input-xlarge form-control" type="text" value="'+username+'">'
														+ '</div>'
														+ '</div>'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_user_password'+ userId +'">Password</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_user_password'+ userId +'" name="edit_user_password"'+
							   					'class="input-xlarge form-control"  type="password">'
														+ '</div>'
														+ '</div>'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label"'+
							   				'for="edit_user_confirm_password'+ userId +'">Confirm Password</label>'
														+ '<div class="col-lg-6">'
														+ '<input id="edit_user_confirm_password'+ userId +'"'+
							   					'name="edit_user_confirm_password"'+
							   					'class="input-xlarge form-control" type="password">'
														+ '</div>'
														+ '</div>'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_user_email'+ userId +'">Email</label>'
														+ '<div class="col-lg-6">'
														+ '<div class="input-group">'
														+ '<input id="edit_user_email'+ userId +'" name="edit_user_email"'+
							   						'class="input-xlarge form-control"  type="email" value="'+email+'">'
														+ '<div class="input-group-addon">'
														+ '<span class="glyphicon glyphicon-envelope"></span>'
														+ '</div>'
														+ '</div>'
														+ '</div>'
														+ '</div>'
														+

														'<div class="form-group">'
														+ '<label class="col-lg-2 control-label" for="edit_user_role'+ userId +'">Role</label>'
														+ '<div class="col-lg-6">'
														+ '<select class="form-control" id="edit_user_role'+ userId +'" name="user_role">'
														+ '<option>admin</option>'
														+ '<option>user</option>'
														+ '</select>'
														+ '</div>'
														+ '</div>'

														+ '</div>'
														+

														'<div class="modal-footer">'
														+ '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'
														+ '<button type="button" class="btn btn-success" onclick="validate('+ userId + ')">Save Changes</button>'
														+ '</div>'+

												'</div>' +

												'</form>'; // Create with jQuery

												$('#edit_user_content' + userId)
														.html(txt);

											});

						});

		$('.deleteUserModel')
				.on(
						"click",
						function() {
							var userId = parseInt(this.id.replace("deleteuser",
									""));

							var txt = '<form class="form-horizontal" id="delete_user_form'+ userId +'" action="../DeleteUserController" method="POST">'
									+ '<div class="modal-header">'
									+ '<button type="button" class="close" data-dismiss="modal">'
									+ '<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>'
									+ '</button>'
									+ '<h4 class="modal-title">Delete User</h4>'
									+ '</div>'
									+ '<div class="modal-body">'
									+ 'Are you sure you want to delete ?'
									+ '<input type="hidden" value="'+userId+'" id="delete_user_id'+userId+'" name="delete_user_id" >'
									+ '</div>'
									+ '<div class="modal-footer">'
									+ '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'
									+ '<button type="submit" class="btn btn-danger" >Delete</button>' + '</div>' +

									'</div>' +

									'</form>';

							$('#delete_user_content' + userId).html(txt);
						});

		function validate(userId) {
	
			var obj = jQuery
					.parseJSON('{"feedbackIcons" : {"valid" : "glyphicon glyphicon-ok",'
							+ '"invalid" : "glyphicon glyphicon-remove","validating" : "glyphicon glyphicon-refresh"},'
							+ '"fields" : {"edit_user_name4'
							
							+ '" : {"message" : "The name is not valid",'
							+ '"validators" : {"notEmpty" : {"message" : "The Name is required and cannot be empty"}}}'
							+ ','
							+ '"edit_user_username'
							+ userId
							+ '" : {'
							+ '"message" : "The username is not valid",'
							+ '"validators" : {'
							+ '"notEmpty" : {'
							+ '"message" : "The Username is required and cannot be empty"'
							+ '}'
							+ '}'
							+ '},'
							+ '"edit_user_email'
							+ userId
							+ '" : {'
							+ '"message" : "The email is not valid",'
							+ '"validators" : {'
							+ '"notEmpty" : {'
							+ '"message" : "The Email is required and cannot be empty"'
							+ '},'
							+ '"emailAddress" : {'
							+ '"message" : "The Email is not valid"'
							+ '}'
							+ '}'
							+ '},'
							+ '"edit_user_password'
							+ userId
							+ '" : {'
							+ '"message" : "The password is not valid",'
							+ '"validators" : {'
							+ '"notEmpty" : {'
							+ '"message" : "The Password is required and cannot be empty"'
							+ '},'
							+ '"stringLength" : {'
							+ '"min" : 8,'
							+ '"message" : "The Password should atleast contain 8 characters"'
							+ '},'
							+ '"identical" : {'
							+ '"field" : "edit_user_confirm_password'
							+ userId
							+ '",'
							+ '"message" : "The password and its confirm are not the same"'
							+ '}'
							+ '}'
							+ '},'
							+ '"edit_user_confirm_password'
							+ userId
							+ '" : {'
							+ '"message" : "The confirm password is not valid",'
							+ '"validators" : {'
							+ '"notEmpty" : {'
							+ '"message" : "The Password is required and cannot be empty"'
							+ '},'
							+ '"stringLength" : {'
							+ '"min" : 8,'
							+ '"message" : "The Password should atleast contain 8 characters"'
							+ '},'
							+ '"identical" : {'
							+ '"field" : "edit_user_password'
							+ userId
							+ '",'
							+ '"message" : "The password and its confirm are not the same"'
							+ '}' + '}' + '}' + '}}');
			
			alert();
			
			$('#edit_user_form4')
			.bootstrapValidator(
					{
						// To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
						feedbackIcons : {
							valid : 'glyphicon glyphicon-ok',
							invalid : 'glyphicon glyphicon-remove',
							validating : 'glyphicon glyphicon-refresh'
						},
						fields : {
							edit_user_name4 : {
								message : 'The name is not valid',
								validators : {
									notEmpty : {
										message : 'The Name is required and cannot be empty'
									}
								}
							
							}
						}
					}
					);
			alert();

		}


	</script>
<%
	} 
	%>
</body>
</html>
