<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>	
	
<jsp:include page="utils/pageHeader.jsp"></jsp:include>
	
	<div class="container" style="margin-left: 0;">

		<sec:authorize access="hasAnyAuthority('LIBRARIAN','USER')">
			<jsp:include page="book/bookList.jsp"></jsp:include>
		</sec:authorize>
	
	</div>
	
<jsp:include page="utils/pageFooter.jsp"></jsp:include>
