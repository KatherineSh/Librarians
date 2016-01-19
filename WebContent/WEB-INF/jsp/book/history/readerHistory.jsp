<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../utils/pageHeader.jsp"></jsp:include>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:if test="${fn:length(historyList) > 0}">
	
	<div class="col-md-12 ">
			<h2>Your books history:</h2>
			<br/>
			
			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						<th>Title</th>
						<th>Author</th>
						<th>Year</th>
						<th>ISBN</th>
						<th>Instance Id</th>
						<th>State</th>
						<th>Taken</th>
						<th>Returned</th>
						<th>Expiration date</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${historyList}" var="history">
					
						<c:set var="instance" value="${history.bookInstance}"/>
						
						<c:set var="book" value="${history.bookInstance.book}"/>
						
						<tr id="${history.id}">						
							
							<td>${book.title}</td>
							<td>${book.author}</td>
							<td>${book.year}</td>
							<td>${book.isbn}</td>
							
							<td>${instance.id}</td>
							
							<td>${history.state}</td>
							<td>${history.dateWhenTaken}</td>
							<td>${history.dateWhenReturned}</td>
							<td>${history.expirationDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
	</div>
</c:if>
<c:if test="${fn:length(historyList) == 0}">
	No history
</c:if>

<div class="col-xs-2">
	<button type="button" class="btn btn-primary" onclick="javascript: window.location.replace('${contextPath}/profile');">Back</button>
</div>

<jsp:include page="../../utils/pageFooter.jsp"></jsp:include>