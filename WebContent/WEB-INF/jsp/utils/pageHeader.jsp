<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	
	<link href="${contextPath}/css/styles.css" rel="stylesheet" type="text/css">
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	
	
	<!-- BOOTSTRAP CSS -->
	<link href="${contextPath}/bootstrap/css/bootstrap.css"	rel="stylesheet">
	<link href="${contextPath}/bootstrap/css/datepicker.css" rel="stylesheet">
	<link href="${contextPath}/bootstrap/css/datepicker.less" rel="stylesheet">
	<link href="${contextPath}/bootstrap/css/bootstrap-table.css" rel="stylesheet">
	
	<!-- BOOTSTRAP JS -->
	<script	src="${contextPath}/bootstrap/js/bootstrap.js"></script>
	<script	src="${contextPath}/bootstrap/js/bootstrap-datepicker.js"></script>
	<script	src="${contextPath}/bootstrap/js/bootstrap-table.js"></script>

</head>
<body style="margin: 50px;">

<jsp:include page="navbar.jsp"></jsp:include>