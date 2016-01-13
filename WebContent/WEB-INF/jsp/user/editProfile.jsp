<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<jsp:include page="../utils/pageHeader.jsp"></jsp:include>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<div class="row">
	<div class="col-md-4">
	
		<form:form action="${contextPath}/editUser" method="POST" modelAttribute="user"
			class="form-horizontal login-form">
			
 			<h2>User information:</h2>
			<br/><br/>
			
			<div class="form-group">
				<label class="control-label col-xs-3">User name:</label>
				<div class="col-xs-9">
					<form:input  path="name" type="text" value="${user.name}"></form:input>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3">User e-mail:</label>
				<div class="col-xs-9">
					<form:input  path="email" type="text" value="${user.email}"></form:input>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3">User birthday:</label>
				<div class="col-xs-9">
					<jsp:include page="../utils/calendar.jsp">
						<jsp:param value="birthday" name="fieldName"/>
					</jsp:include>
				</div>
			</div>


			<div class="form-group">
				<label class="control-label col-xs-3">User access rights:</label>
				<div class="col-xs-9">
					<c:choose>
						<c:when test="${user.role == 'LIBRARIAN'}">Librarian</c:when>
						<c:when test="${user.role == 'USER'}">User</c:when>
						<c:when test="${user.role == 'ADMIN'}">Admin</c:when>
					</c:choose>

				</div>
			</div>
			
			<div class="form-group">
				<div class="col-xs-offset-3 col-xs-2">
					<form:button type="submit" class="btn btn-primary">Save changes</form:button>
				</div>
			</div>
				
		</form:form>
			
	</div>
</div>

<jsp:include page="../utils/pageFooter.jsp"></jsp:include>

