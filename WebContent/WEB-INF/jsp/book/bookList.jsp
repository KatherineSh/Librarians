<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div id="book-table" class="panel panel-default" style="margin-top: 30px;">

		<table id="table" class="table table-striped" 
			
			data-toggle="table" 
			data-url="${contextPath}/bookPage" 
			data-height="522" 
			
			data-content-type="application/json"
			data-data-type="json"
			
			data-data-field="rows"
			
			data-side-pagination="server" 
			data-pagination="true"
			data-page-size="10"
			data-page-list="[5, 10, 20, 50, 100, 200]" 
			data-search="true"
			data-search-time-out="2000">

		<thead>
			<tr>
				<th data-field="title" data-align="center" data-sortable="true">Title</th>
				<th data-field="author" data-align="center" data-sortable="true">Author</th>
				<th data-field="year" data-align="center" data-sortable="true">Item	Name</th>
				<th data-field="isbn" data-align="center" data-sortable="true">ISBN</th>
				<th data-field="description" data-align="center" data-sortable="true">Description</th>
			</tr>
		</thead>

	</table>	
</div>


<script type="text/javascript">
	$(function() {
		$('#table').bootstrapTable({
			
		}).on('search.bs.table',function(e, text) {
			var data = {'search': text};
			
			$.ajax({
				url:"/Librarians/searchBook",
				data: data,
				method: "GET"
			}).done(function(table) {
				//alert("response");
			   	redrowTable(table);
			});
			;
		});
	});
	
	function redrowTable(table){
		$('#table').bootstrapTable('load', table);
	}
</script>
