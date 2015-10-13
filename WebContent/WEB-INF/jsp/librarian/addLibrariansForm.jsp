<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form class="form-horizontal login-form" modelAttribute="user"  autocomplete="off" method="POST" action="./main">

	<h1>Create new librarian:</h1>
	
	<div class="form-group">
		<label for="inputEmail" class="control-label col-xs-2">Email</label>
		<div class="col-xs-10">
			<form:input path="email" type="email" class="form-control" placeholder="Email"/>
			<form:errors path="email" class="error" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputPassword" class="control-label col-xs-2">Password</label>
		<div class="col-xs-10">
			<form:input path="pass" type="password" class="form-control" placeholder="Password"/>
			<form:errors path="pass" class="error" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputPassword" class="control-label col-xs-2">Name</label>
		<div class="col-xs-10">
			<form:input path="name" type="text" class="form-control" placeholder="Name"/>
			<form:errors path="name" class="error"/>
		</div>
	</div>
	<div class="form-group">
		<label for="inputPassword" class="control-label col-xs-2">Birthday</label>
		<div class="col-xs-10">
			<jsp:include page="../utils/calendar.jsp">
				<jsp:param value="birthday" name="fieldName"/>
			</jsp:include>
		</div>
	</div>
	<div class="form-group">
		<div class="col-xs-offset-2 col-xs-10">
			<button type="submit" class="btn btn-primary">Create</button>
		</div>
	</div>
	
</form:form>