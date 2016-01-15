<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../utils/pageHeader.jsp"></jsp:include>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:set var="bookId" value="${bookId}"/>

<br/>
<br/>
<c:if test="${fn:length(instances) == 0}">
	<div class="col-xs-4">
		<span>There is no instances connected to this book.</span>
		<br/><br/>
		<button type="button" class="btn btn-primary btn-lg" onclick="javascript: window.location.replace('${contextPath}/main');">Back</button>
	</div>
</c:if>

<c:if test="${fn:length(instances) > 0}">

	<c:set var="bookDetails" value="${instances[0].book}"/> 
	
	<div class="col-md-12 ">
	
		<div class="col-md-4 ">
			<h2>Book information:</h2>
			<br/>
			<div class="form-group col-md-12">
				<label class="control-label col-md-2">ISBN:</label> 
				<label class="control-label col-md-10"><c:out value="${bookDetails.isbn}" /></label>
			</div>
			<div class="form-group col-md-12">
				<label class="control-label col-md-2">Title:</label> 
				<label class="control-label col-md-10"><c:out value="${bookDetails.title}" /></label>
			</div>
			<div class="form-group col-md-12">
				<label class="control-label col-md-2">Author:</label> 
				<label class="control-label col-md-10"><c:out value="${bookDetails.author}" /></label>
			</div>
			<div class="form-group col-md-12">
				<label class="control-label col-md-2">Year:</label> 
				<label class="control-label col-md-10">
					<c:out value="${bookDetails.year}" />
					<c:if test="${bookDetails.year == null}"><span>-</span></c:if>	
				</label>
			</div>
			<div class="form-group col-md-12">
				<label class="control-label col-md-2">Category:</label> 
				<label class="control-label col-md-10"><c:out value="${bookDetails.category.categoryName}" /></label>
			</div>
			
			<br/>
			<div class="col-xs-2">
				<button type="button" class="btn btn-primary btn-lg" onclick="javascript: window.location.replace('${contextPath}/main');">Back</button>
			</div>
		</div>
		
		<div class="col-md-8 ">	
			<h2>Book instances:</h2>
			<br/>
			<table class="table table-bordered table-striped" style="width: 1000px;">
				<thead>
					<tr>
						<th>Instance id</th>
						<th>Is active</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${instances}" var="i">
						<tr id="${i.id}">						
							<td>${i.id}</td>
							<td>${i.active}</td>
							<td>
							<button type="button" class="btn btn-info"
								onclick="javascript: window.location.replace('${contextPath}/book/history?bookInstanceId=${i.id}&bookId=${bookId}');">See history</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
	</div>
</c:if>


<jsp:include page="../utils/pageFooter.jsp"></jsp:include>