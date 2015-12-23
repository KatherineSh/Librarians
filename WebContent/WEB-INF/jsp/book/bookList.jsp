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
			data-search-time-out="2000"
			data-unique-id="id">

		<thead>
			<tr>
				<th class="col-md-2" data-field="title" data-align="center" data-sortable="true">Title</th>
				<th class="col-md-2" data-field="author" data-align="center" data-sortable="true">Author</th>
				<th class="col-md-2"data-field="year" data-align="center" data-sortable="true">Item	Name</th>
				<th class="col-md-2" data-field="isbn" data-align="center" data-sortable="true">ISBN</th>
				<th class="col-md-2" data-field="description" data-align="center" data-sortable="true">Description</th>
				<th class="col-md-2" data-align="center" style="text-align:center; vertical-align:middle;">Action</th>
			</tr>
		</thead>

	</table>	
</div>


<script type="text/javascript">

	//check is book available to borrow, if yes - show "Borrow book" button in the table
	$(function() {
		$('#table').bootstrapTable({
		}).on('load-success.bs.table', function() {
			checkBooksAvailability(table);
		});
	});
	
	function checkBooksAvailability(table) {
		//var data = {'booksIdArray' : getBooksId(table)};
		var data = getBooksId(table);
		  $.ajax({
			url:"/Librarians/checkBooksCount",
			data: data,
			method: "GET"
		}).done(function(bookInstances) {
		   	showButton(bookInstances);
		});  
	}
	
	function getBooksId(table){
		var result = [];
		var rows = $("#table").find(" tbody tr");
		for(var i=0; i <rows.length; i++){	
			result.push('books[]=' + rows[i].getAttribute("data-uniqueid"));	
		}
		result = result.join('&');
		return result;
	}

	function showButton(bookInstances) {
		if(bookInstances){
			for (var prop in bookInstances) {
				var button = $("<button type='button' class='btn btn-info'>Take</button>");
				
				if(bookInstances.hasOwnProperty(prop)){
					var selector = "#table tbody tr[data-uniqueid='".concat(prop,"']");
					
			    	if(bookInstances[prop] > 0){
						$(selector + ' td').last().html(button);
						$(selector).find('button').click(
								function(){takeBook(this)});
			    	}
			    }
			}
		}
	}
	//borrow action handling
	
	function takeBook(element){
		var trNode = element.parentNode.parentNode;
		var bookId = trNode.getAttribute("data-uniqueid");
		
		var data = {"bookId" : bookId}; 
		 $.ajax({
				url:"/Librarians/assignBookToUser",
				data: data,
				method: "GET"
			}).done(function(result) {
				renderMessageAndButton(bookId, result);
			});  
	}
	
	function renderMessageAndButton(bookId, result) {
		var selector = "tr[data-uniqueid='".concat(bookId,"']");
		var button = $(selector).find("button");
		var lastTd = $(selector + " td").last();
		
		var messageNode = $(selector + " td").find("div").css("class","info-message");
		if(messageNode.length == 0){
			messageNode = $("<div class=\"info-message\"></div>");
		}
		
		if(!result.isAssigned){
			messageNode.text("Sorry, this book is not available now.");
			messageNode.addClass('alert-danger');
		} else {
			messageNode.text("You've took this book.");
			messageNode.addClass('alert-success');
		}
		lastTd.append(messageNode);
		
		if(result.bookCountLeft == 0) {
			button.remove();
		}
	}
	
	//redrow table after search in table
	$(function() {
		$('#table').bootstrapTable({
			
		}).on('search.bs.table',function(e, text) {
			var data = {'search': text};
			
			$.ajax({
				url:"/Librarians/searchBook",
				data: data,
				method: "GET"
			}).done(function(table) {
			   	redrowTable(table);
			});
			;
		});
	});
	
	function redrowTable(table){
		$('#table').bootstrapTable('load', table);
	}
	
</script>
