<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../utils/pageHeader.jsp"></jsp:include>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="row">
<div class="col-md-6">
	<form:form action="${contextPath}/addLibrarian"  method="POST"  modelAttribute="user" autocomplete="off"
	 class="form-horizontal login-form">
	
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
</div>
</div>


<jsp:include page="../utils/pageFooter.jsp"></jsp:include>
