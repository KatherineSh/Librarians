<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../utils/pageHeader.jsp"></jsp:include>
 <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
 
 <div class="container" style="margin-left: 0;">
 
	 <div id="user-table" class="panel panel-default" style="margin-top: 30px;">
	
			<table id="table" class="table table-striped" 
				
				data-toggle="table" 
				data-url="${contextPath}/userPage" 
				data-height="522" 
				
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
					<th data-field="name" data-align="center" data-sortable="true">Name</th>
					<th data-field="email" data-align="center" data-sortable="true">Email</th>
					<th data-field="birthday" data-align="center" data-sortable="true">Birthday</th>
					<th data-field="enabled" data-align="center" data-sortable="true">Enabled</th>
				</tr>
			</thead>
	
		</table>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('#table').bootstrapTable({
		}).on('load-success.bs.table', function() {
			
			if($("#search-label").length > 0) {
				var search = $('#user-table').find('div.search input');
				$(search).before("<label id=\"search-label\">Search by name or e-mail:</label>");
			}
		});
	});
	
	
</script>

<jsp:include page="../utils/pageFooter.jsp"></jsp:include>