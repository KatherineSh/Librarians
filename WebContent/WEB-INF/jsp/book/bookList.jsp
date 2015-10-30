<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="book-table" class="panel panel-default">

		<table id="table" 
			data-toggle="table" 
			data-url="/Librarians/bookPage" 
			data-height="450" 
			
			data-content-type="application/json"
			data-data-type="json"
			
			data-data-field="rows"
			
			data-side-pagination="server" 
			data-pagination="true"
			data-page-size="10"
			data-page-list="[5, 10, 20, 50, 100, 200]" 
			data-search="true">

		<thead>
			<tr>
				<th data-field="title" data-align="right" data-sortable="true">Title</th>
				<th data-field="author" data-align="right" data-sortable="true">Author</th>
				<th data-field="year" data-align="center" data-sortable="true">Item	Name</th>
				<th data-field="isbn" data-sortable="true">ISBN</th>
				<th data-field="description" data-sortable="true">Description</th>
			</tr>
		</thead>

	</table>

	<%--<jsp:include page="../utils/pagination.jsp">
		<jsp:param name="page_count" value="3" />
	</jsp:include>  --%>
</div>
