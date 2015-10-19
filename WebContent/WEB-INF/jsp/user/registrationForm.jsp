<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="registration_block">
	<div class="col-xs-offset-3 col-xs-9">
		<h3>Create new user account:</h3>
	</div>

	<form:form class="form-horizontal login-form" modelAttribute="user" method="POST" action="./register">

		<div class="form-group">
			<label class="control-label col-xs-3">Name</label>
			<div class="col-xs-9">
				<form:input path="name" class="form-control" placeholder="Name" />
				<form:errors path="name" class="error"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-3">Email</label>
			<div class="col-xs-9">
				<form:input path="email" type="email" class="form-control" placeholder="Email" />
				<form:errors path="email" class="error"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-3">Password</label>
			<div class="col-xs-9">
				<form:input path="pass" type="password" class="form-control" placeholder="Password" />
				<form:errors path="pass" class="error"/>
			</div>
		</div>
		
       <%-- <div class="form-group">
				<label class="control-label col-xs-3">Confirm password</label>
				<div class="col-xs-9">
					<form:input path="pass" type="password" class="form-control" placeholder="Password" />
					<form:errors path="pass" css="error"/>
				</div>
			</div> --%>
		
		<div class="form-group">
			<label class="control-label col-xs-3">Birthday</label>
			<div class="col-xs-9">
				<jsp:include page="../utils/calendar.jsp">
					<jsp:param value="birthday" name="fieldName"/>
				</jsp:include>
				<form:errors path="birthday" class="error"/>
			</div>
		</div>

		<div class="form-group">
			<div class="col-xs-offset-3 col-xs-9">
				<button type="submit" class="btn btn-primary">Register</button>
			</div>
		</div>
	</form:form>
	<div class="col-xs-offset-3 col-xs-9">
		<c:if test="${newUserCreated != null}">
			<h3>
				<c:out value="${newUserCreated}"></c:out>
				, on your email was sent confirmation link. Complete account creation please. 
			</h3>
		</c:if>
	</div>
</div>