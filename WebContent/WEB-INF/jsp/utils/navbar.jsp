<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>	

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav id="navbar" class="navbar navbar-default navbar-fixed-top" role="navigation" >
	<div class="container-fluid" >
		<div class="navbar-header"></div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			
			<ul class="nav navbar-nav navbar-left">
				<li><a href="${contextPath}/main">Main </a></li>
				
				<sec:authorize access="hasAuthority('LIBRARIAN')">
					<li><a href="${contextPath}/addBook">Add book</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('LIBRARIAN')">
					<li><a href="${contextPath}/userList">View users</a></li>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('ADMIN')"> 
					<li><a href="${contextPath}/addLibrarian">Add librarian</a></li>
				</sec:authorize>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li><a
					onclick="document.getElementById('logout_form').submit();"
					style="cursor: pointer">Log out</a></li>
			</ul>
			
			<form:form action="${contextPath}/logout" method="post" id="logout_form" class="navbar-form navbar-right">
				<!-- <button type="submit" class="btn btn-default">Log out</button> -->
			</form:form>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="current-user-label">Authorized:  
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="principal.username"/>
					</sec:authorize>
				</li>
				<li class="current-user-label">Role:
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="authorities" var="authorities"/>
					</sec:authorize>
					<c:forEach items="${authorities}" var="authority" varStatus="status">
						<c:out value="${authority}" />
						<c:if test="${ ! status.last}">&nbsp</c:if>
					</c:forEach>
				</li>
			</ul>

		</div>
	</div>
</nav>

 <script type="text/javascript">
	$(document).ready(function() {
		var path = window.location.href;
		$('#navbar a').each(function( index ) {
			
		if(path.indexOf(this.href) > -1){
			//if(path.contains(this.href)) {
				this.parentElement.className = 'active';
			}
		});
	});
</script> 