<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../utils/pageHeader.jsp"></jsp:include>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${userProfile.user}"></c:set>


<div class="row">
	<div class="col-md-4 profile-view-labels">
		
 			<h2>User information:</h2>
			<br/><br/>
			
			<div class="form-group">
				<label class="control-label col-md-6">User name:</label>
				<label class="control-label col-md-6"><c:out value="${user.name}"/></label>
			</div>
			<div class="form-group">
				<label class="control-label col-md-6">User e-mail:</label>
				<label class="control-label col-md-6"><c:out value="${user.email}"/></label>
			</div>
			<div class="form-group">
				<label class="control-label col-md-6">User birthday:</label>
				<label class="date-picker control-label col-md-6">
					<fmt:formatDate value="${user.birthday}"  type="date" pattern="dd-MM-yyyy"/>
				</label>
			</div>
			<div class="form-group">
				<label class="control-label col-md-6">User access rights:</label>
				<label class="control-label col-md-6"><c:out value="${user.role}"/></label>
			</div>
			
			<div class="form-group">
				<div class="col-xs-2">
					<button  type="button" class="btn btn-primary" onclick="javascript: window.location.replace('${contextPath}/editProfile');">Edit</button>
				</div>
				<c:if test="${user.role == 'USER'}">
					<div class="col-xs-2">
						<button  type="button" class="btn btn-primary" onclick="javascript: window.location.replace('${contextPath}/book/history/currentUser');">My books</button>
					</div>
				</c:if>
			</div>

			<div class="col-xs-offset-3 col-xs-9">
				<c:if test="${changesSaved != null}">
					Changes was saved successfully.
				</c:if>
			</div>

	</div>
</div>

<jsp:include page="../utils/pageFooter.jsp"></jsp:include>

