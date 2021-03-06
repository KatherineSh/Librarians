<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../utils/pageHeader.jsp"></jsp:include>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="row">
	<div class="col-md-6">

		<form:form action="${contextPath}/editBook" method="POST" modelAttribute="book"
			class="form-horizontal login-form">
		
 			<h1>Edit the book:</h1>
 			<form:input hidden="true" path="id" type="text" value="${book.id}"></form:input>
		
			<div class="form-group">
				<label class="control-label col-xs-2">Title</label>
				<div class="col-xs-10">
					<form:input path="title" type="text" class="form-control" placeholder="Title" />
					<form:errors path="title" class="error" />
				</div>
			</div>
		
			<div class="form-group">
				<label class="control-label col-xs-2">Description</label>
				<div class="col-xs-10">
					<form:input path="description" type="text" class="form-control"	placeholder="Description" />
					<form:errors path="description" class="error" />
				</div>
			</div>
		
			<div class="form-group">
				<label class="control-label col-xs-2">Author</label>
				<div class="col-xs-10">
					<form:input path="author" type="text" class="form-control" placeholder="Author" />
					<form:errors path="author" class="error" />
				</div>
			</div>
		
			<div class="form-group">
				<label class="control-label col-xs-2">Year</label>
				<div class="col-xs-10">
					<form:input path="year" type="number" min="1900" max="2015"	class="form-control" placeholder="Year" />
					<form:errors path="year" class="error" />
				</div>
			</div>
		
			<div class="form-group">
				<label class="control-label col-xs-2">ISBN</label>
				<div class="col-xs-10">
					<form:input path="isbn" type="number" maxlength="13" class="form-control" placeholder="ISBN" />
					<form:errors path="isbn" class="error" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-xs-2">Count of instances</label>
				<div class="col-xs-10">
					<input name="instanceCount" type="number" readonly value="${fn:length(book.instances)}" min="0" class="form-control">
				</div>
			</div>
		
			 <div class="form-group">
				<label class="control-label col-xs-2">Book Category</label> 
				<div class="col-xs-7"> 
				
				<form:select path="category.id" class="form-control" id="dropdown-categories" >				 
 						<c:forEach items="${categories}" var="category"> 
	 						<c:choose>
		 						<c:when test="${category.id eq book.category.id}">
		 							<form:option selected="true" value="${category.id}" label="${category.categoryName}"></form:option>
			 					</c:when>
			 					<c:when test="${category.id ne book.category.id}">
			 						<form:option value="${category.id}" label="${category.categoryName}"></form:option>
								</c:when>
							</c:choose>
						 </c:forEach>
					</form:select>
											
					<form:errors path="category" class="error" />
				</div>
			</div> 
			
			<div class="form-group">
				<div class="col-xs-offset-2 col-xs-2">
					<form:button type="submit" class="btn btn-primary">Save changes</form:button>
				</div>
				<div class="col-xs-2">
					<button  type="button" class="btn btn-primary" onclick="javascript: window.location.replace('${contextPath}/main');">Back</button>
				</div>
			</div>
		</form:form>
	</div>
</div>
<%-- <c:out  value="${pageContext.request.requestURI}"/> --%>

<jsp:include page="../utils/pageFooter.jsp"></jsp:include>


