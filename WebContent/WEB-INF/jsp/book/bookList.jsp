<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<sec:authorize access="hasAuthority('LIBRARIAN')" var="isAuthorizedLibrarian"></sec:authorize>

<div id="book-table" class="panel panel-default" style="margin-top: 30px;">

		<table id="book-list-table" class="table table-striped" 
			
			data-toggle="book-list-table" 
			data-url="${contextPath}/bookPage" 
			data-height="730" 
			
			
			data-content-type="application/json"
			data-data-type="json"
			
			data-data-field="rows"
			
			data-side-pagination="server" 
			data-pagination="true"
			data-page-size="10"
			data-page-list="[5, 10, 20, 50, 100, 200]" 
			data-search="true"
			data-search-time-out="2000"
			data-unique-id="id">

		<thead>
			<tr>
				<th class="col-md-2" data-field="title" data-align="center" data-sortable="true">Title</th>
				<th class="col-md-2" data-field="author" data-align="center" data-sortable="true">Author</th>
				<th class="col-md-1"data-field="year" data-align="center" data-sortable="true">Year</th>
				<th class="col-md-1" data-field="isbn" data-align="center" data-sortable="true">ISBN</th>
				<th class="col-md-2" data-field="description" data-align="center" data-sortable="true">Description</th>
				<th class="col-md-2" data-field="category.categoryName" data-align="center" data-sortable="true">Category</th>
				<th class="col-md-2" data-align="center" style="text-align:center; vertical-align:middle;">Action</th>
			</tr>
		</thead>

	</table>	
</div>
<script type="text/javascript">
		$(function() {
			$('#book-list-table').bootstrapTable({
		}).on('search.bs.table', function() {
			var rows = $("#book-list-table").find(" tbody tr");
			
			$.each(rows, function(i, val){
				var tdInside = $(val).children();
				var firstTd = tdInside.first();
			
				var newContent = $("<a href='' ></a>");
				newContent.attr("href","${contextPath}/editBook?bookId="+$(val).data('uniqueid'));
				newContent.text(firstTd.text())
				
				firstTd.html(newContent);
			});
		});
	});

	//check is book available to borrow, if yes - show "Borrow book" button in the table
	$(function() {
		$('#book-list-table').bootstrapTable({
		}).on('load-success.bs.table', function() {
			
			var noResultsResponse = $('#book-list-table tbody').find('tr.no-records-found');
			if( noResultsResponse != undefined && noResultsResponse.length > 0){
				return;
			}		
			
			var rows = $("#book-list-table").find(" tbody tr");
	
			if( <c:out value="${isAuthorizedLibrarian}"/> == true ){
				$.each(rows, function(i, val){
					var tdInside = $(val).children();
					var firstTd = tdInside.first();
				
					var newContent = $("<a href='' ></a>");
					newContent.attr("href","${contextPath}/editBook?bookId="+$(val).data('uniqueid'));
					newContent.text(firstTd.text())
					
					firstTd.html(newContent);
				});
			}
			
	 		$.each(rows, function(i, val){
				var id = $(val).data('uniqueid');
				var tdNode = $(val).children().last();
	
				var	takeButton = $("<button id=\""+getTakeButtonId(id)+"\" data-id=\""+ id +"\" type='button' style='display:none' class='btn btn-info take-book'>Take</button>");
				takeButton.click(function(){takeBook($(this).data("id"))});
				tdNode.html(takeButton);
				
				var returnButton = $("<button id=\""+getReturnButtonId(id)+"\" data-id=\""+ id +"\" type='button' style='display:none' class='btn btn-info return-book'>Return</button>");
				returnButton.click(function(){returnBook($(this).data("id"))});
				tdNode.append(returnButton);
				
				var messageNode = $("<div id=\""+getInfoMessageId(id)+"\" data-id=\""+ id +"\" style='display:none' class='info-message'></div>");
				tdNode.append(messageNode);

			});
	 		
			var jsonBookId = getBooksId();
			checkBooksAvailability(jsonBookId);
			
			checkIfNeedBookReturn();
		});
	});

	function getReturnButtonId(id){
		return "return_book-"+ id;
	}
	
	function getTakeButtonId(id){
		return "take_book-"+ id;
	}
	
	function getInfoMessageId(id){
		return "info_message-"+ id;
	}
	
	function checkBooksAvailability(data) {
		  $.ajax({
			url:"/Librarians/checkBooksCount",
			data: data,
			method: "GET"
		}).done(function(bookInstances) {;
			showTakeButton(bookInstances);
		});  
	}
	
	function checkIfNeedBookReturn(){
		var rows = $("#book-list-table").find(" tbody tr");

		$.each(rows, function(i, val){
			var id = $(val).data('uniqueid');
			showReturnButton(id);
		});
	}
	
	function getBooksId(){
		var result = [];
		var rows = $("#book-list-table").find(" tbody tr");
		for(var i=0; i <rows.length; i++){	
			result.push('books[]=' + rows[i].getAttribute("data-uniqueid"));	
		}
		result = result.join('&');
		return result;
	}
	
	function showTakeButton(bookInstances){		
	   	if(bookInstances){
			for (var prop in bookInstances) {
				if(bookInstances.hasOwnProperty(prop)){
					
					if(bookInstances[prop] > 0) {
						$("#take_book-" + prop).show();
					} else {
						$("#take_book-" + prop).hide();
					}
				}
			}
	   	}		
	}
	
	function showReturnButton(bookId) {		
		var data = {"bookId" : bookId};
		$.ajax({
			url:"/Librarians/isReturnBookAvailable",
			data: data,
			method: "GET"
		}).done(function(result){
			if(result.isReturnBookAvailable){
				$("#return_book-" + bookId).show();		
			} else {
				$("#return_book-" + bookId).hide();
			}
		});
	}

	function returnBook(bookId){
		var data = {"bookId":bookId};
		
		$.ajax({
			url:"/Librarians/returnBook",
			data: data,
			method: "GET"
		}).done(function(result){			
			var messageNode = $("#info_message-" + bookId);
			
			if(!result.isMoreBookInstanceLeft){
				$("#return_book-" + bookId).hide();
				messageNode.text("Book is returned.");
			} else {
				messageNode.text("Book is returned, but you have more the same book taken.");
			}
			messageNode.show();
			checkBooksAvailability('books[]=' + bookId);
		});
	}
	
	function takeBook(bookId){
		var data = {"bookId" : bookId}; 
		 $.ajax({
				url:"/Librarians/assignBookToUser",
				data: data,
				method: "GET"
			}).done(function(result) {
				renderMessageAndTakeButton(bookId, result);
				showReturnButton(bookId);
			});  
	}
	
	function renderMessageAndTakeButton(bookId, result) {
		var messageNode = $("#info_message-" + bookId);
		
		if(!result.isAssigned){
			messageNode.text("Sorry, this book is not available now.");
		} else {
			messageNode.text("You've took this book.");
		}
		messageNode.show();
		if(result.bookCountLeft == 0) {
			$("#take_book-" + bookId).hide();
		}
	};
	
</script>
