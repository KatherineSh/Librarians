<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="control-group">
	<div class="controls">
		<div class="input-group">
			<form:input path="age" class="date-picker form-control" /> 
			<label for="age" class="input-group-addon btn"> 
				<span class="glyphicon glyphicon-calendar"></span>
			</label>
			
		</div>
		<form:errors path="age"/>
		
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


