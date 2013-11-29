<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<title>BottomApp</title>
<style>body {margin-left:20px;}</style>
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<h1>Edit this Category</h1>
    <br>
    <h2>Edit: ${category.getName()}</h2>
    <c:url var="editUrl" value="/edit_category"/>	    
    <form:form method="PUT" action="${editUrl}" commandName="category">
		<form:errors path="*" cssClass="errorblock" element="div" />    	
		<form:label path="name"/>Name:
    	<form:input path="name" value="" />
    	<form:errors path="name" cssClass="error" />
    	<form:hidden path="id" value="" />
    	<input type="submit" value="Edit">
    </form:form>	
	<br>
	<a href="<c:url value='/categories'/>">Back</a> 
</body>
</html>