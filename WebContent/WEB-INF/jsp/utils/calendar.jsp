<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="field" value="${param.fieldName}"></c:set>


<div class="control-group">
	<div class="controls">
		<div class="input-group">
			<form:input path="${field}" class="date-picker form-control" data-date-format="dd-mm-yyyy" /> 
			<label for="${field}" class="input-group-addon btn"> 
				<span class="glyphicon glyphicon-calendar"></span>
			</label>
			
		</div>
		<form:errors path="birthday" class="error"/>
		
		<script type="text/javascript">
			$(".date-picker").datepicker();
				
			$(".date-picker").on("change", function () {
			    var id = $(this).attr("id");
			    var val = $("label[for='" + id + "']").text();
			    $("#msg").text(val + " changed");
			});
        </script>
	</div>
</div>


