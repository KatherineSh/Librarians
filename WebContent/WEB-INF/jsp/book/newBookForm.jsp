<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="../utils/pageHeader.jsp"></jsp:include>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="row">
	<div class="col-md-6">

		<form:form action="${contextPath}/newBook" method="POST" modelAttribute="book"
			class="form-horizontal login-form">
		
			<h1>Add a new book:</h1>
		
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
					<div class="col-xs-7">
						<%-- <form:input path="author" type="text" class="form-control" placeholder="Author" /> --%>
						<form:select path="author" class="form-control" id="dropdown-authors">
							<option value="" disabled selected>Select your option</option>
	 						<c:forEach items="${authorsList}" var="author"> 
								<form:option value="${author.name}" label="${author.name}"></form:option>
							 </c:forEach>
						</form:select>
						<form:errors path="author" class="error" />
					</div>
					<div class="col-xs-3"> 
						<button type="button" class="btn btn-primary" onclick="javascript:showModalWindow('addingAuthorModalWindow')">Add Author</button>
					</div>					
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
					<input name="instanceCount" type="number" value="0" min="0" class="form-control">
				</div>
			</div>
		
			<div class="form-group">
				<label class="control-label col-xs-2">Book Category</label> 
				<div class="col-xs-7"> 
					<form:select path="category.id" class="form-control" id="dropdown-categories">
						<option value="" disabled selected>Select your option</option>
 						<c:forEach items="${categories}" var="category"> 
							<form:option value="${category.id}" label="${category.categoryName}"></form:option>
						 </c:forEach>
					</form:select>
					<form:errors path="category" class="error" />
				</div>
				
				<div class="col-xs-2"> 
					<button type="button" class="btn btn-primary" onclick="javascript:showModalWindow('addingCategoryModalWindow')">Add Category</button>
				</div>					
				
				<div class="form-group">
					<label class="control-label col-xs-10">
						<c:if test="${isCategoryAdded}">
							<span>New category was successfully added.</span>
						</c:if>
					</label>
				</div>			
			</div>
			
			<div class="form-group">
				<div class="col-xs-offset-2 col-xs-10">
					<form:button class="btn btn-primary">Add book</form:button>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-xs-5">
					<c:if test="${isBookAdded}">
						<span>New book was successfully added.</span>
					</c:if>
				</label>
			</div>			
				
		</form:form>


<!-- 	<div id="myModal" class="modal fade" aria-hidden="false">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="javascript: clearModalWindow()">&times;</button>
	                <h4 class="modal-title">Adding a new book category</h4>
	            </div>
	      	<div class="modal-body">
					<div class="form-group">
						<label class="control-label col-xs-2">Category name</label>
						<div class="col-xs-10">
							<input id="categoryName" type="text" maxlength="255" class="form-control" placeholder="Name" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-10"> 
							<span class="error" id="error"></span>
						</label>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" aria-hidden="true" onclick="javascript: clearModalWindow()">Close</button>
					<button type="button" class="btn btn-primary" aria-hidden="false" onclick="javascript: sendAddingCategory($('#categoryName').val());" >Add category</button>
				</div>
			</div>
	    </div>
	</div>
 -->
 
 	<jsp:include page="../utils/addingModalWindow.jsp">
		 <jsp:param value="addingCategoryModalWindow" name="windowName"/>
		 <jsp:param value="Adding a new category" name="titleText"/>
		 <jsp:param value="Category name" name="labelText"/>
		 <jsp:param value="categoryName" name="inputName"/>
		 <jsp:param value="Add	category" name="buttonText"/>
		 <jsp:param value="addCategory" name="actionMethodName"/>
	 </jsp:include>
	 
	 <jsp:include page="../utils/addingModalWindow.jsp">
		 <jsp:param value="addingAuthorModalWindow" name="windowName"/>
		 <jsp:param value="Adding a new author" name="titleText"/>
		 <jsp:param value="Author name" name="labelText"/>
		 <jsp:param value="authorName" name="inputName"/>
		 <jsp:param value="Add	author" name="buttonText"/>
		 <jsp:param value="addAuthor" name="actionMethodName"/>
	 </jsp:include>
</div>
</div>

<jsp:include page="../utils/pageFooter.jsp"></jsp:include>

<script type="text/javascript">

	function addAuthor(authorName){
	
		if(authorName == "") {
			$('#error').text("Name should be not empty");
		} 
		else {
			var data = {"authorName" : authorName};
			$.ajax({
				url: '/Librarians/authors',
				data: data,
				method: "GET"
			}).done(function(result){
				if(result.isAuthorAdded == true) {
					$("#addingAuthorModalWindow").modal('hide');
					$('#error').text("");
					
					//updatCategories(result.updatedCategories);
					
				} else if (typeof result.isAuthorAdded === "undefined") {
					$('#error').text("Only librarian have permission to add new category.");
				} else 
					/* if(result.isCategoryExisted == true) {
					$('#error').text("The tipped category name is already existed. Duplication is not allowed.");
				} else */
				{
					$('#error').text("Name should be not empty");
				}
			});
		}
	}

	function addCategory(categoryName){

		if(categoryName == "") {
			$('#error').text("Name should be not empty");
		} 
		else {
			var data = {"categoryName" : categoryName};
			$.ajax({
				url:"/Librarians/addCategory",
				data: data,
				method: "GET"
			}).done(function(result){
				if(result.isCategoryAdded == true) {
					$("#addingCategoryModalWindow").modal('hide');
					$('#error').text("");
					
					updatCategories(result.updatedCategories);
					
				} else if (typeof result.isCategoryAdded === "undefined") {
					$('#error').text("Only librarian have permission to add new category.");
				} else if(result.isCategoryExisted == true) {
					$('#error').text("The tipped category name is already existed. Duplication is not allowed.");
				} else {
					$('#error').text("Name should be not empty");
				}
			});
		}
	}
	
	function updatCategories(updatedCategories) {
		var length = updatedCategories.length;
		if(length != 0){		
			var newOption = $("<option></option>");
			newOption.attr("id", updatedCategories[length - 1].id);
			newOption.text(updatedCategories[length - 1].categoryName);
			
			$("#dropdown-categories").append(newOption);
		}
	}
</script>
