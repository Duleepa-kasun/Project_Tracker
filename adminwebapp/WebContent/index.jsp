<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="css/bootstrapValidator.css">

<title>Log in</title>
</head>

<body>

<div class="container" style="margin-top:30px">

<div class="col-md-4 col-md-offset-4">
  <h1><img src="resources/LoginBanner.png" height="100" width="100" />Project Tracker</h1>

  </div>
</div>


<div class="container" style="margin-top:30px">

<div class="col-md-4 col-md-offset-4">

    <div class="panel panel-default">
  <div class="panel-heading"><h3 class="panel-title"><strong>Sign in </strong></h3></div>
  <div class="panel-body">
   <form role="form" id="loginform" method="post" action="LoginController">
  <div class="form-group">
    <label for="exampleInputEmail1">Email</label>
    <input type="email" class="input form-control"  id="exampleInputEmail1" name="email" placeholder="Enter email">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password </label>
    <input type="password" class="input form-control" id="exampleInputPassword1" name="password" placeholder="Password">
  </div>
  <%
  	String s = (String)request.getSession().getAttribute("inputError");
    		if(s!=null&&!s.trim().equalsIgnoreCase("")){
    			session.setAttribute("inputError", null);
  %>
  <div>
  		<label ><%=s%></label>
  </div>
  <%} %>
  <button type="submit" class="btn btn-success">Sign in</button>
</form>
  </div>
</div>
</div>
</div>


 <script src="js/jquery.js"></script>
 <script src="bootstrap/js/bootstrap.min.js"></script>
 <script src="js/bootstrapValidator.js"></script>

        <script>
            $(document).ready(function() {
                $('#loginform').bootstrapValidator({
                    // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        exampleInputEmail1: {
                            message: 'Invalid email address',
                            validators: {
                                notEmpty: {
                                    message: 'Email is required and cannot be empty'
                                }
                            }
                        },
                        exampleInputPassword1: {
                            validators: {
                                notEmpty: {
                                    message: 'Password is required and cannot be empty'
                                },
                                stringLength: {
                                	min : 8,
                                    message: 'Password must have at least 8 characters'
                                }
                            }
                        }
                    }
                });
            });
        </script>

</body>
</html>


