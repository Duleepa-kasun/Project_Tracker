<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%
	if(session.getAttribute("token")==null){
		response.sendRedirect("../index.jsp");
	}else{

%>

<%
	String userId = request.getParameter("user_id");
	out.print(userId);
%>
<form class="form-horizontal" id="edit_user_form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">Edit User</h4>
    </div>
    <div class="modal-body">


        <!-- Text input-->
        <div class="form-group">
            <label class="col-lg-2 control-label" for="edit_user_name">Name</label>
            <div class="col-lg-6">
                <input id="edit_user_name" name="edit_user_name" placeholder="Name of the user" class="input-xlarge form-control" required="" type="text">

            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-lg-2 control-label" for="edit_user_username">Username</label>
            <div class="col-lg-6">
                <input id="edit_user_username" name="edit_user_username" placeholder="Username for this user" class="input-xlarge form-control" required="" type="text">

            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-lg-2 control-label" for="edit_user_password">Password</label>
            <div class="col-lg-6">
                <input id="edit_user_password" name="edit_user_password" placeholder="Password for this user" class="input-xlarge form-control" required="" type="password">

            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-lg-2 control-label" for="edit_user_confirm_password">Confirm Password</label>
            <div class="col-lg-6">
                <input id="edit_user_confirm_password" name="edit_user_confirm_password" placeholder="Confirm the password" class="input-xlarge form-control" required="" type="password">
            </div>
        </div>

        <!-- Email input-->
        <div class="form-group">
            <label class="col-lg-2 control-label" for="edit_user_email">Email</label>
            <div class="col-lg-6">
                <div class="input-group">
                    <input id="edit_user_email" name="edit_user_email" placeholder="Email address of the user" class="input-xlarge form-control" required="" type="email">
                    <div class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-2 control-label" for="edit_user_role">Role</label>
            <div class="col-lg-6">
                <select class="form-control" id="edit_user_role" name="user_role">
                    <option>admin</option>
                    <option>user</option>
                </select>
            </div>
        </div>



        <!-- Button -->
        <!--        <div class="form-group">
                    <label class="col-lg-2 control-label" ></label>
                    <div class="col-lg-6">
                        <button id="add_user" name="add_user" class="btn btn-lg btn-primary">Add</button>
                    </div>
                </div>-->


    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button id="edit_user" name="edit_user" class="btn btn-success">Save Changes</button>
    </div>

</form>

<script>
    $(document).ready(function() {
        $('#edit_user_form').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                edit_user_name: {
                    message: 'The name is not valid',
                    validators: {
                        notEmpty: {
                            message: 'The Name is required and cannot be empty'
                        }
                    }
                },
                edit_user_username: {
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {
                            message: 'The Username is required and cannot be empty'
                        }
                    }
                },
                edit_user_email: {
                    message: 'The email is not valid',
                    validators: {
                        notEmpty: {
                            message: 'The Email is required and cannot be empty'
                        },
                        emailAddress: {
                            message: 'The Email is not valid'
                        }
                    }
                },
                edit_user_password: {
                    message: 'The password is not valid',
                    validators: {
                        notEmpty: {
                            message: 'The Password is required and cannot be empty'
                        },
                        stringLength: {
                            min: 8,
                            message: 'The Password should atleast contain 8 characters'
                        },
                        identical: {
                            field: 'edit_user_confirm_password',
                            message: 'The password and its confirm are not the same'
                        }
                    }
                },
                edit_user_confirm_password: {
                    message: 'The confirm password is not valid',
                    validators: {
                        notEmpty: {
                            message: 'The Password is required and cannot be empty'
                        },
                        stringLength: {
                            min: 8,
                            message: 'The Password should atleast contain 8 characters'
                        },
                        identical: {
                            field: 'edit_user_password',
                            message: 'The password and its confirm are not the same'
                        }
                    }
                }
            }
        });
    });
</script>
<%} %>
