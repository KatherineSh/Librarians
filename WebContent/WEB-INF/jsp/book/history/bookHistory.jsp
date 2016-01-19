<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../utils/pageHeader.jsp"></jsp:include>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%-- <c:set var="bookId" value="${historyList[0].bookInstance.book.id}"/> --%>
<c:set var="bookId" value="${bookId}"/>


<br/>
<br/>
<c:if test="${fn:length(historyList) == 0}">
	<div class="col-xs-4">
		<span>Was found no history according to this book instance.</span>
		<br/><br/>
		<button type="button" class="btn btn-lg btn-primary" onclick="javascript: window.location.replace('${contextPath}/book/instances?bookId=${bookId}');">Back</button>
	</div>	
</c:if>

<br/>
<c:if test="${fn:length(historyList) > 0}">
	<table class="table table-bordered table-striped">
		<thead>
			<tr style="text-align: center;">
				<th>User</th>
				<th>In state</th>
				<th>Date of taking</th>
				<th>Date of returning</th>
				<th>Expiration date (need return)</th>
			</tr>
		</thead>
		
		<tbody>
				<c:forEach items="${historyList}" var="history">
					<tr id="${history.id}">
					
					<!-- //need to get book instance here and remove add the same details -->
						<td>${history.reader.name}</td>
						<td>${history.state}</td>
						<td>${history.dateWhenTaken}</td>
						<td>${history.dateWhenReturned}</td>
						<td>${history.expirationDate}</td>
					</tr>
				</c:forEach>
		
		</tbody>
	</table>
	<div class="col-xs-4">
		<br/><br/>
		<button type="button" class="btn btn-lg btn-primary" onclick="javascript: window.location.replace('${contextPath}/book/instances?bookId=${bookId}');">Back</button>
	</div>	
</c:if>

<jsp:include page="../../utils/pageFooter.jsp"></jsp:include>

