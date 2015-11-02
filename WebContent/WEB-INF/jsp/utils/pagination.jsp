<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- <c:set var="page_prefix" value="${pageContext.request.contextPath}"></c:set> --%>
<c:set var="page">${param.page_count}</c:set>


<script type="text/javascript">
	function updatePage(data){
		page_count = data.count;
		alert(page_count);
//-------------
		var paginationItems = $(".pagination").children();
		for (i = 0; i < page_count; i++) {
/* 			if(paginationItems[i] != null) {
				paginationItems[i].value = i;
			} else {
				
			} */
		}
	 	var myAwesomePagination = $("#my-awesome-pagination");
		
//------------		
	}
	
	function loadPage(){
	 $.ajax({
		url:"/Librarians/bookPage",
		type: "GET",
		dataType: "json",
		contentType : "application/json",
		success: 
		function(data) {
			console.log("RESULT: ", data);
			updatePage(data);
		},
		 error : function(e) {
			console.log("ERROR: ", e);
		}
	}); 
	}

</script>

<nav>
	<ul class="pagination" id="my-awesome-pagination">
		<li><a href="#" aria-label="Previous"> 
		<span aria-hidden="true">&laquo;</span>
		</a></li>
		 
		 <c:forEach begin="1" end="${page}" varStatus="pageIndex" >
			<li class="pagination-item" id="pagination-item-${pageIndex.index}">
				<a href="javascript:loadPage()">${pageIndex.index}</a>
			</li>
		</c:forEach>
		
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>