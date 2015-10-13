<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="bootstrap/css/datepicker.css" rel="stylesheet">
	<link href="bootstrap/css/datepicker.less" rel="stylesheet">
	
	<link href="css/styles.css" rel="stylesheet" type="text/css">
    
  </head>
  <body style="margin: 50px;">
  
      <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
    <script src="bootstrap/js/bootstrap-datepicker.js"></script>
    
  <br/>
  <h1>Welcome! You are loged in!</h1>
  <br/>
<%--   <c:if  test="${!empty userEmail}">
  	E-mail:<c:out value="${userEmail}"></c:out>
  </c:if>
  <br/> --%>
  
  <jsp:include page="librarian/addLibrariansForm.jsp"></jsp:include>
  <c:if  test="${param.newLibrarianAdded == true}">
  	<br/><h4>New librarian added</h4>	
  </c:if>
  <form action="<c:url value="/logout"/>" method="post" id="logout_form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<a onclick="document.getElementById('logout_form').submit();" style="cursor: pointer">Log out</a>
  </form>

  </body>
</html>
