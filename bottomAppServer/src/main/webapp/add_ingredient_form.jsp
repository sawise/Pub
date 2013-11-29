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
	<h1>Add Ingredient</h1>
	<br>
    <c:url var="addUrl" value="/add_ingredient"/>	
    <form:form method="POST" action="${editUrl}" commandName="ingredient">
		<form:errors path="*" cssClass="errorblock" element="div" />    	
		<form:label path="name"/>Name:
    	<form:input path="name" value="" />
    	<form:errors path="name" cssClass="error" />
    	
		<form:label path="category">Category:</form:label>
		<form:select path="category.id" commandName="ingredient">
        	<form:options items="${categories}" itemValue="id" itemLabel="name"/>
        </form:select>
        <form:errors path="category" cssClass="error" />
    	
    	<form:hidden path="id" value="" />
    	<input type="submit" value="Submit">
    </form:form>	
	<br>
	<a href="<c:url value='/ingredients'/>">Back</a> 
</body>
</html>