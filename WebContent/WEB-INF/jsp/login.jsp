<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Bootstrap 101 Template</title>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/datepicker.css" rel="stylesheet">
<link href="bootstrap/css/datepicker.less" rel="stylesheet">

<link href="css/styles.css" rel="stylesheet" type="text/css">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootstrap/js/bootstrap.js"></script>
<script src="bootstrap/js/bootstrap-datepicker.js"></script>

</head>
<body>

	<br />
	<h1>Hello, Librarians! Please, log in:</h1>
	<br />
	
	<div class="container" style="margin-left: 0;">
		
		<div class="row">
			<div class="col-md-6">
				<div class="col-xs-offset-3 col-xs-9">
					<h3>Sign in:</h3>
				</div>
				
				<form class="form-horizontal login-form" method="POST" action="./login">
					<input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}" />
		
					<div class="form-group">
						<label for="inputEmail" class="control-label col-xs-3">Login</label>
						<div class="col-xs-9">
							<input class="form-control" placeholder="Login" name="username">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="control-label col-xs-3">Password</label>
						<div class="col-xs-9">
							<input type="password" class="form-control" placeholder="Password"
								name="password">
						</div>
					</div>
					<!--<div class="form-group">
						<div class="col-xs-offset-2 col-xs-9">
							<div class="checkbox">
								<label><input type="checkbox"> Remember me</label>
							</div>
						</div>
					</div> -->
					<div class="col-xs-offset-3 col-xs-9">
						<p class="error">
							<c:if test="${param.error != null}">
								<c:out value="${error}"></c:out>
							</c:if>
						</p>
					</div>
					<div class="form-group">
						<div class="col-xs-offset-3 col-xs-9">
							<button type="submit" class="btn btn-primary">Login</button>
						</div>
					</div>
				</form>
				
			</div>
			<div class="col-md-6">
				<jsp:include page="user/registrationForm.jsp">
					<jsp:param value="birthday" name="fieldName"/>
				</jsp:include>
			</div>
		</div>
	</div>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>