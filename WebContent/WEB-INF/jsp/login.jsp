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

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<!-- By styles -->
<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>
<body>

	<br />
	<h1>Hello, Librarians! Please, log in:</h1>
	<br />
	<p class="error">
		<c:if test="${param.error != null}">
			<c:out value="${error}"></c:out>
		</c:if>
	</p>
	<br />

	<div class="bs-example">
		<form class="form-horizontal login-form" method="POST"
			action="./login">


			<div class="form-group">
				<label for="inputEmail" class="control-label col-xs-2">Login</label>
				<div class="col-xs-10">
					<input class="form-control" placeholder="Login" name="username">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="control-label col-xs-2">Password</label>
				<div class="col-xs-10">
					<input type="password" class="form-control" placeholder="Password"
						name="password">
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-offset-2 col-xs-10">
					<div class="checkbox">
						<label><input type="checkbox"> Remember me</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-offset-2 col-xs-10">
					<button type="submit" class="btn btn-primary">Login</button>
				</div>
			</div>
		</form>
	</div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>