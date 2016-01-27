<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set value="${param.windowName}" var="modalWindowName"></c:set>
<c:set value="${param.titleText}" var="title"></c:set>
<c:set value="${param.labelText}" var="label"></c:set>
<c:set value="${param.buttonText}" var="button"></c:set>
<c:set value="${param.actionMethodName}" var="methodName"></c:set>
<c:set value="${param.inputName}" var="inputName"></c:set>


<div id="${modalWindowName}" class="modal fade" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" 
						onclick="javascript: clearModalWindow()">&times;</button>
				<h4 class="modal-title">${title}</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label class="control-label col-xs-2">${label}</label>
					<div class="col-xs-10">
						<input id="${inputName}" type="text" maxlength="255"
								class="form-control" placeholder="Name" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-10"> <span class="error"
						id="error"></span>
					</label>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					aria-hidden="true" onclick="javascript: clearModalWindow()">Close</button>
				
				<button type="button" class="btn btn-primary" aria-hidden="false"
					onclick="javascript: ${methodName}($('#${inputName}').val());">
					${button}</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function showModalWindow(windowName) {
		clearModalWindow();
		$("#" + windowName).modal('show');
	}
	
	function clearModalWindow(){
		$('#error').text("");
		var inputName = "<c:out value='${inputName}'/>";
		$("#"+inputName).val("");
	}
</script>
